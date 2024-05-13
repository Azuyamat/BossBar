package com.azuyamat.bossbar.registries

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

const val LISTENERS_PACKAGE = "com.azuyamat.bossbar.listeners"

class ListenerRegistry(
    private val plugin: JavaPlugin
) : Registry<Listener>(LISTENERS_PACKAGE) {

    override fun register() {
        val listeners = reflect(Listener::class.java)
        listeners.forEach {
            plugin.server.pluginManager.registerEvents(it, plugin)
        }
    }
}