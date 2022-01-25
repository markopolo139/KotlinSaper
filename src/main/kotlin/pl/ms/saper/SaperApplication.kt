package pl.ms.saper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SaperApplication

fun main(args: Array<String>) {
	runApplication<SaperApplication>(*args)
}
