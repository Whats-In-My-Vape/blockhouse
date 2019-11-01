/*
package org.wit.blockhouse.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class BlockhouseMemStore : UserStore, AnkoLogger {

  val blockhouses = ArrayList<BlockhouseModel>()

  override fun findAll(): List<BlockhouseModel> {
    return blockhouses
  }
  override fun findById(id:Long) : BlockhouseModel? {
    val foundBlockhoue: BlockhouseModel? = blockhouses.find { it.id == id }
    return foundBlockhoue
  }

  override fun create(blockhouse: BlockhouseModel) {
    blockhouse.id = getId()
    blockhouses.add(blockhouse)
    logAll()
  }

  override fun update(blockhouse: BlockhouseModel) {
    var foundBlockhouse: BlockhouseModel? = blockhouses.find { p -> p.id == blockhouse.id }
    if (foundBlockhouse != null) {
      foundBlockhouse.title = blockhouse.title
      foundBlockhouse.description = blockhouse.description
      foundBlockhouse.image = blockhouse.image
      foundBlockhouse.lat = blockhouse.lat
      foundBlockhouse.lng = blockhouse.lng
      foundBlockhouse.zoom = blockhouse.zoom
      logAll();
    }
  }

  override fun delete(blockhouse: BlockhouseModel) {
    blockhouses.remove(blockhouse)
  }

  fun logAll() {
    blockhouses.forEach { info("${it}") }
  }
  override fun clear() {
    blockhouses.clear()
  }
}*/
