package com.azuyamat.bossbar.commands

import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import com.azuyamat.bossbar.collectors.Collector
import com.azuyamat.bossbar.collectors.CollectorBuilder
import com.azuyamat.bossbar.data.enums.IslandRole
import com.azuyamat.bossbar.data.tables.IslandData
import com.azuyamat.bossbar.registries.DatabaseRegistry
import com.azuyamat.bossbar.serialization.toSerializableLocation
import com.azuyamat.bossbar.providers.IslandProvider
import com.azuyamat.bossbar.services.IslandService
import com.azuyamat.bossbar.utils.fromConfig
import com.azuyamat.bossbar.utils.getIsland
import com.azuyamat.bossbar.utils.hasIsland
import org.bukkit.entity.Player

@CommandAlias("island|is|cell")
@Description("Manage your island.")
class IslandCommand : Command() {

    @Default
    fun onCommand(player: Player) {
        val island = player.getIsland()
        if (island == null) {
            player.sendMessage("island.create.message".fromConfig())
            return
        }
        val location = island.centerLocation.toLocation()
        player.teleport(location)
        player.sendActionBar("island.teleport.message".fromConfig())
    }

    @Subcommand("create")
    fun onCreate(player: Player) {
        if (player.hasIsland()) {
            player.sendMessage("island.create.alreadyHasIsland".fromConfig())
            return
        } else {
            CollectorBuilder(
                "island.create.name.title".fromConfig(),
                "island.create.name.prompt".fromConfig()
            ).verifyValue {
                val isValid = it.length in 3..16 && it.all { char -> char.isLetterOrDigit() }
                if (!isValid)
                    return@verifyValue Collector.ValidationResult.invalid("Island name must be an alphanumeric string between 3 and 16 characters.")
                val isTaken = DatabaseRegistry.islands.getMatching { islandData -> islandData.name.equals(it, true) }.isNotEmpty()
                if (isTaken)
                    return@verifyValue Collector.ValidationResult.invalid("Island name is already taken.")
                return@verifyValue Collector.ValidationResult.valid()
            }.onReceive { value ->
                val location = IslandProvider.provide()
                val island = IslandData(
                    name = value,
                    centerLocation = location.toSerializableLocation()
                ).apply {
                    addMember(player.uniqueId, IslandRole.OWNER)
                }
                DatabaseRegistry.islands.insert(island)
                DatabaseRegistry.players.update(player.uniqueId) {
                    it.islandUUID = island.uuid
                }
                IslandService.buildIsland(island)
                player.sendMessage("island.create.success".fromConfig())
            }.build(player).register()
        }
    }

    @Subcommand("delete")
    fun onDelete(player: Player) {
        val island = player.getIsland()
        if (island == null) {
            player.sendMessage("island.delete.noIsland".fromConfig())
            return
        }
        if (!island.isOwner(player.uniqueId)) {
            player.sendMessage("island.delete.noPerms".fromConfig())
            return
        }
        CollectorBuilder.areYouSure({
            // Yes
            island.delete()
            player.sendMessage("island.delete.success".fromConfig())
        }) {
            // No
            player.sendMessage("island.delete.cancel".fromConfig())
        }.build(player).register()
    }
}