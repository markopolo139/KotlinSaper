package pl.ms.saper.app.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.thymeleaf.TemplateEngine
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templateresolver.ITemplateResolver

@Configuration
class ThymeLeafConfiguration {

    @Bean
    @Scope("singleton")
    fun getTemplateEngine(): TemplateEngine {
        val templateEngine = TemplateEngine()
        templateEngine.setTemplateResolver(getTemplateResolver())
        templateEngine.addDialect(Java8TimeDialect())
        return templateEngine
    }

    @Bean
    @Scope("singleton")
    fun getTemplateResolver(): ITemplateResolver {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.templateMode = TemplateMode.HTML
        templateResolver.prefix = "templates/"
        templateResolver.suffix = ".html"
        templateResolver.characterEncoding = "UTF-8"
        return templateResolver
    }

}