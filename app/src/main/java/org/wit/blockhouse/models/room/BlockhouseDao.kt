package org.wit.blockhouse.models.room

import androidx.room.*
import org.wit.blockhouse.models.BlockhouseModel

@Dao
interface BlockhouseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(blockhouse: BlockhouseModel)

    @Query("SELECT * FROM BlockhouseModel")
    fun findAll(): List<BlockhouseModel>

    @Query("select * from BlockhouseModel where id = :id")
    fun findById(id: Long): BlockhouseModel

    @Update
    fun update(blockhouse: BlockhouseModel)

    @Delete
    fun deleteBlockhouse(blockhouse: BlockhouseModel)
}