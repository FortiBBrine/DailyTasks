package me.fortibrine.dailytasks.listener

import me.fortibrine.dailytasks.DailyTasksPlugin
import me.fortibrine.dailytasks.task.BreakBlocksTask
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener(
    private val plugin: DailyTasksPlugin
): Listener {

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val player = event.player
        val uniqueId = player.uniqueId

        val tasks = plugin.taskManager.tasks[player.name]
            ?: return

        tasks.forEach { task ->
            if (task is BreakBlocksTask) {
                task.increase(event.player)
            }
        }

        plugin.taskManager.save(player)
    }

}