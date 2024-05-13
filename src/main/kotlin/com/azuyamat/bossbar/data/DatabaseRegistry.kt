package com.azuyamat.bossbar.data

import com.azuyamat.bossbar.data.tables.PlayerData
import org.bukkit.plugin.java.JavaPlugin

object DatabaseRegistry {
    val players = DatabaseHandler(PlayerData::class, "playerData")

    private val databases = listOf(
        players
    )

    fun init(plugin: JavaPlugin) {
        databases.forEach {
            it.init(plugin)
        }
    }
}