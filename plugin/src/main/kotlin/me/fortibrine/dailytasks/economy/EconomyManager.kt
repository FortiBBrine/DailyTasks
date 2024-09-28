package me.fortibrine.dailytasks.economy

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.plugin.RegisteredServiceProvider

class EconomyManager {

    private val economy by lazy {
        val server = Bukkit.getServer()
        val provider: RegisteredServiceProvider<Economy>? = server.servicesManager.getRegistration(Economy::class.java)
        val economy = provider?.provider

        economy
    }

    fun depositPlayer(player: OfflinePlayer, amount: Double) {
        economy?.depositPlayer(player, amount)
    }

    fun withdrawPlayer(player: OfflinePlayer, amount: Double) {
        economy?.withdrawPlayer(player, amount)
    }

    fun getBalance(player: OfflinePlayer): Double {
        return economy?.getBalance(player) ?: 0.0
    }

}