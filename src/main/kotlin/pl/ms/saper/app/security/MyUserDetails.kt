package pl.ms.saper.app.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import pl.ms.saper.app.data.repositories.UserRepository
import pl.ms.saper.app.exceptions.InvalidUserException
import pl.ms.saper.app.utils.toCustomUser

@Service
class MyUserDetails: UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String?): CustomUser {
        if (username == null) throw InvalidUserException()

        val currentUser = userRepository.findByUsername(username).orElseThrow { throw InvalidUserException(username) }

        return User.builder()
            .username(username)
            .password(currentUser.userPassword)
            .roles(*currentUser.rolesSet.toTypedArray())
            .build().toCustomUser(currentUser.userId ?: 1)
    }
}
