package com.patronusstudio.BottleFlip.Temp.emails

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/mail")
@RestController // Class
class EmailController {
    @Autowired
    private val emailService: EmailService? = null

    @PostMapping("/sendMail")
    fun sendMail(@RequestBody details: EmailDetails?): String? {
        return emailService!!.sendSimpleMail(details!!)
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    fun sendMailWithAttachment(
        @RequestBody details: EmailDetails?
    ): String? {
        return emailService!!.sendMailWithAttachment(details!!)
    }
}