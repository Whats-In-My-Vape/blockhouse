package org.wit.blockhouse.authorisation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_layout.toolbarAdd
import kotlinx.android.synthetic.main.settings_page.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.blockhouse.R
import org.wit.blockhouse.activities.BlockhouseListActivity
import org.wit.blockhouse.main.MainApp
import org.wit.blockhouse.models.BlockhouseModel
import org.wit.blockhouse.models.UserModel

class UserSettings : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var blockhouse = BlockhouseModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_page)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp

        fullName.setText(app.currentUser.name)
        newEmail.setHint(app.currentUser.email)

        update.setOnClickListener {
            val newUser = UserModel()

            if (newEmail.text.isEmpty() && newPasswd.text.isEmpty()) {
                toast("Update Failed (Empty Inputs)")
            } else {
                if (oldPasswd.text.toString() != app.currentUser.password) {
                    toast("Old Password does not match")
                    reset()
                } else if(newEmail.text.isEmpty()) {
                    newUser.id = app.currentUser.id
                    newUser.name = app.currentUser.name
                    newUser.email = app.currentUser.email
                    newUser.password = newPasswd.text.toString()

                    app.users.updateUser(newUser)
                    toast("Update Complete")
                    reset()

                } else {
                    newUser.id = app.currentUser.id
                    newUser.name = app.currentUser.name
                    newUser.email = newEmail.text.toString()
                    newUser.password = newPasswd.text.toString()

                    app.users.updateUser(newUser)
                    toast("Update Complete")
                    reset()

                }
            }
        }

        back.setOnClickListener {
            startActivity(Intent(this@UserSettings, BlockhouseListActivity::class.java))
            finish()
        }
    }

    private fun reset() {
        newEmail.setHint(app.currentUser.email)
        newEmail.setText("")
        oldPasswd.setText("")
        newPasswd.setText("")
    }

}