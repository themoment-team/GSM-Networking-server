package team.themoment.gsmNetworking.domain.board.controller

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.ISpringTemplateEngine
import javax.mail.internet.MimeMessage

@RestController
class TestController (
    private val javaMailSender: JavaMailSender,
    private val templateEngine: ISpringTemplateEngine
) {
    @GetMapping("/test-send")
    fun test(): String {
        javaMailSender.send(getMessage())
        return "OK"
    }
    private fun getMessage(): MimeMessage {
        val message = javaMailSender.createMimeMessage()
        val messageHelper = MimeMessageHelper(message, "UTF-8")
        messageHelper.setSubject("이메일 전송 테스트")
        messageHelper.setText(createMailTemplate(), true)
        messageHelper.setTo("s23012@gsm.hs.kr")
        return message
    }

    private fun createMailTemplate(): String {
        val context = Context()
        context.setVariable("teacherBoardId", 1)
        context.setVariable("teacherPostTitle", "안녕")
        return templateEngine.process("email-template", context)
    }
}