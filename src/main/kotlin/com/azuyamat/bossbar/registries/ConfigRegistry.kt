package com.azuyamat.bossbar.registries

import com.azuyamat.bossbar.Config
import org.bukkit.plugin.java.JavaPlugin

object ConfigRegistry : Registry {
    val default = Config("config.yml")
    private val configs = mutableMapOf(
        "default" to default
    )

    fun reload(id: String): ReloadResult {
        val config = configs[id] ?: return ReloadResult.NOT_FOUND
        return try {
            config.reload()
            ReloadResult.SUCCESS
        } catch (e: Exception) {
            ReloadResult.FAILURE
        }
    }

    override fun init(plugin: JavaPlugin) {
        configs.forEach { (_, it) ->
            it.load(plugin)
        }
    }

    override fun teardown() {
        configs.forEach { (_, it) ->
            it.save()
        }
    }

    fun getIds(): Set<String> = configs.keys

    enum class ReloadResult(
        val message: String,
        val success: Boolean
    ) {
        SUCCESS("Successfully reloaded the configuration.", true),
        FAILURE("Failed to reload the configuration.", false),
        NOT_FOUND("Configuration not found.", false)
    }
}