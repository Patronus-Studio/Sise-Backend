package com.patronusstudio.BottleFlip.Temp.emails


// Interface
interface EmailService {
    // Method
    // To send a simple email
    fun sendSimpleMail(details: EmailDetails): String?

    // Method
    // To send an email with attachment
    fun sendMailWithAttachment(details: EmailDetails): String?
}