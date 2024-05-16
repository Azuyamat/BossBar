package com.azuyamat.bossbar.serialization

import org.bukkit.Bukkit
import org.bukkit.World
import java.io.Serializable

class SWorld(
    val name: String
) : Serializable {
    constructor(world: World) : this(world.name)

    fun toWorld(): World {
        return Bukkit.getWorld(name)!!
    }

    companion object {
        private const val serialVersionUID = 161606543026748223L
    }
}