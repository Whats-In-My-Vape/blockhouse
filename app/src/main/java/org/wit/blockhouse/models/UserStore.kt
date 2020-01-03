package org.wit.blockhouse.models

interface UserStore {

    // Functions for users //
    fun findAllUsers(): List<UserModel>
    fun createUser(user: UserModel)
    fun updateUser(user: UserModel)
    fun deleteUser(user: UserModel)

    // Find user by email  //
    fun findUserByEmail(email: String): UserModel?

}