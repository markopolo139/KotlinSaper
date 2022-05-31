package pl.ms.saper.app.security

import net.bytebuddy.utility.RandomString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import pl.ms.saper.app.data.repositories.UserRepository
import pl.ms.saper.app.exceptions.InvalidTokenException
import pl.ms.saper.app.exceptions.InvalidUserEmailException
import pl.ms.saper.app.exceptions.SendingEmailException
import pl.ms.saper.app.utils.validatePassword
import java.io.File

@Service
class ForgotPasswordService {

    companion object {
        const val EMAIL_PATH = "/api/v1/reset/password?token="
        const val EMAIL_FROM = "springtest1@onet.pl"
    }

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var mailSender: JavaMailSender

    @Autowired
    private lateinit var templateEngine: TemplateEngine

    //TODO: when learn jwt, token also generate via jwt
    fun sendMessage(email: String, serverPath: String) {

        val userEntity = userRepository.findByEmail(email).orElseThrow { throw InvalidUserEmailException(email) }
        with(userEntity) {
            passwordToken = passwordToken ?: RandomString.make(100)
        }

        sendMail(email, "$serverPath$EMAIL_PATH${userEntity.passwordToken}")

        userRepository.save(userEntity)
    }

    fun sendMail(email: String, hostPath: String) {

        val context = Context()
        context.setVariable("sendPath", hostPath)

        val mimeMessage = mailSender.createMimeMessage()

        try {
            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true)

            mimeMessageHelper.setFrom(EMAIL_FROM, "Spring assistant")
            mimeMessageHelper.setTo(email)
            mimeMessageHelper.setSubject("Password Recovery")

            mimeMessageHelper.addInline("image", File("src/main/kotlin/resources/static/forgotPasswordImage.png"))
            mimeMessageHelper.setText(
                templateEngine.process("email", context), true
            )

            mailSender.send(mimeMessage)
        }
        catch (ex: MailException) { throw SendingEmailException() }

    }

    fun changePassword(token: String, newPassword: String) {

        val userEntity = userRepository.findByPasswordToken(token).orElseThrow { throw InvalidTokenException() }
        userEntity.userPassword = passwordEncoder.encode(validatePassword(newPassword))
        userEntity.passwordToken = null

        userRepository.save(userEntity)
    }

}