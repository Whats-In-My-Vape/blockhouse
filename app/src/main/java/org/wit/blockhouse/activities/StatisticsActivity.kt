package org.wit.blockhouse.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_layout.toolbarAdd
import kotlinx.android.synthetic.main.stats_page.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.blockhouse.authorisation.LoginActivity
import org.wit.blockhouse.authorisation.UserSettings
import org.wit.blockhouse.R
import org.wit.blockhouse.main.MainApp
import org.wit.blockhouse.models.BlockhouseModel


class StatisticsActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var blockhouse = BlockhouseModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stats_page)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp

        noBlockhouses.text = countBlockhouses().toString()
        noVisits.text = countVisits().toString()
        noImages.text = nImages().toString()
        noNotes.text = nNotes().toString()
    }

    private fun countBlockhouses(): Int {
        return app.users.findAllBlockhouses(app.currentUser).size
    }

    private fun countVisits(): Int {
        var total = 0
        val blockhouses: List<BlockhouseModel> = app.users.findAllBlockhouses(app.currentUser)

        for (visits in blockhouses) {
            if (visits.check_box) {
                total++
            }
        }
        return total
    }

    private fun nImages(): Int {
        var total = 0
        val blockhouses: List<BlockhouseModel> = app.users.findAllBlockhouses(app.currentUser)

        for (i in blockhouses) {
            total = i.image_list.size
        }
        return total
    }


    private fun nNotes(): Int {
        var total = 0
        val blockhouses: List<BlockhouseModel> = app.users.findAllBlockhouses(app.currentUser)

        for (i in blockhouses) {
            total = i.note.size
        }
        return total
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<BlockhouseActivity>(0)
            R.id.item_stats -> startActivity(intentFor<StatisticsActivity>())
            R.id.item_settings -> startActivity(intentFor<UserSettings>())
            R.id.item_logout -> {
                toast("Logged Out")
                logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun logout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}


