package com.azuyamat.bossbar.registries

import org.reflections.Reflections

abstract class ReflectionRegistry<K>(
    private val `package`: String
) : Registry {

    fun reflect(`class`: Class<K>): List<K> {
        val reflections = Reflections(`package`)
        return reflections.getSubTypesOf(`class`).toList().mapNotNull {
            it.constructors[0].newInstance() as? K
        }
    }
}