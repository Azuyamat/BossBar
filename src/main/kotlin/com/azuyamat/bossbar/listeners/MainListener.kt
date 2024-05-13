package com.azuyamat.bossbar.listeners

import com.azuyamat.bossbar.utils.not
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent
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
    }

    @EventHandler
    fun onFoodLevelChange(event: FoodLevelChangeEvent) {
        event.isCancelled = true
    }
}
