package me.fortibrine.dailytasks.commands

import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import me.fortibrine.dailytasks.DailyTasksPlugin
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

@Command(name = "ElementTestPlugin")
class PluginCommand (
    private val plugin: DailyTasksPlugin
) {

    @Execute(name = "reload")
    @Permission("elementtestplugin.reload")
    fun reload(@Context sender: CommandSender) {
        plugin.reloadConfig()
        sender.sendMessage("Конфиг перезагружен.")
    }

    @Execute(name = "reset")
    @Permission("elementtestplugin.reset")
    fun reset(
        @Context sender: CommandSender,
        @Arg username: String
    ) {
        val player = Bukkit.getOfflinePlayer(username)

        if (player == null) {
            sender.sendMessage("Не удалось найти игрока.")
            return
        }

        plugin.taskManager.reset(player)
        sender.sendMessage("Информация сброшена.")
    }

}