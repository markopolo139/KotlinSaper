package pl.ms.saper.web.models.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

class RegistrationModel(
    @field:NotEmpty @field:Length(min = 5) val username: String,
    @field:NotEmpty var password: String,
    @field:NotEmpty @field:Email val email: String,
)




