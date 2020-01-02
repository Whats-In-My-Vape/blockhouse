package org.wit.blockhouse.models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.wit.blockhouse.models.BlockhouseModel

@Database(entities = arrayOf(BlockhouseModel::class), version = 1,  exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun blockhouseDao(): BlockhouseDao
}