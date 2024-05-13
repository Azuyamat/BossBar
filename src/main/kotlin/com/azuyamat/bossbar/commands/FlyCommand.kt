package com.azuyamat.bossbar.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import com.azuyamat.bossbar.utils.not
import org.bukkit.entity.Player

@CommandAlias("fly")
@CommandPermission("bossbar.command.fly.use")
@Description("Toggle fly mode.")
class FlyCommand : BaseCommand() {

    @Default
    fun onCommand(player: Player) {
        toggleFlyMode(player)
    }

    @Default
    fun onCommandOther(player: Player, other: Player) {
        if (other == player) {
            toggleFlyMode(player)
            return
        }

        toggleFlyMode(other)

        player.sendMessage(!"<pre> Toggled fly mode for <primary>${other.name}</primary> to ${if (other.allowFlight) "<green>ON" else "<red>OFF"}")
    }

    private fun toggleFlyMode(player: Player) {
        val allowed = !player.allowFlight

        player.allowFlight = allowed
        player.isFlying = allowed

        player.sendMessage(!"<pre> Flight ${if (allowed) "<green>enabled</green>" else "<red>disabled</red>"}")
    }
}