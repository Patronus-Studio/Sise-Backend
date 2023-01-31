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
    var phoneNumber: String? = null
    @JsonProperty
    var nameSurname: String? = null
    @JsonProperty
    var whichAirport: String? = null
    @JsonProperty
    var whichDistrinct: String? = null
    @JsonProperty
    var startDestination:String? = null
    @JsonProperty
    var numberOfSuitcases: String? = null
    @JsonProperty
    var boardingDate: String?= null
    @JsonProperty
    var flightNumber: String? = null
    @JsonProperty
    var numberOfChildSeats: String? = null
    @JsonProperty
    var numberOfCustomer: String? = null
    @JsonProperty
    var reservationCreatedTime: String?= null
    @JsonProperty
    var status: String? = null

}

enum class CustomerStatus

fun String.toLocalDateTime(): LocalDateTime {
    //LocalDateTime.parse("Jan 31 2023 11:51:49 PM" DateTimeFormatter.ofPattern("MMM dd yyyy h:mm:ss a" Locale.US))
    return LocalDateTime.parse(this ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US))
}
