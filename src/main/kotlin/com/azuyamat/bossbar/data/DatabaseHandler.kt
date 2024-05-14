package com.azuyamat.bossbar.data

import com.azuyamat.bossbar.data.tables.Data
import me.outspending.munch.Munch
import me.outspending.munch.connection.MunchConnection
import me.outspending.munch.connection.MunchDatabase
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass

typealias Database<K, V> = MunchDatabase<K, V>

class DatabaseHandler<K: Data<V>, V: Any>(
    `class`: KClass<K>,
    private val databaseName: String
) {
    private val munch = Munch.create(`class`).process<V>()
    private val database = MunchConnection.create(munch)
    private val cache = mutableMapOf<V, K>()

    fun init(plugin: JavaPlugin) {
        val path = plugin.dataFolder.resolve("$databaseName.db")
        database.connect(path)
        database.createTable()
    }

    fun runOnDatabase(action: (Database<K, V>) -> Unit) {
        action(database)
    }

    fun getFromDB(key: V): K? {
        return database.getData(key)
    }

    fun insertIntoDB(value: K) {
        database.addData(value)
    }

    fun replaceInDB(value: K) {
        database.deleteData(value.getId())
        database.addData(value)
    }

    fun updateInDB(value: K) {
        database.updateData(value, value.getId())
    }

    fun deleteFromDB(key: V) {
        database.deleteData(key)
    }

    fun getAllFromDB(): MutableList<K> {
        return database.getAllData() ?: mutableListOf()
    }

    fun get(key: V): K? {
        return cache[key] ?: getFromDB(key)
    }

    fun getAll(fromCache: Boolean = true): List<K> {
        return if (fromCache)
            cache.values.toList()
        else
            getAllFromDB()
    }

    fun insert(obj: K) {
        cache[obj.getId()] = obj
        insertIntoDB(obj)
    }

    fun update(obj: K) {
        cache[obj.getId()] = obj
        updateInDB(obj)
    }

    fun delete(obj: K) {
        cache.remove(obj.getId())
        deleteFromDB(obj.getId())
    }

    fun delete(key: V) {
        cache.remove(key)
        deleteFromDB(key)
    }

    fun save(obj: K) {
        cache.remove(obj.getId())
        updateInDB(obj)
    }

    fun saveAllCache() {
        cache.forEach { (_, value) ->
            save(value)
        }
        cache.clear()
    }
}