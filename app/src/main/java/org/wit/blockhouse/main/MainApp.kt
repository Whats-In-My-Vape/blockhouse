package org.wit.blockhouse.main

import android.app.Application
import org.jetbrains.anko.*
import org.wit.blockhouse.R
import org.wit.blockhouse.models.room.BlockhouseStoreRoom
import org.wit.blockhouse.models.BlockhouseStore
import org.wit.blockhouse.models.json.UserJSONStore
import org.wit.blockhouse.models.UserModel
import org.wit.blockhouse.models.UserStore
import java.lang.Thread.sleep

class MainApp : Application(), AnkoLogger {

    val emailRegex =
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"


    lateinit var users: UserStore
    //lateinit var currentUser: UserModel
    lateinit var blockhouses: BlockhouseStore

    override fun onCreate() {
        sleep(2000)
        setTheme(R.style.AppTheme)
        super.onCreate()
        blockhouses = BlockhouseStoreRoom(applicationContext)
        users = UserJSONStore(applicationContext)
        info("Blockhouse started")
        doAsync {
            users.createUser(UserModel(2, "problems@arrise.now", "secret", "4", 4))
            uiThread {
                     }
                }

        fun isEmailValid(email: String): Boolean {
            return emailRegex.toRegex().matches(email)
        }
    }
}