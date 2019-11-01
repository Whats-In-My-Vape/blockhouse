package org.wit.blockhouse.models

interface UserStore {


    fun findAll(): ArrayList<BlockhouseModel>

    fun findAllBlockhouses(user: UserModel): List<BlockhouseModel>
    fun createBlockhouse(user: UserModel, blockhouse: BlockhouseModel)
    fun updateBlockhouse(user: UserModel, blockhouse: BlockhouseModel)
    fun deleteBlockhouse(user: UserModel, blockhouse: BlockhouseModel)
    fun findOneBlockhouse(user: UserModel, id: Int): BlockhouseModel?

    // Functions for users //
    fun findAllUsers(): ArrayList<UserModel>

    fun createUser(user: UserModel)
    fun updateUser(user: UserModel)
    fun deleteUser(user: UserModel)

    // Find user by email  //
    fun findUserByEmail(email: String): UserModel?

}