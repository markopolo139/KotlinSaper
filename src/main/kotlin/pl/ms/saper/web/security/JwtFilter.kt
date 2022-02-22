package pl.ms.saper.web.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import pl.ms.saper.app.data.repositories.UserRepository
import pl.ms.saper.app.security.CustomUser
import pl.ms.saper.app.utils.toCustomUser
import java.util.*
import java.util.List
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(private val userRepository: UserRepository): OncePerRequestFilter() {


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

        val username = String(Base64.getDecoder().decode(encoded)).split(":")[0]

        val currentUser = userRepository
            .findByUsername(username)
            .orElseThrow { throw UsernameNotFoundException(username) }

        val customUser = User.builder()
            .username(currentUser.username)
            .password(currentUser.userPassword)
            .roles(*currentUser.rolesSet.toTypedArray())
            .build().toCustomUser(currentUser.userId ?: 1)

        val authentication = UsernamePasswordAuthenticationToken(
            customUser, null,
            if (currentUser == null) listOf() else customUser.authorities
        )

        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
}