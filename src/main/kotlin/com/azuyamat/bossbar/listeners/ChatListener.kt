package com.azuyamat.bossbar.listeners

import com.azuyamat.bossbar.data.enums.ChatColor
import com.azuyamat.bossbar.data.tables.PlayerData
import com.azuyamat.bossbar.registries.DatabaseRegistry
import com.azuyamat.bossbar.utils.color
import com.azuyamat.bossbar.utils.not
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class ChatListener : Listener {

    @EventHandler
    fun onChat(event: AsyncChatEvent) {
        event.renderer { player, _, message, _ ->
            val data = DatabaseRegistry.players.get(player.uniqueId) ?: PlayerData(player.uniqueId)
            val color = ChatColor.entries[data.chatColor]
            val textMessage = (message as TextComponent).content().color(color)
            (!"<primary>${player.name}</primary><gray>: ").append(textMessage)
        }
    }
}