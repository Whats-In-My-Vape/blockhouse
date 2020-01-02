/*
package org.wit.blockhouse.authorisation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_page.*
import kotlinx.android.synthetic.main.register_page.back
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.blockhouse.R
import org.wit.blockhouse.activities.BlockhouseListActivity
import org.wit.blockhouse.main.MainApp
import org.wit.blockhouse.models.UserModel
import java.lang.Exception

class LoginActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        app = application as MainApp

        loginButton.setOnClickListener {
            val email = loginEmail!!.text.toString()
            val password = loginPassword!!.text.toString()

            if (email == "" || password == "") {
                toast(" !! ERROR EMPTY INPUTS !!")
            } else {
                if (app.isEmailValid(email)) {

                    try {

                        val user: UserModel? = app.users.findUserByEmail(email)

                        if (user != null && user.password == password) {
                            toast("HELLO ${user.name} :)")

                            // We set the currentUser to be the currentUser who logged in
                            app.currentUser = user

                            startActivity(
                                Intent(
                                    this@LoginActivity,
                                    BlockhouseListActivity::class.java
                                )
                            )
                        } else {
                            toast("!! ERROR USER NOT FOUND !!")
                        }
                    } catch (e: Exception) {
                        toast("!! ERROR LOGGING IN !! ")
                        toast("delete app now if this happens")
                        toast("over and over")
                    }
                } else {
                    toast("Invalid email format !!")
                }
            }

            back.setOnClickListener {
                startActivity(intentFor<ArrivalActivity>())
                finish()
            }
        }
    }
}*/
