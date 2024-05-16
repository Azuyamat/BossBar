package com.azuyamat.bossbar.commands.admin

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import com.azuyamat.bossbar.collectors.CollectorBuilder
import com.azuyamat.bossbar.commands.Command
import com.azuyamat.bossbar.providers.IslandProvider
import com.azuyamat.bossbar.utils.fromConfig
import org.bukkit.entity.Player

@CommandAlias("isa|islandadmin")
@CommandPermission("bossbar.admin")
@Description("Island admin commands")
class AdminIslandCommand : Command() {

    @Subcommand("setIslandLocation")
    fun onSetIslandLocation(player: Player) {
        CollectorBuilder.areYouSure({
            // Yes
            IslandProvider.setLastLocation(player.location)
            player.sendMessage("island.admin.setLastLocation.message".fromConfig())
        }).build(player).register()
    }
}