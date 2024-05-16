package com.azuyamat.bossbar.providers.states

import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.full.findAnnotation

class StateManager<T : State>(
    private val state: T
) {
    private val meta = StateMeta(
        file = state::class.findAnnotation<File>()?.value ?: throw IllegalStateException("State must have a @File annotation.")
    )
    private var file: java.io.File? = null

    fun load(plugin: JavaPlugin) {
        file = getFile(plugin)
        state.load(getFile(plugin))
    }

    fun save() {
        file?.let {
            state.save(it)
        }
    }

    fun getState(): T {
        return state
    }

    fun modifyState(modifier: T.() -> Unit) {
        state.modifier()
    }

    private fun getFile(plugin: JavaPlugin): java.io.File {
        return java.io.File(plugin.dataFolder, meta.file)
    }
}