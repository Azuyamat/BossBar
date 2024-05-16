package com.azuyamat.bossbar.verification

import java.util.logging.Logger

interface Verification {
    fun verifyBefore(): VerificationCallback
    fun verifyAfter(): VerificationCallback
}

class VerificationCallback(
    private var isSuccess: Boolean,
    private val message: String? = null
) {
    private val children = mutableListOf<VerificationCallback>()

    fun isSuccess(): Boolean {
        return isSuccess
    }

    fun setSuccess(success: Boolean) {
        isSuccess = success
    }

    fun getMessage(): String? {
        return message
    }

    fun addChild(callback: VerificationCallback) {
        children.add(callback)
    }

    fun log(logger: Logger, indent: Int = 0) {
        if (message != null) {
            val indentString = " ".repeat(indent)
            val parsed = indentString + message
            if (isSuccess) {
                logger.info(parsed)
            } else {
                logger.warning(parsed)
            }
        }
        children.forEach {
            it.log(logger, indent + 1)
        }
    }

    companion object {
        fun success(message: String? = null): VerificationCallback {
            return VerificationCallback(true, message)
        }

        fun failure(message: String? = null): VerificationCallback {
            return VerificationCallback(false, message)
        }
    }
}