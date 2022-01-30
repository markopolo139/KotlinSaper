package pl.ms.saper.web.security

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
class SecurityConfiguration: WebSecurityConfigurerAdapter() {
}