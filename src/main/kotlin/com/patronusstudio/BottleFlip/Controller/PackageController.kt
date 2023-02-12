package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Model.PackageCategoriesTypeModel
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

    @PostMapping("/getPackageByUsername")
    fun getPackageByUsername(@RequestParam username:String): BaseResponse{
        return packageService.getPackagesFromUsername(username)
    }
    @PostMapping("/getPackageByName")
    fun getPackageByName(@RequestParam name:String):BaseResponse{
        return packageService.getPackagesFromPackageName(name)
    }

    @PostMapping("/getPackageByMostLike")
    fun getPackageByMostLike():BaseResponse{
        return packageService.getPackagesFromMostLike()
    }

    @PostMapping("/getPackageByMostDownload")
    fun getPackageByMostDownload():BaseResponse{
        return packageService.getPackagesFromMostDownload()
    }

    @GetMapping("/getAllPackageCategories")
    fun getAllPackageCategories():BaseResponse{
        return packageService.getAllPackageCategories()
    }

    @PostMapping("/addPackageCategory")
    fun addPackageCategory(@RequestBody model: PackageCategoriesTypeModel):BaseResponse{
        return packageService.addPackageCategory(model)
    }

    @PostMapping("/updatePackageCategory")
    fun updatePackageCategory(@RequestBody model:PackageCategoriesTypeModel):BaseResponse{
        return packageService.updatePackageCategory(model)
    }

    @GetMapping("/getPackageByCategoryName")
    fun getAllPackageCategories(@RequestParam packageCategory:Int):BaseResponse{
        return packageService.getPackagesFromPackageCategories(packageCategory)
    }

    @PostMapping("/updatePackageNumberOfDownload")
    fun updatePackageNumberOfDownload(@RequestParam packageId:Int):BaseResponse{
        return packageService.updatePackageNumberOfDownload(packageId)
    }

    @GetMapping("/getAllPackages")
    fun getAllPackages():BaseResponse{
        return packageService.getAllPackages()
    }
}