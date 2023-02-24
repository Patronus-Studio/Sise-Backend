package com.patronusstudio.BottleFlip.Service

import com.patronusstudio.BottleFlip.Repository.SqlRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LogService {

    @Autowired
    private lateinit var sqlRepo: SqlRepo

    fun setLog(requestBody:String,responseBody:String){
        val sql = "INSERT INTO logs(requestBody,responseBody) VALUES(\'$requestBody\',\'$responseBody\')"
        val isThereLevelResult = sqlRepo.setData(sql)
    }

}