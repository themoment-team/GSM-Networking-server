//package team.themoment.gsmNetworking.global.email.config
//
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.mail.MailSender
//import org.springframework.mail.javamail.JavaMailSenderImpl
//import java.util.Properties
//
//@Configuration
//class MailConfig(
//    @Value("\${spring.mail.host}") private val host: String,
//    @Value("\${spring.mail.port}") private val port: Int,
//    @Value("\${spring.mail.username}") private val username: String,
//    @Value("\${spring.mail.password}") private val password: String,
//    @Value("\${spring.mail.properties.mail.smtp.auth}") private val auth: Boolean,
//    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}") private val starttlsEnable: Boolean,
//    @Value("\${spring.mail.properties.mail.smtp.starttls.required}") private val starttlsRequired: Boolean,
//    @Value("\${spring.mail.properties.mail.smtp.connectiontimeout}") private val connectionTimeout: Int,
//    @Value("\${spring.mail.properties.mail.smtp.timeout}") private val timeout: Int,
//    @Value("\${spring.mail.properties.mail.smtp.writetimeout}") private val writeTimeout: Int
//) {
//    @Bean
//    fun getMailSender(): MailSender {
//        val mailSender = JavaMailSenderImpl()
//        mailSender.host = host
//        mailSender.port = port
//        mailSender.username = username
//        mailSender.password = password
//        mailSender.defaultEncoding = "UTF-8"
//
//        val props: Properties = mailSender.javaMailProperties
//        props["mail.smtp.auth"] = auth
//        props["mail.transport.protocol"] = "smtp"
//        props["mail.smtp.starttls.enable"] = starttlsEnable
//        props["mail.smtp.starttls.required"] = starttlsRequired
//        props["mail.smtp.connectiontimeout"] = connectionTimeout
//        props["mail.smtp.timeout"] = timeout
//        props["mail.smtp.writetimeout"] = writeTimeout
//
//        return mailSender
//    }
//}
