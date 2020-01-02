package org.wit.blockhouse.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.blockhouse.models.UserModel
import org.wit.blockhouse.models.UserStore
import org.wit.blockhouse.helpers.*
import java.util.*


val JSON_FILE_user = "users.json"
val gsonBuilder_user = GsonBuilder().setPrettyPrinting().create()
val listType_user = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type

fun generateRandomUserId(): Long {
    return Random().nextLong()
}

class UserJSONStore : UserStore, AnkoLogger {

    val context: Context
    var users = mutableListOf<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE_user)) {
            deserialize()
        }
    }

override fun findAllUsers(): MutableList<UserModel> {
    return users
}

override fun createUser(user: UserModel) {
    user.id = generateRandomUserId()
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

    private fun serialize() {
        val jsonString = gsonBuilder_user.toJson(users,
            listType_user
        )
        write(context, JSON_FILE_user, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context,
            JSON_FILE_user
        )
        users = Gson().fromJson(jsonString,
            listType_user
        )
    }


}