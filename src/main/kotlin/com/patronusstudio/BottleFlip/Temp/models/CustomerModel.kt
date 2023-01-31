package com.patronusstudio.BottleFlip.Temp.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.patronusstudio.BottleFlip.Base.BaseModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

//dbdeki local saat trdekinden 3 saat geride
// yazdırma yaparken 3 çıkar dbden getirdiğinde apiye döndürürken 3 saat ekle
class CustomerModel : BaseModel() {
    @JsonProperty
    var id: Int? = null
    @JsonProperty
    var email: String? = null
    @JsonProperty
    var phone_number: String? = null
    @JsonProperty
    var name_surname: String? = null
    @JsonProperty
    var airport_id: String? = null
    @JsonProperty
    var distrinct_id: String? = null
    @JsonProperty
    var package_size: String? = null
    @JsonProperty
    var boarding_date: String?= null
    @JsonProperty
    var flight_number: String? = null
    @JsonProperty
    var child_seat_size: String? = null
    @JsonProperty
    var customer_size: String? = null
    @JsonProperty
    var reservation_create_time: String?= null
    @JsonProperty
    var status: String? = null
}

enum class CustomerStatus

fun String.toLocalDateTime(): LocalDateTime {
    //LocalDateTime.parse("Jan 31 2023 11:51:49 PM" DateTimeFormatter.ofPattern("MMM dd yyyy h:mm:ss a" Locale.US))
    return LocalDateTime.parse(this ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US))
}
