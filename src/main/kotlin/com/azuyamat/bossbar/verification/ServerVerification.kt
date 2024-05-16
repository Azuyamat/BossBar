package com.azuyamat.bossbar.verification

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

/**
 * Makes sure the server is ready to start up properly.
 */
class ServerVerification : Verification {
    private val verifications = listOf(
        DatabaseVerification()
    )

    override fun verifyBefore(): VerificationCallback {
        val fails = verifications.map {
            it.verifyBefore()
        }.filter { !it.isSuccess() }
        return if (fails.isNotEmpty()) {
            VerificationCallback.failure("Failed to complete server verification [BEFORE]").apply {
                fails.forEach { addChild(it) }
            }
        } else {
            VerificationCallback.success("Server is ready to start up.")
        }
    }

    override fun verifyAfter(): VerificationCallback {
        val fails = verifications.map {
            it.verifyAfter()
        }.filter { !it.isSuccess() }
        return if (fails.isNotEmpty()) {
            VerificationCallback.failure("Failed to complete server verification [AFTER]").apply {
                fails.forEach { addChild(it) }
            }
        } else {
            VerificationCallback.success("Server is ready to start up.")
        }
    }

    fun logBefore(logger: Logger) {
        verifyBefore().log(logger)
    }

    fun logAfter(logger: Logger) {
        verifyAfter().log(logger)
    }
}