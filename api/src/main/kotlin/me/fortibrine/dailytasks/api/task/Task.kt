package me.fortibrine.dailytasks.api.task

import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack

interface Task {
    fun increase(player: OfflinePlayer)
    fun isCompleted(): Boolean
    fun getItemStack(): ItemStack
    fun giveReward(player: OfflinePlayer)
}
