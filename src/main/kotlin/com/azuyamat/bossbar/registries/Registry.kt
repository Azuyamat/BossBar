package com.azuyamat.bossbar.registries

import org.bukkit.plugin.java.JavaPlugin

interface Registry {
    fun init(plugin: JavaPlugin)
    fun teardown()
}