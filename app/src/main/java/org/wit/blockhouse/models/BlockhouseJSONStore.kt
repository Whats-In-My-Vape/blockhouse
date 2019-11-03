package org.wit.blockhouse.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.blockhouse.helpers.*
import java.util.*
import kotlin.collections.ArrayList

val JSON_FILE = "blockhouses.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<BlockhouseModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class BlockhouseJSONStore(val context: Context) : UserStore, AnkoLogger {

  var users = arrayListOf<UserModel>()

  init {
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(users, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    users = Gson().fromJson(jsonString, listType)
  }

  override fun findAll(): ArrayList<BlockhouseModel> {
    val total: ArrayList<BlockhouseModel> = arrayListOf()

    for (user in users) {
      if (user.blockhouses.isNotEmpty()) {
        for (blockhouse in user.blockhouses) {
          total.add(blockhouse)
        }
      }
    }
    return total
  }

  override fun findAllBlockhouses(user: UserModel): List<BlockhouseModel> {
    return user.blockhouses
  }

  override fun createBlockhouse(user: UserModel, blockhouse: BlockhouseModel) {
    blockhouse.id = generateRandomId().toInt()
    user.blockhouses.add(blockhouse)
    serialize()
  }

  override fun updateBlockhouse(user: UserModel, blockhouse: BlockhouseModel) {

    val foundBlockhouse: BlockhouseModel? = user.blockhouses.find { p -> p.id == blockhouse.id }

    if (foundBlockhouse != null) {
      foundBlockhouse.title = blockhouse.title
      foundBlockhouse.description = blockhouse.description
      foundBlockhouse.location.lat = blockhouse.location.lat.toString().toDouble()
      foundBlockhouse.location.lng = blockhouse.location.lng.toString().toDouble()
      foundBlockhouse.note = blockhouse.note
      foundBlockhouse.check_box = blockhouse.check_box
      foundBlockhouse.date = blockhouse.date
      serialize()
    }
  }

  override fun deleteBlockhouse(user: UserModel, blockhouse: BlockhouseModel) {
    val blockhouse: BlockhouseModel? = user.blockhouses.find { x -> x.id == blockhouse.id }
    user.blockhouses.remove(blockhouse)
    serialize()
  }

  override fun findOneBlockhouse(user: UserModel, id: Int): BlockhouseModel? {
    return user.blockhouses.find { p -> p.id == id }
  }

  // ------------- Override Function for Users ------------- //

  override fun findAllUsers(): ArrayList<UserModel> {
    return users
  }

  override fun createUser(user: UserModel) {
    user.id = generateRandomId().toInt()
    users.add(user)
    serialize()
  }

  override fun updateUser(user: UserModel) {
    val newUser: UserModel? = users.find { x -> x.id == user.id }

    if (newUser != null) {
      newUser.name = user.name
      newUser.email = user.email
      newUser.password = user.password
      newUser.blockhouses = user.blockhouses
    }
    serialize()
  }

  override fun deleteUser(user: UserModel) {
    users.remove(user)
    serialize()
  }

  override fun findUserByEmail(email: String): UserModel? {
    return users.find { x -> x.email == email }
  }
}
