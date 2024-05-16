package com.azuyamat.bossbar.utils

import com.azuyamat.bossbar.data.tables.PlayerData
import com.azuyamat.bossbar.registries.DatabaseRegistry
import org.bukkit.entity.Player

fun Player.hasIsland(): Boolean {
    val data = DatabaseRegistry.players.get(this.uniqueId) ?: return false
    return data.islandUUID != null
}

fun Player.getData() = DatabaseRegistry.players.get(this.uniqueId) ?: PlayerData(uuid = this.uniqueId)
fun Player.getIsland() = this.getData().islandUUID?.let { DatabaseRegistry.islands.get(it) }