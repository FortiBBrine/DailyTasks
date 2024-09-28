package me.fortibrine.dailytasks.listener

import me.fortibrine.dailytasks.DailyTasksPlugin
import me.fortibrine.dailytasks.api.event.TaskCompleteEvent
import me.fortibrine.dailytasks.task.TwoTasksTask
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class TaskCompleteListener(
    private val plugin: DailyTasksPlugin
): Listener {

    @EventHandler
    fun onTaskComplete(event: TaskCompleteEvent) {
        val task = event.task
        val tasks = plugin.taskManager.tasks[event.player.name]
            ?: return

        if (task !is TwoTasksTask) {
            tasks.forEach {
                if (it is TwoTasksTask) {
                    it.increase(event.player)
                }
            }
        }

        plugin.taskManager.save(event.player)
    }

}