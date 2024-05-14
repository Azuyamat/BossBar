package com.azuyamat.bossbar.data.enums

import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor

enum class ChatColor(val value: String, val color: TextColor) {
    RED("<red>", NamedTextColor.RED),
    YELLOW("<yellow>", NamedTextColor.YELLOW),
    BLUE("<blue>", NamedTextColor.BLUE),
    PURPLE("<light_purple>", NamedTextColor.LIGHT_PURPLE),
    PINK("<dark_purple>", NamedTextColor.DARK_PURPLE),
    WHITE("<white>", NamedTextColor.WHITE),
    BLACK("<black>", NamedTextColor.BLACK),
}