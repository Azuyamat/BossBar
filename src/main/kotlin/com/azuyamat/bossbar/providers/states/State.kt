package com.azuyamat.bossbar.providers.states

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

abstract class State {
    fun load(file: File) {
        fillValues(YamlConfiguration.loadConfiguration(file))
    }

    fun save(file: File) {
        println("Saving to $file")
        val config = YamlConfiguration()
        saveValues(config)
        config.save(file)
    }

    private fun fillValues(config: YamlConfiguration) {
        config.getKeys(false).forEach {
            val field = this::class.java.getDeclaredField(it)
            field.isAccessible = true
            field.set(this, config.get(it))
        }
    }

    private fun saveValues(config: YamlConfiguration) {
        this::class.java.declaredFields.forEach {
            it.isAccessible = true
            config.set(it.name, it.get(this))
        }
    }
}