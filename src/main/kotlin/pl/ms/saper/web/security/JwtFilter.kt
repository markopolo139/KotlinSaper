package pl.ms.saper.web.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import pl.ms.saper.app.data.repositories.UserRepository
import pl.ms.saper.app.utils.toCustomUser
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
    ): OncePerRequestFilter() {


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION) ?: ""
        if (header.isEmpty() || !header.startsWith("Basic ")) {
            filterChain.doFilter(request, response)
            return;
        }

        val encoded = header.split(" ")[1].trim();
        if (encoded.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        val splitUser = String(Base64.getDecoder().decode(encoded)).split(":")

        val currentUser = userRepository
            .findByUsername(splitUser[0])
            .ifPresent {

                if(!passwordEncoder.matches(splitUser[1], it.userPassword)) {
                    return@ifPresent;
                }

                val customUser = User.builder()
                    .username(it.username)
                    .password(it.userPassword)
                    .roles(*it.rolesSet.toTypedArray())
                    .build().toCustomUser(it.userId ?: 1)

                val authentication = UsernamePasswordAuthenticationToken(
                    customUser, null, customUser.authorities
                )

                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }

        filterChain.doFilter(request, response)
    }
}