package com.patronusstudio.BottleFlip.Repository

import com.patronusstudio.BottleFlip.BaseSealed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRepo {

    @Autowired
    private lateinit var jdbcConnection: JdbcTemplate

    fun getData(sqlQuery: String): BaseSealed {
        return try {
            val result = jdbcConnection.queryForMap(sqlQuery)
            BaseSealed.Succes(result)
        } catch (e: DataAccessException) {
            BaseSealed.Error(mapOf(Pair("error", e.localizedMessage)))
        }
    }

}