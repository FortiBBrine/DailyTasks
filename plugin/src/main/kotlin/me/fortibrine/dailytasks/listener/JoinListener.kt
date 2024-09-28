package me.fortibrine.dailytasks.listener

import me.fortibrine.dailytasks.DailyTasksPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener (
    private val plugin: DailyTasksPlugin
): Listener {

    @EventHandler
    fun join(event: PlayerJoinEvent) {
        plugin.taskManager.load(event.player)
    }

}