package me.fortibrine.dailytasks.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.util.concurrent.CompletableFuture

class DatabaseManager (
    private val url: String
) {

    private val config: HikariConfig = HikariConfig().apply {
        jdbcUrl = url
    }

    private val dataSource: HikariDataSource = HikariDataSource(config)
    private val connection = dataSource.connection

    fun prepare(sql: String, vararg params: Any) {
        CompletableFuture.runAsync {
            val statement = connection.prepareStatement(sql)

            for (i in 1..params.size) {
                statement.setObject(i, params)
            }

            statement.execute()
        }
    }

    fun query(sql: String, vararg params: Any) =
        CompletableFuture.supplyAsync {
            val statement = connection.prepareStatement(sql)

            for (i in 1..params.size) {
                statement.setObject(i, params)
            }

            statement.executeQuery()
        }

}