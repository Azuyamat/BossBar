package com.azuyamat.bossbar

import co.aikar.commands.PaperCommandManager
import com.azuyamat.bossbar.providers.IslandProvider
import com.azuyamat.bossbar.registries.*
import com.azuyamat.bossbar.verification.ServerVerification
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.measureTime

val MAIN_COLOR = TextColor.color(255, 179, 71)

val instance = JavaPlugin.getPlugin(BossBar::class.java)
val pluginManager = Bukkit.getPluginManager()

class BossBar : JavaPlugin() {
    lateinit var commandManager: PaperCommandManager
        private set
    private val registries = mutableListOf<Registry>()
    private val serverVerification = ServerVerification()
    val dropDatabase = false

    override fun onEnable() {
        commandManager = PaperCommandManager(this)

        registries.addAll(
            listOf(
                ConfigRegistry,
                DatabaseRegistry,
                CommandRegistry,
                ListenerRegistry,
                CollectorRegistry,
                IslandProvider,
            )
        )
        registries.forEach {
            logger.info("Registering ${it::class.simpleName}")
            val initTime = measureTime {
                it.init(this)
            }
            logger.info("Registered ${it::class.simpleName} in $initTime")
        }

        serverVerification.logBefore(logger)
        // Insert code below


        // Insert code above
        serverVerification.logAfter(logger)
    }

    override fun onDisable() {
        registries.forEach {
            logger.info("Unregistering ${it::class.simpleName}")
            val teardownTime = measureTime {
                it.teardown()
            }
            logger.info("Unregistered ${it::class.simpleName} in $teardownTime")
        }
    }
}
