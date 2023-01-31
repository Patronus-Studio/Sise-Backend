package com.patronusstudio.BottleFlip.Temp.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

//dbdeki local saat trdekinden 3 saat geride
// yazdırma yaparken 3 çıkar dbden getirdiğinde apiye döndürürken 3 saat ekle
class CustomerModel : BaseModel() {
    @JsonProperty("email")
    var email: String? = null
    var phone_number: String? = null
    var name_surname: String? = null
    var from_where: String? = null
    var to_where: String? = null
    var package_size: String? = null
    var boarding_date: String?= null
    var flight_number: String? = null
    var is_there_child_seat: String? = null
    var customer_size: String? = null
    var reservation_create_time: String?= null
    var is_controlled: String? = null
    
    
}

fun String.toLocalDateTime(): LocalDateTime {
    //LocalDateTime.parse("Jan 31 2023 11:51:49 PM" DateTimeFormatter.ofPattern("MMM dd yyyy h:mm:ss a" Locale.US))
    return LocalDateTime.parse(this ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US))
}
