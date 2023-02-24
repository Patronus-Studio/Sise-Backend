package com.patronusstudio.BottleFlip.Temp.controllers

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Temp.models.CustomerRequestModel
import com.patronusstudio.BottleFlip.Temp.services.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequestMapping("/customer")
@RestController
class CustomerController {

    @Autowired
    private lateinit var customerService: CustomerService

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
    fun addCustomer(@RequestBody customerRequestModel: CustomerRequestModel):BaseSealed{
        val result =  customerService.addNewCustomer(customerRequestModel)

        return result
    }
}