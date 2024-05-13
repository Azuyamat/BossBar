package com.azuyamat.bossbar.commands.gamemodes

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import com.azuyamat.bossbar.utils.not
import org.bukkit.GameMode
import org.bukkit.entity.Player

@CommandAlias("gma")
@CommandPermission("bossbar.commands.gma.use")
@Description("Set your gamemode to adventure.")
class GMACommand : BaseCommand() {

    @Default
    fun onCommand(player: Player) {
        player.gameMode = GameMode.ADVENTURE
        player.sendMessage(!"<pre> Set gamemode to <primary>adventure")
    }
}