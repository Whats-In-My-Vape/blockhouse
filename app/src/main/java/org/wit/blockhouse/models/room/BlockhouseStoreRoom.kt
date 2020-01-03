package org.wit.blockhouse.models.room

import android.content.Context
import androidx.room.Room
import org.wit.blockhouse.models.BlockhouseModel
import org.wit.blockhouse.models.BlockhouseStore


class BlockhouseStoreRoom (val context: Context) : BlockhouseStore {

    var dao: BlockhouseDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.blockhouseDao()
    }

    override fun findAll(): List<BlockhouseModel> {
        return dao.findAll()
    }

    override fun findById(id: Long): BlockhouseModel? {
        return dao.findById(id)
    }

    override fun create(blockhouse: BlockhouseModel) {
        dao.create(blockhouse)
    }

    override fun update(blockhouse: BlockhouseModel) {
        dao.update(blockhouse)
    }

    override fun delete(blockhouse: BlockhouseModel) {
        dao.deleteBlockhouse(blockhouse)
    }

    override fun clear() {
    }
}