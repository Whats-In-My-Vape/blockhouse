package org.wit.blockhouse.main

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.*
import org.wit.blockhouse.R
import org.wit.blockhouse.models.BlockhouseStore
import org.wit.blockhouse.models.firebase.BlockhouseFireStore
import java.lang.Thread.sleep

class MainApp : Application(), AnkoLogger {

    lateinit var blockhouses: BlockhouseStore

    override fun onCreate() {
        sleep(2000)
        setTheme(R.style.AppTheme)
        super.onCreate()
        blockhouses = BlockhouseFireStore(applicationContext)


    }
}