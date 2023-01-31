package com.patronusstudio.BottleFlip.Temp.models

data class CustomerRequestModel(
    val boardingDate: String,
    val email: String,
    val flightNumber: String,
    val nameSurname: String,
    val numberOfChildSeats: String,
    val numberOfCustomer: String,
    val numberOfSuitcases: String,
    val phoneNumber: String,
    val startDestination: String,
    val whichAirport: String,
    val whichDistrinct: String
)