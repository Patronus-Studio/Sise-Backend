package com.patronusstudio.BottleFlip.Repository

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.patronusstudio.BottleFlip.Base.BaseModel
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.enums.SqlErrorType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.reflect.Type


@Repository
class SqlRepo {

    @Autowired
    private lateinit var jdbcConnection: JdbcTemplate

    val mapper = jacksonObjectMapper().also {
        it.registerModule(JavaTimeModule())
    }


    fun <T : BaseModel> getDataForObject(sqlQuery: String, classType: Class<T>): BaseSealed {
        return try {
            val resultSql = jdbcConnection.queryForMap(sqlQuery)
            val json = mapper.writeValueAsString(resultSql)
            val result = Gson().fromJson(json,classType)
            BaseSealed.Succes(result)
        } catch (e: DataAccessException) {
            BaseSealed.Error(mapOf(Pair("error", e.localizedMessage)), SqlErrorType.DATA_ACCES_EXCEPTON)
        }
    }

    fun <T : BaseModel> getDataForList(sqlQuery: String, classType: Class<T>): BaseSealed {
        return try {
            val result = jdbcConnection.queryForList(sqlQuery)

            val listType: Type = TypeToken.getParameterized(ArrayList::class.java, classType).type
            val keyPairBoolDataList: List<T> = Gson().fromJson(Gson().toJson(result), listType)

            BaseSealed.Succes(keyPairBoolDataList)
        } catch (e: DataAccessException) {
            BaseSealed.Error(mapOf(Pair("error", e.localizedMessage)), SqlErrorType.DATA_ACCES_EXCEPTON)
        }
    }


    fun getBasicData(sqlQuery: String): BaseSealed {
        return try {
            val result = jdbcConnection.queryForObject(sqlQuery, String::class.java)
            BaseSealed.Succes(result)
        } catch (e: EmptyResultDataAccessException) {
            BaseSealed.Error(mapOf(Pair("error", e.localizedMessage)), SqlErrorType.EMPTY_RESULT_DATA)
        } catch (e: DataAccessException) {
            BaseSealed.Error(mapOf(Pair("error", e.localizedMessage)), SqlErrorType.DATA_ACCES_EXCEPTON)
        }
    }

    fun setData(sqlQuery: String): BaseSealed {
        return try {
            jdbcConnection.update(sqlQuery)
            BaseSealed.Succes()
        } catch (e: DataAccessException) {
            BaseSealed.Error(mapOf(Pair("error", e.localizedMessage)), SqlErrorType.DATA_ACCES_EXCEPTON)
        }
    }
}