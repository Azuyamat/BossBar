package com.azuyamat.bossbar.verification

import com.azuyamat.bossbar.data.tables.Data
import com.azuyamat.bossbar.data.tables.IslandData
import com.azuyamat.bossbar.data.tables.PlayerData
import java.io.Serializable
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties

class DatabaseVerification : Verification {
    private val bypassClasses = listOf(
        UUID::class,
        Int::class,
        String::class,
        Boolean::class,
        Double::class,
        Float::class,
        Long::class
    )
    private val classes = listOf(
        PlayerData::class,
        IslandData::class
    )


    override fun verifyBefore(): VerificationCallback {
        val fails = classes.map {
            areFieldsSerializable(it)
        }.filter { !it.isSuccess() }
        return if (fails.isNotEmpty()) {
            VerificationCallback.failure("Failed to verify database").apply {
                fails.forEach { addChild(it) }
            }
        } else {
            VerificationCallback.success("Database is ready to start up.")
        }
    }

    override fun verifyAfter(): VerificationCallback {
        return VerificationCallback.success("Database is ready to start up.")
    }

    private fun <T: Data<*>> areFieldsSerializable(clazz: KClass<T>): VerificationCallback {
        return try {
            val callback = VerificationCallback.success()
            for (member in clazz.declaredMemberProperties) {
                if (bypassClasses.contains(member.returnType.classifier)) {
                    continue
                }
                if (member !is Serializable) {
                    callback.setSuccess(false)
                    callback.addChild(VerificationCallback.failure("${member.name} in ${clazz.simpleName} is not serializable (${member.returnType.classifier})"))
                }
            }
            callback
        } catch (e: Exception) {
            VerificationCallback.failure("Failed to verify ${clazz.simpleName}: ${e.message}")
        }
    }
}