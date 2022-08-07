package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Model.PackageModel
import com.patronusstudio.BottleFlip.Service.PackageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequestMapping("/package")
@RestController
class PackageController {

    @Autowired
    private lateinit var packageService:PackageService

    @PostMapping("/insertPackage")
    fun insertPackage(@RequestBody model: PackageModel): BaseResponse{
        return packageService.insertPackage(model)
    }

    @PostMapping("/getPackage")
    fun getPackageByUsername(@RequestParam username:String): BaseResponse{
        return packageService.getPackagesFromUsername(username)
    }
}