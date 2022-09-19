package com.patronusstudio.BottleFlip.Controller

import com.patronusstudio.BottleFlip.Base.BaseResponse
import com.patronusstudio.BottleFlip.Service.CreateTableService
import com.patronusstudio.BottleFlip.enums.TableTypeEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/createTable")
@RestController
class CreateTableController {

    @Autowired
    private lateinit var createTableService: CreateTableService

    @PostMapping
    fun createTableUser(@RequestParam tableType: TableTypeEnum):BaseResponse{
        return createTableService.createTablePackage(tableType)
    }
}