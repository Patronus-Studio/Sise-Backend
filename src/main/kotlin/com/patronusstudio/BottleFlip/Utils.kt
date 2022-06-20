package com.patronusstudio.BottleFlip

import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

object Mysql{
    fun getConnection():Statement{
        val connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/bottleflipgames",
            "root", "123456789"
        )
        return connection.createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE
        )
    }
}