package com.azuyamat.bossbar.providers

interface Provider<K> {
    fun provide(): K
}