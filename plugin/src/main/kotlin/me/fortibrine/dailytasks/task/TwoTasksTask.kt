package me.fortibrine.dailytasks.task

import me.fortibrine.dailytasks.DailyTasksPlugin
import me.fortibrine.dailytasks.api.event.TaskCompleteEvent
import me.fortibrine.dailytasks.api.task.Task
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class TwoTasksTask (
    private val plugin: DailyTasksPlugin
) : Task {

    var current: Int = 0
        private set

    val need: Int = 2

    override fun increase(player: OfflinePlayer) {

        if (isCompleted()) return

        current ++

        if (isCompleted()) {
            Bukkit.getPluginManager().callEvent(TaskCompleteEvent(this, player))
        }
    }

    override fun isCompleted() = current >= need

    override fun getItemStack() =
        ItemStack(Material.matchMaterial("SKULL_ITEM") ?: Material.matchMaterial("PLAYER_HEAD")).apply {
            val meta = this.itemMeta as SkullMeta

            meta.displayName = when (isCompleted()) {
                true -> "§aЕжедневное задание"
                false -> "§fЕжедневное задание"
            }

            meta.lore = listOf(
                "§6Выполнить $need заданий",
                "",
                when (isCompleted()) {
                    true -> "§aПрогресс: $current из $need"
                    false -> "§fПрогресс: $current из $need"
                },
                "§6Награда: 200 золотых",
                when (isCompleted()) {
                    true -> "§oНажмите ЛКМ чтобы забрать награду"
                    false -> ""
                }
            )

            this.itemMeta = meta
        }

    override fun giveReward(player: OfflinePlayer) {
        plugin.economyManager.depositPlayer(player, 400.0)
    }

}