package org.wit.blockhouse.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.blockhouse.R
import org.wit.blockhouse.models.BlockhouseJSONStore
import org.wit.blockhouse.models.UserModel
import org.wit.blockhouse.models.UserStore
import java.lang.Thread.sleep

class MainApp : Application(), AnkoLogger {

    val emailRegex =
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"


    lateinit var users: UserStore
    lateinit var currentUser: UserModel

    override fun onCreate() {
        sleep(2000)
        setTheme(R.style.AppTheme)
        super.onCreate()
        users = BlockhouseJSONStore(applicationContext)
        info("Blockhouse started")
    }

    fun isEmailValid(email: String): Boolean {
        return emailRegex.toRegex().matches(email)
    }
}