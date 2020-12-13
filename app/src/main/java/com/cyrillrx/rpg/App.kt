package com.cyrillrx.rpg

import android.app.Application
import com.cyrillrx.logger.Logger
import com.cyrillrx.logger.Severity
import com.cyrillrx.logger.extension.LogCat
import com.onesignal.OneSignal

/**
 * @author Cyril Leroux
 *         Created on 24/09/2019.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init()
        Logger.initialize()
        if (BuildConfig.DEBUG) {
            Logger.addChild(LogCat(Severity.VERBOSE))
        }
    }
}