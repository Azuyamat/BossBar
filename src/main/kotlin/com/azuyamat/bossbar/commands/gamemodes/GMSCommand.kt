package com.azuyamat.bossbar.commands.gamemodes

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import com.azuyamat.bossbar.utils.not
import org.bukkit.GameMode
import org.bukkit.entity.Player

@CommandAlias("gms")
@CommandPermission("bossbar.commands.gms.use")
@Description("Set your gamemode to survival.")
class GMSCommand : BaseCommand() {

    @Default
    fun onCommand(player: Player) {
        player.gameMode = GameMode.SURVIVAL
        player.sendMessage(!"<pre> Set gamemode to <primary>survival")
    }
}