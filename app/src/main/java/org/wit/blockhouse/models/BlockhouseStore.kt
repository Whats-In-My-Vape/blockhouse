package org.wit.blockhouse.models

interface BlockhouseStore {
    fun findAll(): List<BlockhouseModel>
    fun findById(id:Long) : BlockhouseModel?
    fun create(blockhouse: BlockhouseModel)
    fun update(blockhouse: BlockhouseModel)
    fun delete(blockhouse: BlockhouseModel)
}