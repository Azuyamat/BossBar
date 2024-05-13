package com.azuyamat.bossbar.registries

import co.aikar.commands.BaseCommand
import co.aikar.commands.PaperCommandManager

const val COMMANDS_PACKAGE = "com.azuyamat.bossbar.commands"

class CommandRegistry(
    private val commandManager: PaperCommandManager,
) : Registry<BaseCommand>(COMMANDS_PACKAGE) {

    override fun register() {
        val classes = reflect(BaseCommand::class.java)
        classes.forEach{ command ->
            commandManager.registerCommand(command)
        }
    }
}