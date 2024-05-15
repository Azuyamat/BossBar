package com.azuyamat.bossbar.utils

import com.azuyamat.bossbar.registries.DatabaseRegistry
import org.bukkit.entity.Player

fun Player.hasIsland(): Boolean {
    val data = DatabaseRegistry.players.get(this.uniqueId) ?: return false
    return data.islandUUID != null
}