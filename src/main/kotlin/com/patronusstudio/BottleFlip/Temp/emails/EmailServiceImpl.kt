package com.patronusstudio.BottleFlip.Temp.emails

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.File
import javax.mail.MessagingException


//https://www.geeksforgeeks.org/spring-boot-sending-email-via-smtp/
@Service
class EmailServiceImpl : EmailService {
    @Autowired
    private val javaMailSender: JavaMailSender? = null

    @Value("\${spring.mail.username}")
    private val sender: String? = null

    override fun sendSimpleMail(details: EmailDetails): String? {
        return try {
            val mailMessage = SimpleMailMessage()
            mailMessage.setFrom(sender!!)
            mailMessage.setTo(details.recipient)
            mailMessage.setText(details.msgBody)
            mailMessage.setSubject(details.subject)
            javaMailSender!!.send(mailMessage)
            "Mail Sent Successfully..."
        } // Catch block to handle the exceptions
        catch (e: Exception) {
            e.localizedMessage
        }
    }

    // Method 2
    // To send an email with attachment
    override fun sendMailWithAttachment(details: EmailDetails): String? {
        // Creating a mime message
        val mimeMessage = javaMailSender!!.createMimeMessage()
        val mimeMessageHelper: MimeMessageHelper
        return try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper = MimeMessageHelper(mimeMessage, true)
            mimeMessageHelper.setFrom(sender!!)
            mimeMessageHelper.setTo(details.recipient)
            mimeMessageHelper.setText(details.msgBody)
            mimeMessageHelper.setSubject(
                details.subject
            )

            val file = FileSystemResource(
                File(details.attachment)
            )
            mimeMessageHelper.addAttachment(
                file.filename, file
            )

            // Sending the mail
            javaMailSender.send(mimeMessage)
            "Mail sent Successfully"
        } // Catch block to handle MessagingException
        catch (e: MessagingException) {

            // Display message when exception occurred
            "Error while sending mail!!!"
        }
    }
}