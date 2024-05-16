package com.azuyamat.bossbar.serialization

import java.io.Serializable

class SerializableList<T: Any>(override val size: Int) : ArrayList<T>(), Serializable {
    constructor() : this(0)

    companion object {
        private const val serialVersionUID = -2951334475885013744L
    }
}