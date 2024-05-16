package com.azuyamat.bossbar.data.tables

import java.io.Serializable

interface Data<K : Any> : Serializable {
    fun getId(): K
}