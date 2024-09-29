package me.fortibrine.dailytasks

import com.j256.ormlite.logger.Level
import com.j256.ormlite.logger.Logger
import dev.rollczi.litecommands.LiteCommands
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory
import me.fortibrine.dailytasks.commands.DailyCommand
import me.fortibrine.dailytasks.economy.EconomyManager
import me.fortibrine.dailytasks.listener.BlockBreakListener
import me.fortibrine.dailytasks.listener.JoinListener
import me.fortibrine.dailytasks.listener.TaskCompleteListener
import me.fortibrine.dailytasks.listener.ZombieKillListener
import me.fortibrine.dailytasks.task.util.TaskManager
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class DailyTasksPlugin: JavaPlugin() {

    val taskManager by lazy { TaskManager(this) }
    val economyManager by lazy { EconomyManager() }

    private lateinit var liteCommands: LiteCommands<CommandSender>

    override fun onEnable() {
        this.liteCommands = LiteBukkitFactory.builder("daily-tasks", this)
            .commands(
                DailyCommand(this)
            )
            .build()

        Logger.setGlobalLogLevel(Level.OFF)

        listOf(
            BlockBreakListener(this),
            ZombieKillListener(this),
            JoinListener(this),
            TaskCompleteListener(this)
        ).forEach { server.pluginManager.registerEvents(it, this) }
    }

    override fun onDisable() {
        this.liteCommands.unregister()
    }

}