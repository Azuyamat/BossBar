package com.azuyamat.bossbar.data

import com.azuyamat.bossbar.data.tables.Data
import com.azuyamat.bossbar.data.tables.IslandData
import com.azuyamat.bossbar.instance
import com.azuyamat.bossbar.utils.createIfNotExists
import me.outspending.munch.Munch
import me.outspending.munch.connection.MunchConnection
import me.outspending.munch.connection.MunchDatabase
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KFunction3

typealias Database<K, V> = MunchDatabase<K, V>

class DatabaseHandler<K : Data<V>, V : Any>(
    `class`: KClass<K>,
    private val databaseName: String,
    private val defaultInstance: (V) -> K
) {
    private val munch = Munch.create(`class`).process<V>()
    private val database = MunchConnection.create(munch)
    private val cache = mutableMapOf<V, K>()

    fun init(plugin: JavaPlugin) {
        val file = plugin.dataFolder.resolve("$databaseName.db")
        file.createIfNotExists()
        database.connect(file)
        if (instance.dropDatabase) {
            database.deleteTable()
        }
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

    fun update(key: V, action: (K) -> Unit) {
        val obj = get(key) ?: getDefaultValue(key)
        action(obj)
        cache[key] = obj
        replaceInDB(obj)
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

    fun getMatching(predicate: (K) -> Boolean): List<K> {
        val fromDB = database.getAllDataWithFilter(predicate)
        val fromCache = cache.values.filter(predicate)
        val all = (fromDB ?: mutableListOf()) + fromCache
        return all.distinct()
    }

    fun drop() {
        database.deleteTable()
    }

    private fun getDefaultValue(key: V): K {
        return defaultInstance.invoke(key)
    }
}