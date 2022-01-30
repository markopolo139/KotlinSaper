package pl.ms.saper.app.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pl.ms.saper.app.security.MyUserDetails

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
            .authorizeRequests()
            .antMatchers("/test")
            .permitAll()
            .anyRequest()
            .authenticated()
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
}