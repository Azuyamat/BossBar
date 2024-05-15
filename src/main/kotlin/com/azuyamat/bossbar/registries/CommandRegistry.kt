package com.azuyamat.bossbar.registries

import co.aikar.commands.BukkitCommandCompletionContext
import co.aikar.commands.CommandCompletions.AsyncCommandCompletionHandler
import com.azuyamat.bossbar.commands.Command
import com.azuyamat.bossbar.instance
import org.bukkit.plugin.java.JavaPlugin

const val COMMANDS_PACKAGE = "com.azuyamat.bossbar.commands"

object CommandRegistry : ReflectionRegistry<Command>(COMMANDS_PACKAGE) {
    private val commandManager
        get() = instance.commandManager

    fun registerCompletion(id: String, completion: AsyncCommandCompletionHandler<BukkitCommandCompletionContext>) {
        commandManager.commandCompletions.registerAsyncCompletion(id, completion)
    }

    override fun init(plugin: JavaPlugin) {
        val classes = reflect(Command::class.java)
        classes.forEach { command ->
            commandManager.registerCommand(command)
        }
    }

    override fun teardown() {}
}