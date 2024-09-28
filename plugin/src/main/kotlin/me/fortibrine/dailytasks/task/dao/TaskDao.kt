package me.fortibrine.dailytasks.task.dao

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.fortibrine.dailytasks.DailyTasksPlugin
import me.fortibrine.dailytasks.api.task.Task
import me.fortibrine.dailytasks.data.DatabaseManager
import me.fortibrine.dailytasks.task.BreakBlocksTask
import me.fortibrine.dailytasks.task.TwoTasksTask
import me.fortibrine.dailytasks.task.ZombieKillTask
import java.util.concurrent.CompletableFuture

class TaskDao (
    private val plugin: DailyTasksPlugin
) {

    private val databaseManager: DatabaseManager = plugin.databaseManager

    init {
        databaseManager.prepare(
            "CREATE TABLE IF NOT EXISTS tasks (" +
            "    name VARCHAR(255) PRIMARY KEY," +
            "    tasks VARCHAR(255)" +
            ");"
        )
    }

    fun getTasks(username: String): CompletableFuture<List<Task>> {
        return CompletableFuture.supplyAsync {
            val resultSet = databaseManager.query(
                "SELECT * FROM tasks WHERE username = ?",
                username
            ).get()

            if (!resultSet.next()) {
                listOf(
                    BreakBlocksTask(plugin = plugin),
                    ZombieKillTask(plugin = plugin),
                    TwoTasksTask(plugin = plugin)
                )
            }

            val tasksJsonString = resultSet.getString("tasks")

            val tasks = Gson().fromJson<List<Task>>(tasksJsonString, (object : TypeToken<List<Task>>() {
            }).type)

           tasks
        }
    }

    fun setTasks(username: String, tasks: List<Task>) {

        val jsonString = Gson().toJson(
            tasks
        )

        databaseManager.prepare(
            "MERGE INTO tasks (username, tasks)" +
                    "KEY (username)" +
                    "VALUES (?, ?);",
            username,
            jsonString
        )
    }

}