package org.wit.blockhouse.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_blockhouse_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.blockhouse.authorisation.LoginActivity
import org.wit.blockhouse.authorisation.UserSettings
import org.wit.blockhouse.adapters.BlockhouseAdapter
import org.wit.blockhouse.adapters.BlockhouseListener
import org.wit.blockhouse.R
import org.wit.blockhouse.main.MainApp
import org.wit.blockhouse.models.BlockhouseModel

class BlockhouseListActivity : AppCompatActivity(), BlockhouseListener {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_blockhouse_list)
    app = application as MainApp
    toolbar.title = title
    setSupportActionBar(toolbar)

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter =
        BlockhouseAdapter(app.currentUser.blockhouses, this)
    loadBlockhouses()
  }

  private fun loadBlockhouses() {
    showBlockhouses( app.currentUser.blockhouses)
  }

  fun showBlockhouses (blockhouses: List<BlockhouseModel>) {
    recyclerView.adapter = BlockhouseAdapter(blockhouses, this)
    recyclerView.adapter?.notifyDataSetChanged()
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

  override fun onBlockhouseClick(blockhouse: BlockhouseModel) {
    startActivityForResult(intentFor<BlockhouseActivity>().putExtra("blockhouse_edit", blockhouse), 0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadBlockhouses()
    super.onActivityResult(requestCode, resultCode, data)
  }

  override fun del(blockhouse: BlockhouseModel) {
    app.users.deleteBlockhouse(app.currentUser, blockhouse)
    loadBlockhouses()
    toast("Blockhouse Successfully Deleted")
  }

  private fun logout() {
    val intent = Intent(this, LoginActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
    finish()
  }
}