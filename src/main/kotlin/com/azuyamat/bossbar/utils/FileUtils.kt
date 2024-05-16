package com.azuyamat.bossbar.utils

import java.io.File

fun File.createIfNotExists() {
    if (!this.exists()) {
        this.createNewFile()
    }
}