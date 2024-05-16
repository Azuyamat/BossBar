package com.azuyamat.bossbar.services

import com.azuyamat.bossbar.data.tables.IslandData
import com.azuyamat.bossbar.instance
import org.bukkit.Bukkit
import org.bukkit.Material

object IslandService {
    fun buildIsland(island: IslandData) {
        val location = island.centerLocation.toLocation().add(0.0, -1.0, 0.0)
        val world = location.world
        Bukkit.getScheduler().runTask(instance, Runnable {
            world.getBlockAt(location).type = Material.GRASS_BLOCK
        })
    }
}