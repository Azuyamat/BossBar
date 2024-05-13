package com.azuyamat.bossbar

import co.aikar.commands.PaperCommandManager
import com.azuyamat.bossbar.config.DefaultConfig
import com.azuyamat.bossbar.data.DatabaseRegistry
import com.azuyamat.bossbar.data.tables.PlayerData
import com.azuyamat.bossbar.registries.CommandRegistry
import com.azuyamat.bossbar.registries.ListenerRegistry
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

val MAIN_COLOR = TextColor.color(255, 179, 71)

val instance = JavaPlugin.getPlugin(BossBar::class.java)
val pluginManager = Bukkit.getPluginManager()

class BossBar : JavaPlugin() {
    private lateinit var commandManager: PaperCommandManager
    lateinit var config: DefaultConfig
        private set

    override fun onEnable() {
        config = DefaultConfig(this)
        commandManager = PaperCommandManager(this)

        DatabaseRegistry.init(this)

        testDB()

        val registries = listOf(
            CommandRegistry(commandManager),
            ListenerRegistry(this),
        )
        registries.forEach {
            logger.info("Registering ${it::class.simpleName}")
            it.register()
            logger.info("Registered ${it::class.simpleName}")
        }
    }

    override fun onDisable() {
        config.save()
    }

    private fun testDB() {
        val db = DatabaseRegistry.players
        val playerData = PlayerData(UUID.randomUUID(), 2)
        db.runOnDatabase {
            it.addData(playerData)
            it.getAllData()?.forEach(::println)
        }
    }
}
