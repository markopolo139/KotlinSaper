package pl.ms.saper.app.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.ms.saper.app.data.repositories.UserRepository
import pl.ms.saper.app.utils.toUserEntity
import pl.ms.saper.app.utils.validatePassword
import pl.ms.saper.web.models.request.RegistrationModel

@Service
class UserManagementService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    fun saveUser(registerModel: RegistrationModel) {

        with(registerModel) {
            password = passwordEncoder.encode(validatePassword(this.password))
        }
        userRepository.save(registerModel.toUserEntity())
    }

}