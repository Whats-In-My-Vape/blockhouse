package org.wit.blockhouse.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.blockhouse.models.BlockhouseStore
import org.wit.blockhouse.helpers.*
import org.wit.blockhouse.models.BlockhouseModel
import org.wit.blockhouse.models.UserModel
import java.util.*
import kotlin.collections.ArrayList

val JSON_FILE = "blockhouses.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<BlockhouseModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class BlockhouseJSONStore(val context: Context) :  BlockhouseStore, AnkoLogger {


  var blockhouses = arrayListOf<BlockhouseModel>()

  init {
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(blockhouses,
      listType
    )
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    blockhouses = Gson().fromJson(jsonString, listType)
  }

  override fun findAll(): MutableList<BlockhouseModel> {
    return blockhouses
  }

  override fun create(blockhouse: BlockhouseModel) {
    blockhouse.id = generateRandomId()
    blockhouses.add(blockhouse)
    serialize()
  }

  override fun update(blockhouse: BlockhouseModel) {

    val hillfortsList = findAll() as java.util.ArrayList<BlockhouseModel>
    var foundBlockhouse: BlockhouseModel? = hillfortsList.find { p -> p.id == blockhouse.id }
    if (foundBlockhouse != null) {
      foundBlockhouse.title = blockhouse.title
      foundBlockhouse.description = blockhouse.description
      foundBlockhouse.location.lat = blockhouse.location.lat.toString().toDouble()
      foundBlockhouse.location.lng = blockhouse.location.lng.toString().toDouble()
     // foundBlockhouse.note = blockhouse.note
     // foundBlockhouse.check_box = blockhouse.check_box
      foundBlockhouse.date = blockhouse.date
      serialize()
    }
  }

  override fun delete(blockhouse: BlockhouseModel) {
    blockhouses.remove(blockhouse)
    serialize()
  }

  override fun findById(id: Long): BlockhouseModel? {
    val foundBlockhouse: BlockhouseModel? = blockhouses.find { it.id == id }
    return foundBlockhouse
  }

  override fun clear(){
    blockhouses.clear()
  }

}
