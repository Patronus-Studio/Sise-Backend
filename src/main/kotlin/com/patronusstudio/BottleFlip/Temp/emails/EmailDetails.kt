package com.patronusstudio.BottleFlip.Temp.emails

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor // Class
class EmailDetails {
    // Class data members
    var recipient: String = ""
    var msgBody: String = ""
    var subject: String = ""
    val attachment: String = ""
}