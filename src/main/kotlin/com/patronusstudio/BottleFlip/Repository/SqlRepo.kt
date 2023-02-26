package com.patronusstudio.BottleFlip.Repository

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mysql.jdbc.Statement
import com.patronusstudio.BottleFlip.Base.BaseModel
import com.patronusstudio.BottleFlip.Base.BaseSealed
import com.patronusstudio.BottleFlip.Temp.models.CustomerModel
import com.patronusstudio.BottleFlip.enums.SqlErrorType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import java.lang.reflect.Type
import java.sql.PreparedStatement
import kotlin.reflect.KClass


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
            val listType: Type = object : TypeToken<List<T>>(){}.type
            val json = Gson().toJson(result)
            val keyPairBoolDataList: List<T> = Gson().fromJson(json, listType)
            if(keyPairBoolDataList.isEmpty()){
                BaseSealed.Error(mapOf(), SqlErrorType.EMPTY_RESULT_DATA)
            } else{
                BaseSealed.Succes(keyPairBoolDataList)
            }
        } catch (e: DataAccessException) {
            BaseSealed.Error(mapOf(Pair("error", e.localizedMessage)), SqlErrorType.DATA_ACCES_EXCEPTON)
        }
    }

    fun getList(sqlQuery: String): List<CustomerModel> {
        return try {
            return jdbcConnection.query(sqlQuery,BeanPropertyRowMapper(CustomerModel::class.java))

        } catch (e: DataAccessException) {
            listOf<CustomerModel>()
        }
    }

    fun <T:BaseModel> getDataForListWithReturnList(sqlQuery: String,clazz: KClass<T>): BaseSealed {
        return try {
            val result = jdbcConnection.query(sqlQuery,BeanPropertyRowMapper(clazz.java))
            BaseSealed.Succes(result)
        } catch (e: DataAccessException) {
            BaseSealed.Error(mapOf(Pair("error", e.localizedMessage)), SqlErrorType.EMPTY_RESULT_DATA)
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

    fun setDataWithReturnPrimaryKey(sqlQuery: String): BaseSealed {
        return try {
            val keyHolder: KeyHolder = GeneratedKeyHolder()
            jdbcConnection.update({ connection ->
                val ps: PreparedStatement = connection
                    .prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)
                ps
            }, keyHolder)
            BaseSealed.Succes(data = keyHolder.key)
        } catch (e: DataAccessException) {
            BaseSealed.Error(mapOf(Pair("error", e.localizedMessage)), SqlErrorType.DATA_ACCES_EXCEPTON)
        }
    }

}