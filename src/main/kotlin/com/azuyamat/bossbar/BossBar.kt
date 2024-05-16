package com.azuyamat.bossbar

import co.aikar.commands.PaperCommandManager
import com.azuyamat.bossbar.providers.IslandProvider
import com.azuyamat.bossbar.registries.*
import com.azuyamat.bossbar.utils.LogColor
import com.azuyamat.bossbar.verification.ServerVerification
import net.kyori.adventure.text.format.TextColor
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.measureTime

val MAIN_COLOR = TextColor.color(255, 179, 71)

val instance = JavaPlugin.getPlugin(BossBar::class.java)

class BossBar : JavaPlugin() {
    lateinit var commandManager: PaperCommandManager
        private set
    private val registries = mutableListOf<Registry>()
    private val serverVerification = ServerVerification()
    val dropDatabase = false

    override fun onEnable() {
        commandManager = PaperCommandManager(this)

        registries.addAll(listOf(
                ConfigRegistry,
                DatabaseRegistry,
                CommandRegistry,
                ListenerRegistry,
                CollectorRegistry,
                IslandProvider,
        ))
        registries.forEach(::initRegistry)
        serverVerification.logBefore(logger)
        serverVerification.logAfter(logger)
    }

    override fun onDisable() {
        registries.forEach(::teardownRegistry)
    }
}

private fun initRegistry(registry: Registry) {
    val initTime = measureTime {
        registry.init(instance)
    }
    instance.logger.info(LogColor.CYAN.colorize("Registered ${registry::class.simpleName} in $initTime"))
}

private fun teardownRegistry(registry: Registry) {
    val teardownTime = measureTime {
        registry.teardown()
    }
    instance.logger.info(LogColor.CYAN.colorize("Unregistered ${registry::class.simpleName} in $teardownTime"))
}
