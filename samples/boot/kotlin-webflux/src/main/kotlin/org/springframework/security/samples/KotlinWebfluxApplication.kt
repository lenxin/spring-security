package org.springframework.security.samples

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinWebfluxApplication

fun main(args: Array<String>) {
	runApplication<KotlinWebfluxApplication>(*args)
}
