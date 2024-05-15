package com.azuyamat.bossbar.commands.gamemodes

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import com.azuyamat.bossbar.commands.Command
import com.azuyamat.bossbar.utils.not
import org.bukkit.GameMode
import org.bukkit.entity.Player

@CommandAlias("gmc")
@CommandPermission("bossbar.commands.gmc.use")
@Description("Set your gamemode to creative.")
class GMCCommand : Command() {

    @Default
    fun onCommand(player: Player) {
        player.gameMode = GameMode.CREATIVE
        player.sendMessage(!"<pre> Set gamemode to <primary>creative")
    }
}