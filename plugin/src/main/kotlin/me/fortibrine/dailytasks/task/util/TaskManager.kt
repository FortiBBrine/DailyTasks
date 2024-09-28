package me.fortibrine.dailytasks.task.util

import me.fortibrine.dailytasks.DailyTasksPlugin
import me.fortibrine.dailytasks.api.task.Task
import org.bukkit.OfflinePlayer

class TaskManager (
    private val plugin: DailyTasksPlugin
) {

    val tasks: MutableMap<String, List<Task>> = mutableMapOf()

    fun save(player: OfflinePlayer) {

        plugin.taskDao.setTasks(
            player.name,
            tasks[player.name] ?: return
        )

    }

    fun load(player: OfflinePlayer) {
        tasks.remove(player.name)

        plugin.taskDao.getTasks(player.name).thenAccept {
            this.tasks[player.name] = it
        }
    }

}