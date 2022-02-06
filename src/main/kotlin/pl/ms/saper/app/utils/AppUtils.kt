package pl.ms.saper.app.utils

import org.passay.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import pl.ms.saper.app.data.entites.UserEntity
import pl.ms.saper.app.exceptions.InvalidPasswordException
import pl.ms.saper.app.security.CustomUser
import pl.ms.saper.web.models.request.RegistrationModel

fun UserDetails.toCustomUser(userId: Int): CustomUser = CustomUser(username, password, authorities.toMutableSet(), userId)

fun RegistrationModel.toUserEntity(): UserEntity = UserEntity(
    null, username, password, email, null, mutableSetOf("USER")
)

fun validatePassword(password: String): String {
    val passwordValidator = PasswordValidator(
        listOf(
            LengthRule(5,Int.MAX_VALUE),
            WhitespaceRule(),
            UsernameRule(true, true),
            CharacterRule(EnglishCharacterData.UpperCase, 1),
            CharacterRule(EnglishCharacterData.Special, 1),
            CharacterRule(EnglishCharacterData.Digit, 1)
        )
    )

    val ruleResult = passwordValidator.validate(PasswordData(password))

    if (!ruleResult.isValid)
        throw InvalidPasswordException(ruleResult.details.toString())

    return password
}