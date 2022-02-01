package pl.ms.saper.web.models.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

class RegistrationModel(
    @NotEmpty @Length(min = 5) val username: String,
    @NotEmpty val password: String,
    @NotEmpty @Email val email: String,
)




