package com.azuyamat.bossbar.registries

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

const val LISTENERS_PACKAGE = "com.azuyamat.bossbar.listeners"

object ListenerRegistry : ReflectionRegistry<Listener>(LISTENERS_PACKAGE) {

    override fun init(plugin: JavaPlugin) {
        val listeners = reflect(Listener::class.java)
        listeners.forEach {
            plugin.server.pluginManager.registerEvents(it, plugin)
        }
    }

    override fun teardown() {}
}