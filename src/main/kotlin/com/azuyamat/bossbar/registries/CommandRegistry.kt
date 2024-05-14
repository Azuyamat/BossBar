package com.azuyamat.bossbar.registries

import co.aikar.commands.BaseCommand
import co.aikar.commands.PaperCommandManager
import org.bukkit.plugin.java.JavaPlugin

const val COMMANDS_PACKAGE = "com.azuyamat.bossbar.commands"

class CommandRegistry(
    private val commandManager: PaperCommandManager,
) : ReflectionRegistry<BaseCommand>(COMMANDS_PACKAGE) {

    override fun init(plugin: JavaPlugin) {
        val classes = reflect(BaseCommand::class.java)
        classes.forEach{ command ->
            commandManager.registerCommand(command)
        }
    }

    override fun teardown() {}
}