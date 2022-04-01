package com.lsroar.timbercrashlytics

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class TimberCrashlytics {

    private class CrashReportingTree : Timber.Tree() {

        override fun log(
            priority: Int,
            tag: String?,
            message: String,
            throwable: Throwable?
        ) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
            FirebaseCrashlytics.getInstance().log(StringBuilder().append(tag).append(message).toString())
            throwable?.let {
                if (priority == Log.ERROR) {
                    FirebaseCrashlytics.getInstance().recordException(it)
                }
            }
        }
    }

    companion object {

        fun init() {
            @Suppress("ConstantConditionIf")
            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            } else {
                Timber.plant(CrashReportingTree())
            }
        }

        fun log(msg: String) {
            Timber.d(msg)
        }

        fun logError(throwable: Throwable?, functionName: String? = null, message: String? = null) {
            throwable?.let {
                Timber.e(it, formatErrorText(getTag(), functionName, message))
            } ?: Timber.e(formatErrorText(getTag(), functionName, message))
        }

        inline fun <reified T : Any> logErrorAndNull(throwable: Throwable?, message: String = "No message"): T? {
            throwable?.let {
                Timber.e(it, message)
            } ?: Timber.e(message)
            return null
        }

        private fun getTag(): String {
            return this::class.java.simpleName
        }

        private fun formatErrorText(tag: String, functionName: String? = "", message: String? = ""): String {
            return "$tag $functionName: $message"
        }
    }
}
