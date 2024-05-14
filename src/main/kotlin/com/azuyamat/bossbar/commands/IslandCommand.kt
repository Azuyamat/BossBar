package com.azuyamat.bossbar.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import org.bukkit.entity.Player

@CommandAlias("island|is|cell")
@Description("Manage your island.")
class IslandCommand {

    @Default
    fun onCommand(player: Player) {

    }
}