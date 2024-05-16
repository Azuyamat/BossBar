package com.azuyamat.bossbar.serialization

import org.bukkit.Location
import org.bukkit.World
import java.io.Serializable

class SerializableLocation(
    val world: SWorld,
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float
) : Serializable {
    constructor(location: Location) : this(SWorld(location.world), location.x, location.y, location.z, location.yaw, location.pitch)
    constructor(world: World, x: Double, y: Double, z: Double) : this(SWorld(world), x, y, z, 0f, 0f)

    fun toLocation(): Location {
        return Location(world.toWorld(), x, y, z, yaw, pitch)
    }

    fun clone(): SerializableLocation {
        return SerializableLocation(world, x, y, z, yaw, pitch)
    }

    companion object {
        private const val serialVersionUID = 161606543026748223L
    }
}

fun Location.toSerializableLocation(): SerializableLocation {
    return SerializableLocation(SWorld(this.world), this.x, this.y, this.z, this.yaw, this.pitch)
}