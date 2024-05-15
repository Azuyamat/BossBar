package com.azuyamat.bossbar.collectors

import net.kyori.adventure.text.Component

data class CollectorMeta(
    val title: Component,
    val prompt: Component,
    var timeoutDuration: Long = 30000, // 30 seconds
    var timeout: Long = System.currentTimeMillis() + timeoutDuration,
    var onCollect: (String) -> Unit = {},
    var onCancel: () -> Unit = {},
    var onTimeout: () -> Unit = {},
    var verifyValue: (String) -> Collector.ValidationResult = { Collector.ValidationResult(true) },
)
