package pl.ms.saper.web.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.ms.saper.app.services.UserManagementService
import pl.ms.saper.web.models.request.RegistrationModel
import javax.validation.Valid

@CrossOrigin
@RestController
@Validated
class UserManagementController {

    @Autowired
    private lateinit var userManagementService: UserManagementService

    @PostMapping("/api/v1/register")
    fun registerUser(@Valid @RequestBody registerPayload: RegistrationModel) =
        userManagementService.saveUser(registerPayload)

}