package pl.ms.saper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SaperApplication

//TODO:
// add roles to methods (f.e. hasRole("User")), create admin panel (password admin)
// think how to create configuration presets (f.e. currentConfigurationId in database for board entity)
fun main(args: Array<String>) {
	runApplication<SaperApplication>(*args)
}
