package com.azuyamat.bossbar.registries

import com.azuyamat.bossbar.data.DatabaseHandler
import com.azuyamat.bossbar.data.tables.IslandData
import com.azuyamat.bossbar.data.tables.PlayerData
import com.azuyamat.bossbar.serialization.toSerializableLocation
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin

object DatabaseRegistry : Registry {
    val players = DatabaseHandler(PlayerData::class, "playerData") { key -> PlayerData(key) }
    val islands = DatabaseHandler(IslandData::class, "islandData") { key ->
        IslandData(
            name = key.toString(), centerLocation = Location(
                null,
                0.0, 0.0, 0.0
            ).toSerializableLocation()
        )
    }

    private val databases = listOf(
        players,
        islands
    )

    override fun init(plugin: JavaPlugin) {
        databases.forEach {
            it.init(plugin)
        }
    }

    override fun teardown() {
        databases.forEach {
            it.saveAllCache()
        }
    }
}