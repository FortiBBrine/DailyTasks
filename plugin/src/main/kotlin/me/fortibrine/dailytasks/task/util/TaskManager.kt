package me.fortibrine.dailytasks.task.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource
import com.j256.ormlite.table.TableUtils
import me.fortibrine.dailytasks.DailyTasksPlugin
import me.fortibrine.dailytasks.api.task.Task
import me.fortibrine.dailytasks.api.task.TaskAdapter
import me.fortibrine.dailytasks.task.BreakBlocksTask
import me.fortibrine.dailytasks.task.TwoTasksTask
import me.fortibrine.dailytasks.task.ZombieKillTask
import me.fortibrine.dailytasks.task.table.PlayerTasks
import org.bukkit.OfflinePlayer
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder

class TaskManager (
    private val plugin: DailyTasksPlugin
) {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Task::class.java, TaskAdapter())
        .create()

    val tasks: MutableMap<String, List<Task>> = mutableMapOf()
    private val connectionSource = JdbcPooledConnectionSource(
        "jdbc:h2:~/test2"
    ).apply {
        TableUtils.createTableIfNotExists(this, PlayerTasks::class.java)
    }
    private val playerTasksDao: Dao<PlayerTasks, String> = DaoManager.createDao(
        connectionSource,
        PlayerTasks::class.java
    )

    fun remove(player: OfflinePlayer, task: Task) {
        val playerTasks = tasks[player.name] ?: emptyList()
        val mutablePlayerTasks = playerTasks.toMutableList()
        mutablePlayerTasks.remove(task)
        tasks[player.name] = mutablePlayerTasks
        save(player)
    }

    fun save(player: OfflinePlayer) {

        val jsonString = gson.toJson(
            tasks.getOrDefault(player.name, emptyList()),
            (object : TypeToken<List<Task>>() {
            }).type
        )

        playerTasksDao.createOrUpdate(PlayerTasks(
            player.name,
            Base64Coder.encodeString(jsonString)
        ))

    }

    fun load(player: OfflinePlayer) {
        tasks.remove(player.name)

        val playerTasks = playerTasksDao.queryForId(player.name)

        if (playerTasks == null) {
            tasks[player.name] = listOf(
                BreakBlocksTask(),
                ZombieKillTask(),
                TwoTasksTask()
            )

            return
        }

        val tasks = gson.fromJson<List<Task>>(
            Base64Coder.decodeString(playerTasks.tasks),
            (object : TypeToken<List<Task>>() {
            }).type
        )

        this.tasks[player.name] = tasks

    }

}