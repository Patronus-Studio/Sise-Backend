package com.patronusstudio.BottleFlip.Repository

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.patronusstudio.BottleFlip.Base.BaseModel
import com.patronusstudio.BottleFlip.Base.BaseSealed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRepo {

    @Autowired
    private lateinit var jdbcConnection: JdbcTemplate

    val mapper = jacksonObjectMapper().also {
        it.registerModule(JavaTimeModule())
    }


    fun <T : BaseModel> getData(sqlQuery: String, classType: T): BaseSealed {
        return try {
            val result = jdbcConnection.queryForMap(sqlQuery)
            val json = mapper.writeValueAsString(result)
            val user = mapper.readValue(json, classType::class.java)
            BaseSealed.Succes(user)
        } catch (e: DataAccessException) {
            BaseSealed.Error(mapOf(Pair("error", e.localizedMessage)))
        }
    }

    fun setData(sqlQuery: String): BaseSealed {
        return try {
            jdbcConnection.update(sqlQuery)
            BaseSealed.Succes()
        } catch (e: DataAccessException) {
            BaseSealed.Error(mapOf(Pair("error", e.localizedMessage)))
        }
    }
}