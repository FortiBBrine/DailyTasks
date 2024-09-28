package me.fortibrine.dailytasks.listener

import me.fortibrine.dailytasks.DailyTasksPlugin
import me.fortibrine.dailytasks.task.ZombieKillTask
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class ZombieKillListener(
    private val plugin: DailyTasksPlugin,
): Listener {

    @EventHandler
    fun death(event: EntityDeathEvent) {
        val entity = event.entity ?: return
        val killer = entity.killer ?: return

        if (entity.type != EntityType.ZOMBIE) return

        val uniqueId = killer.uniqueId

        val tasks = plugin.taskManager.tasks[killer.name]
            ?: return

        tasks.forEach { task ->
            if (task is ZombieKillTask) {
                task.increase(killer)
            }
        }

        plugin.taskManager.save(killer)
    }

}