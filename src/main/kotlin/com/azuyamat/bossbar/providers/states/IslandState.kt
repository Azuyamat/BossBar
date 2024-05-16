package com.azuyamat.bossbar.providers.states

import org.bukkit.Location

@File("islandState.yml")
data class IslandState(
    var lastLocation: Location = Location(null, 0.0, 0.0, 0.0),
    val xDistance: Int = 100,
    val zDistance: Int = 100,
    val islandsPerX: Int = 10,
) : State() {
    fun nextLocation() {
        val shouldTranslateOnX = (lastLocation.x.toInt() / xDistance) % islandsPerX == 0
        if (shouldTranslateOnX) {
            lastLocation.x += xDistance
        } else {
            lastLocation.x -= xDistance * (islandsPerX - 1)
            lastLocation.z += zDistance
        }
    }
}
