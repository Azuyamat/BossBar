package com.azuyamat.bossbar.registries

import com.azuyamat.bossbar.collectors.Collector
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

object CollectorRegistry : Registry {
    private const val TIMEOUT = 0L
    private const val UPDATE_INTERVAL = 20L // 1 second
    private val collectors = mutableMapOf<UUID, Collector>()

    override fun init(plugin: JavaPlugin) {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable { updateCollectors() }, TIMEOUT, UPDATE_INTERVAL)
    }
    override fun teardown() {}

    fun addCollector(uuid: UUID, collector: Collector) {
        collectors[uuid] = collector
    }

    fun removeCollector(uuid: UUID) {
        collectors.remove(uuid)
    }

    fun getCollector(uuid: UUID): Collector? {
        return collectors[uuid]
    }

    fun hasCollector(uuid: UUID): Boolean {
        return collectors.containsKey(uuid)
    }

    private fun updateCollectors() {
        if (collectors.isEmpty()) return
        collectors.filter {
            it.value.hasExpired()
        }.forEach { (uuid, collector) ->
            collectors.remove(uuid)
            collector.onTimeout()
        }
        collectors.values.forEach(Collector::promptPlayer)
    }
}