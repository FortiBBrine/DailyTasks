package me.fortibrine.dailytasks.commands

import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import me.fortibrine.dailytasks.DailyTasksPlugin
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ru.boomearo.menuinv.api.Menu
import ru.boomearo.menuinv.api.MenuType
import ru.boomearo.menuinv.api.PluginPage
import ru.boomearo.menuinv.api.icon.IconBuilder

@Command(name = "daily")
class DailyCommand (
    private val plugin: DailyTasksPlugin
) {

    private val pluginPage by lazy {

        val page = object : PluginPage {
            override fun getPlugin() = this@DailyCommand.plugin
            override fun getPage() = "main"
        }

        Menu.registerPages(plugin)
            .createTemplatePage(page)
            .setInventoryTitle { "Ежедневные задания" }
            .setMenuType(MenuType.CHEST_9X5)
            .setStructure(
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . A . B . C . .",
                ". . . . . . . . .",
                ". . . . . . . . .",
            )
            .setIngredient('A',
                IconBuilder()
                    .setIconUpdate { _, player ->
                        plugin.taskManager.tasks[player.name]?.get(0)?.getItemStack() ?: ItemStack(Material.BARRIER)
                    }
            )
            .setIngredient('B',
                IconBuilder()
                    .setIconUpdate { _, player ->
                        plugin.taskManager.tasks[player.name]?.get(1)?.getItemStack() ?: ItemStack(Material.BARRIER)
                    }
            )
            .setIngredient('C',
                IconBuilder()
                    .setIconUpdate { _, player ->
                        plugin.taskManager.tasks[player.name]?.get(2)?.getItemStack() ?: ItemStack(Material.BARRIER)
                    }
            )

        page
    }

    @Execute
    fun execute(
        @Context player: Player
    ) {

        plugin.taskManager.load(player)

        Menu.open(pluginPage, player)

    }

}