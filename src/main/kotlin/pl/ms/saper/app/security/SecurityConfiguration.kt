package pl.ms.saper.app.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.AnonymousAuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import pl.ms.saper.app.security.MyUserDetails

//TODO : check anonymous and authentication (in future change to jwt (to learn this))
@EnableWebSecurity
class SecurityConfiguration: WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var myUserDetails: MyUserDetails

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(myUserDetails)
    }

    override fun configure(http: HttpSecurity?) {
        if (http == null) return

        http.csrf()
            .disable()
            .cors()
            .and()
            .anonymous().principal("anonymous").authorities("ROLE_ANONYMOUS")
            .and()
            .authorizeRequests()
            .antMatchers("/test")
            .permitAll()
            .antMatchers("/AuthenticationTest")
            .authenticated()
            .anyRequest()
            .hasAnyAuthority("ANONYMOUS", "USER")
            .and()
            .httpBasic()
            .and()
            .formLogin()
            .successForwardUrl("/game")
            .and()
            .logout()
            .deleteCookies("JSESSIONID")
    }

    @Bean
    @Scope("singleton")
    fun getAuthenticationProvider(): DaoAuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setUserDetailsService(myUserDetails)
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder())
        return daoAuthenticationProvider
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