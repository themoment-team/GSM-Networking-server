package team.themoment.gsmNetworking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class GsmNetworkingApplication

fun main(args: Array<String>) {
	runApplication<GsmNetworkingApplication>(*args)
}
