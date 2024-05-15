package com.azuyamat.bossbar.registries

import com.azuyamat.bossbar.utils.not
import net.kyori.adventure.text.Component
import org.bukkit.plugin.java.JavaPlugin

object MessageRegistry : Registry {
    private val cache = mutableMapOf<String, Component>()

    fun getMessage(key: String, vararg values: Any): Component? {
        val component = cache[key] ?: getAndPut(key)

        return if (values.isEmpty()) {
            component
        } else {
            component?.let { comp ->
                var modifiedComponent = comp
                values.forEachIndexed { index, value ->
                    modifiedComponent = modifiedComponent.replaceText { configurer ->
                        configurer.match("%$index").replacement(value.toString())
                    }
                }
                modifiedComponent
            }
        }
    }

    fun emptyCache() {
        cache.clear()
    }

    private fun getAndPut(key: String): Component? {
        val message = ConfigRegistry.default.config.getString(key)?.not() ?: return null
        cache[key] = message
        return message
    }

    override fun init(plugin: JavaPlugin) {}
    override fun teardown() {}

}