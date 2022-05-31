package pl.ms.saper.web.validation

import pl.ms.saper.app.configuration.ConfigKeyImpl
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ValidConfigurationKeyImpl: ConstraintValidator<ValidConfigurationKey, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {

        if (value == null)
            return false

        return try {
            ConfigKeyImpl.valueOf(value)
            true
        } catch (ex: Exception) {
            false
        }
    }

}