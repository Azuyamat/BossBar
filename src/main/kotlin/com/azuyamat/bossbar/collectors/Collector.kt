package com.azuyamat.bossbar.collectors

import com.azuyamat.bossbar.registries.CollectorRegistry
import com.azuyamat.bossbar.utils.fromConfig
import com.azuyamat.bossbar.utils.not
import org.bukkit.entity.Player

class Collector(
    private val player: Player,
    private val meta: CollectorMeta
) {
    private var collected = false
    private var value: String? = null

    fun promptPlayer() {
        player.sendActionBar(meta.title.appendSpace().append(meta.prompt).appendSpace().append("collector.cancel.actionbar".fromConfig()))
    }

    fun verifyValue(value: String): ValidationResult {
        resetTimeout()
        val result = meta.verifyValue(value)
        if (!result.isValid) {
            if (result.message != null) {
                player.sendMessage(!"<red>${result.message}")
            } else {
                player.sendMessage("collector.invalid.message".fromConfig())
            }
        }
        return result
    }

    fun hasExpired(): Boolean {
        return System.currentTimeMillis() > meta.timeout
    }

    fun onTimeout() {
        player.sendMessage("collector.timeout.message".fromConfig())
        meta.onTimeout()
    }

    fun onCancel() {
        player.sendMessage("collector.cancel.message".fromConfig())
        meta.onCancel()
    }

    fun onCollect(value: String) {
        this.value = value
        this.collected = true
        meta.onCollect(value)
    }

    fun isCollected(): Boolean {
        return collected
    }

    fun getValue(): String? {
        return value
    }

    fun getPlayer(): Player {
        return player
    }

    fun getMeta(): CollectorMeta {
        return meta
    }

    fun cancel() {
        CollectorRegistry.removeCollector(player.uniqueId)
        onCancel()
    }

    fun collect(value: String) {
        CollectorRegistry.removeCollector(player.uniqueId)
        onCollect(value)
    }

    fun discard() {
        CollectorRegistry.removeCollector(player.uniqueId)
        onCancel()
    }

    fun register() {
        if (CollectorRegistry.hasCollector(player.uniqueId)) {
            player.sendMessage("collector.alreadyRegistered.message".fromConfig())
            return
        }
        CollectorRegistry.addCollector(player.uniqueId, this)
        promptPlayer()
    }

    private fun resetTimeout() {
        meta.timeout = System.currentTimeMillis() + meta.timeoutDuration
    }

    data class ValidationResult(
        val isValid: Boolean,
        val message: String? = null
    ) {
        companion object {
            fun valid(): ValidationResult {
                return ValidationResult(true)
            }

            fun invalid(message: String): ValidationResult {
                return ValidationResult(false, message)
            }
        }
    }
}