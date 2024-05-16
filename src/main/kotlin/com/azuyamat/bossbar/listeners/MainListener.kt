package com.azuyamat.bossbar.listeners

import com.azuyamat.bossbar.registries.CollectorRegistry
import com.azuyamat.bossbar.utils.fromConfig
import com.azuyamat.bossbar.utils.not
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * Mostly there to disable pesky events. *cough* food level *cough*
 */
class MainListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        event.joinMessage(!"<primary>${event.player.name}</primary> <gray>joined!")
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        event.quitMessage(!"<primary>${event.player.name}</primary> <gray>left!")
        CollectorRegistry.getCollector(event.player.uniqueId)?.cancel()
    }

    @EventHandler
    fun onFoodLevelChange(event: FoodLevelChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onCommand(event: PlayerCommandPreprocessEvent) {
        val player = event.player
        val hasCollector = CollectorRegistry.hasCollector(player.uniqueId)
        if (hasCollector) {
            event.isCancelled = true
            player.sendMessage("collector.active.message".fromConfig())
        }
    }
}
