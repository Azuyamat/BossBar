package com.azuyamat.bossbar.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class DefaultConfig(plugin: JavaPlugin) {
    private val dataFile = File(plugin.dataFolder, "config.yml")
    var config: YamlConfiguration = YamlConfiguration()
        private set

    init {
        load()
    }

    fun load() {
        config = YamlConfiguration.loadConfiguration(dataFile)
    }

    fun save() {
        config.save(dataFile)
    }

    fun createIfNotExists() {
        if (!dataFile.exists()) {
            dataFile.mkdirs()
        }
    }
}