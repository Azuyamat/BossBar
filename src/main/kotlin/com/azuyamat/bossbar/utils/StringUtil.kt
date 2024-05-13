package com.azuyamat.bossbar.utils

import com.azuyamat.bossbar.MAIN_COLOR
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

private val tagResolvers = listOf<TagResolver>(
    Placeholder.styling("primary", MAIN_COLOR),
    Placeholder.parsed("pre", "<primary><bold>BOSSBAR</bold></primary> <sep>"),
    Placeholder.parsed("sep", "<gray>|")
)
private val mm = MiniMessage.miniMessage()

fun mm(text: String): Component {
    return mm.deserialize(text, *tagResolvers.toTypedArray())
}

operator fun String.not() = mm(this)