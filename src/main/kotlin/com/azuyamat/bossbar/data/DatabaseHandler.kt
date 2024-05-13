package com.azuyamat.bossbar.data

import me.outspending.munch.Munch
import me.outspending.munch.connection.MunchConnection
import me.outspending.munch.connection.MunchDatabase
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID
import kotlin.reflect.KClass

typealias Database<K> = MunchDatabase<K, UUID>

class DatabaseHandler<K: Any>(
    `class`: KClass<K>,
    private val databaseName: String
) {
    private val munch = Munch.create(`class`).process<UUID>()
    private val database = MunchConnection.create(munch)

    fun init(plugin: JavaPlugin) {
        val path = plugin.dataFolder.resolve("$databaseName.db")
        database.connect(path)
        database.createTable()
    }

    fun runOnDatabase(action: (Database<K>) -> Unit) {
        action(database)
    }
}