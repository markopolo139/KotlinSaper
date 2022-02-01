package pl.ms.saper.app.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.ms.saper.app.data.repositories.UserRepository
import pl.ms.saper.app.utils.toUserEntity
import pl.ms.saper.web.models.request.RegistrationModel

@Service
class UserManagementService {

    @Autowired
    private lateinit var userRepository: UserRepository

    fun saveUser(registerModel: RegistrationModel) {
        userRepository.save(registerModel.toUserEntity())
    }

}