package pl.ms.saper.app.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.AnonymousAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import pl.ms.saper.app.data.repositories.UserRepository
import pl.ms.saper.web.security.JwtFilter

//TODO : check anonymous and authentication (in future change to jwt (to learn this))
@EnableWebSecurity
class SecurityConfiguration(): WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun configure(http: HttpSecurity?) {
        if (http == null) return

        http.csrf()
            .disable()
            .cors()
            .and()
            .anonymous().principal("anonymous").authorities("ROLE_ANONYMOUS")
            .and()
            .httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers("/test", "/login", "/api/v1/register", "/api/v1/send/message", "/api/v1/reset/password")
            .permitAll()
            .antMatchers("/AuthenticationTest")
            .authenticated()
            .anyRequest()
            .hasAnyRole("ANONYMOUS", "USER")

        http.addFilterAfter(
                JwtFilter(userRepository, getPasswordEncoder()),
                UsernamePasswordAuthenticationFilter::class.java,
            )
            .formLogin()
            .loginProcessingUrl("/login")
            .successForwardUrl("/game")
            .and()
            .logout()
            .deleteCookies("JSESSIONID")
    }


    @Bean
    @Scope("singleton")
    fun getPasswordEncoder() = BCryptPasswordEncoder()

    @Bean
    @Scope("singleton")
    fun getRolesHierarchy(): RoleHierarchy {
        val rolesHierarchy: RoleHierarchyImpl = RoleHierarchyImpl()
        rolesHierarchy.setHierarchy("""
            ROLE_ADMIN > ROLE_USER
            ROLE_USER > ROLE_ANONYMOUS
        """.trimIndent())

        return rolesHierarchy
    }

    @Bean
    @Scope("singleton")
    fun getAnonymousProvider() = AnonymousAuthenticationProvider("anonymous_access")


    @Bean
    @Scope("singleton")
    fun getAnonymousFilter() = AnonymousAuthenticationFilter(
        "anonymous_access", "anonymous", listOf(SimpleGrantedAuthority("ROLE_ANONYMOUS"))
    )

}