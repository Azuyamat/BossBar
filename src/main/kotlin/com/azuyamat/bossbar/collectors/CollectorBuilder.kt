package com.azuyamat.bossbar.collectors

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

class CollectorBuilder(
    title: Component,
    prompt: Component,
) {
    private val meta = CollectorMeta(title, prompt)

    fun withTimeout(duration: Long): CollectorBuilder {
        meta.timeoutDuration = duration
        return this
    }

    fun onReceive(onReceive: (String) -> Unit): CollectorBuilder {
        meta.onCollect = onReceive
        return this
    }

    fun onCancel(onCancel: () -> Unit): CollectorBuilder {
        meta.onCancel = onCancel
        return this
    }

    fun onTimeout(onTimeout: () -> Unit): CollectorBuilder {
        meta.onTimeout = onTimeout
        return this
    }

    fun verifyValue(verifyValue: (String) -> Collector.ValidationResult): CollectorBuilder {
        meta.verifyValue = verifyValue
        return this
    }

    fun build(player: Player): Collector {
        return Collector(player, meta.apply {
            timeout = System.currentTimeMillis() + timeoutDuration
        })
    }
}