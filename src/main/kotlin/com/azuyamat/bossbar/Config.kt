package com.azuyamat.bossbar

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Config(private val path: String) {
    private var failed = false
    private var dataFile: File? = null
    var config: YamlConfiguration = YamlConfiguration()
        private set

    fun load(plugin: JavaPlugin) {
        try {
            dataFile = File(plugin.dataFolder, path)
            config = YamlConfiguration.loadConfiguration(dataFile!!)
        } catch (e: Exception) {
            fail()
            e.printStackTrace()
        }
    }

    fun reload() {
        try {
            config = YamlConfiguration.loadConfiguration(dataFile!!)
        } catch (e: Exception) {
            fail()
            e.printStackTrace()
        }
    }

    fun save() {
        if (failed) return
        dataFile?.let {
            config.save(it)
        }
    }

    private fun fail() {
        failed = true
        instance.logger.warning("Failed to load configuration file: $path")
    }
}