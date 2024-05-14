package com.azuyamat.bossbar

import co.aikar.commands.PaperCommandManager
import com.azuyamat.bossbar.config.DefaultConfig
import com.azuyamat.bossbar.registries.DatabaseRegistry
import com.azuyamat.bossbar.data.enums.ChatColor
import com.azuyamat.bossbar.data.tables.PlayerData
import com.azuyamat.bossbar.registries.CommandRegistry
import com.azuyamat.bossbar.registries.ListenerRegistry
import com.azuyamat.bossbar.registries.Registry
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import kotlin.time.measureTime

val MAIN_COLOR = TextColor.color(255, 179, 71)

val instance = JavaPlugin.getPlugin(BossBar::class.java)
val pluginManager = Bukkit.getPluginManager()

class BossBar : JavaPlugin() {
    private lateinit var commandManager: PaperCommandManager
    lateinit var config: DefaultConfig
        private set
    private val registries = mutableListOf<Registry>()

    override fun onEnable() {
        config = DefaultConfig(this)
        commandManager = PaperCommandManager(this)

        registries.addAll(listOf(
            DatabaseRegistry,
            CommandRegistry(commandManager),
            ListenerRegistry
        ))
        registries.forEach {
            logger.info("Registering ${it::class.simpleName}")
            val initTime = measureTime {
                it.init(this)
            }
            logger.info("Registered ${it::class.simpleName} in $initTime")
        }

        testDB()
    }

    override fun onDisable() {
        config.save()
        registries.forEach {
            logger.info("Unregistering ${it::class.simpleName}")
            val teardownTime = measureTime {
                it.teardown()
            }
            logger.info("Unregistered ${it::class.simpleName} in $teardownTime")
        }
    }

    private fun testDB() {
        val db = DatabaseRegistry.players
        val playerData = PlayerData(UUID.randomUUID(), ChatColor.RED.ordinal)
        db.insert(playerData)
        db.save(playerData)
//        db.getAll(false).forEach(::println)
    }
}
