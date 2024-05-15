package com.azuyamat.bossbar.commands.admin

import co.aikar.commands.annotation.*
import com.azuyamat.bossbar.commands.Command
import com.azuyamat.bossbar.registries.CommandRegistry
import com.azuyamat.bossbar.registries.ConfigRegistry
import com.azuyamat.bossbar.registries.MessageRegistry
import com.azuyamat.bossbar.utils.fromConfig
import org.bukkit.command.CommandSender

@CommandAlias("config")
@Description("Manage the plugin configuration.")
@CommandPermission("bossbar.admin.config")
class ConfigCommand : Command() {

    override fun init() {
        CommandRegistry.registerCompletion("config") { c ->
            return@registerCompletion ConfigRegistry.getIds().filter { it.startsWith(c.input) }
        }
    }

    @Default
    @Description("List all available configurations.")
    fun onList(sender: CommandSender) {
        sender.sendMessage("config.list.message".fromConfig(ConfigRegistry.getIds().joinToString()))
    }

    @Subcommand("reload")
    @Description("Reload the plugin configuration.")
    fun onReload(sender: CommandSender, id: String, @Optional @Default("false") emptyCache: Boolean) {
        val result = ConfigRegistry.reload(id)
        if (result.success && emptyCache) {
            MessageRegistry.emptyCache()
        }
        sender.sendMessage("config.reload.message".fromConfig(id, result.message))
    }
}