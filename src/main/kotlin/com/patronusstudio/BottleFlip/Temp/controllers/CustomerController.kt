package com.patronusstudio.BottleFlip.Temp.controllers

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Temp.emails.EmailService
import com.patronusstudio.BottleFlip.Temp.models.CustomerRequestModel
import com.patronusstudio.BottleFlip.Temp.services.AirportService
import com.patronusstudio.BottleFlip.Temp.services.CustomerService
import com.patronusstudio.BottleFlip.Temp.services.DistrictService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequestMapping("/customer")
@RestController
class CustomerController {

    @Autowired
    private lateinit var emailService: EmailService

    @Autowired
    private lateinit var customerService: CustomerService

    @Autowired
    private lateinit var districtService: DistrictService

    @Autowired
    private lateinit var airportService: AirportService

    @GetMapping("/getCustomers")
    fun fetchAllCustomer(): BaseResponse {
        return customerService.getAllCustomers()
    }

    @GetMapping("/getAllActiveCustomers")
    fun getAllActiveCustomers(): BaseResponse {
        return customerService.getAllActiveCustomers()
    }

    @GetMapping("/getAllDeactiveCustomers")
    fun getAllDeactiveCustomers(): BaseResponse {
        return customerService.getAllDeactiveCustomers()
    }

    @PostMapping("/addCustomer")
    fun addCustomer(@RequestBody customerRequestModel: CustomerRequestModel): BaseSealed {
        return customerService.addNewCustomer(customerRequestModel)
    }
}