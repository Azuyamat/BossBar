package com.azuyamat.bossbar.providers

import com.azuyamat.bossbar.registries.Registry
import com.azuyamat.bossbar.providers.states.IslandState
import com.azuyamat.bossbar.providers.states.StateManager
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin

object IslandProvider : Provider<Location>, Registry {
    private val islandState = StateManager(IslandState())

    override fun provide(): Location {
        val location = islandState.getState().lastLocation.clone()
        islandState.getState().nextLocation()
        return location
    }

    override fun init(plugin: JavaPlugin) {
        islandState.load(plugin)
    }

    override fun teardown() {
        islandState.save()
    }

    fun setLastLocation(location: Location) {
        islandState.modifyState {
            lastLocation = location
        }
    }
}