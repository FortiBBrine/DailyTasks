package me.fortibrine.dailytasks.api.event

import me.fortibrine.dailytasks.api.task.Task
import org.bukkit.OfflinePlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class TaskCompleteEvent(
    val task: Task,
    val player: OfflinePlayer
): Event() {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlerList
        }
    }

}