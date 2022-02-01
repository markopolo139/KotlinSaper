package pl.ms.saper.app.utils

import org.passay.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import pl.ms.saper.app.data.entites.UserEntity
import pl.ms.saper.app.exceptions.InvalidPasswordException
import pl.ms.saper.app.security.CustomUser
import pl.ms.saper.web.models.request.RegistrationModel

@Autowired
private lateinit var passwordEncoder: PasswordEncoder

fun UserDetails.toCustomUser(): CustomUser = CustomUser(username, password, authorities.toMutableSet())

fun RegistrationModel.toUserEntity(): UserEntity = UserEntity(
    null, username, password, email, null
)

fun validatePassword(password: String): String {
    val passwordValidator = PasswordValidator(
        listOf(
            LengthRule(5,Int.MAX_VALUE),
            WhitespaceRule(),
            SpecialCharacterRule(1),
            DigitCharacterRule(1),
            UppercaseCharacterRule(1)
        )
    )

    val ruleResult = passwordValidator.validate(PasswordData(password))

    if (!ruleResult.isValid)
        throw InvalidPasswordException(ruleResult.details.toString())

    return passwordEncoder.encode(password)
}