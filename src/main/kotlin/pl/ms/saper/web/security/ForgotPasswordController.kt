package pl.ms.saper.web.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.ms.saper.app.security.ForgotPasswordService
import javax.servlet.http.HttpServletRequest
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@CrossOrigin
@RestController
@Validated
class ForgotPasswordController {

    @Autowired
    private lateinit var forgotPasswordService: ForgotPasswordService


    @PostMapping("/api/v1/send/message")
    fun sendMessage(
        @RequestParam @Email email: String,
        request: HttpServletRequest
    ) = forgotPasswordService.sendMessage(email, request.requestURL.toString().replace(request.servletPath, ""))

    @PostMapping("/api/v1/reset/password")
    fun resetPassword(
        @RequestParam @NotBlank token: String,
        @RequestParam @NotBlank password: String
    ) = forgotPasswordService.changePassword(token, password)

}