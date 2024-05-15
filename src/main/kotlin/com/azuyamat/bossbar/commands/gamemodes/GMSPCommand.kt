package com.azuyamat.bossbar.commands.gamemodes

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import com.azuyamat.bossbar.commands.Command
import com.azuyamat.bossbar.utils.not
import org.bukkit.GameMode
import org.bukkit.entity.Player

@CommandAlias("gmsp")
@CommandPermission("bossbar.commands.gmsp.use")
@Description("Set your gamemode to spectator.")
class GMSPCommand : Command() {

    @Default
    fun onCommand(player: Player) {
        player.gameMode = GameMode.SPECTATOR
        player.sendMessage(!"<pre> Set gamemode to <primary>spectator")
    }
}