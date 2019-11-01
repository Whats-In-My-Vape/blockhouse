package org.wit.blockhouse.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_blockhouse.view.*
import org.wit.blockhouse.R
import org.wit.blockhouse.helpers.readImageFromPath
import org.wit.blockhouse.models.BlockhouseModel


interface BlockhouseListener {
  fun onBlockhouseClick(blockhouse: BlockhouseModel)
}

class BlockhouseAdapter constructor(private var blockhouses: List<BlockhouseModel>,
                                   private val listener: BlockhouseListener
) : RecyclerView.Adapter<BlockhouseAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(
        LayoutInflater.from(parent?.context).inflate(
            R.layout.card_blockhouse,
            parent,
            false
        )
    )
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val blockhouse = blockhouses[holder.adapterPosition]
    holder.bind(blockhouse, listener)
  }

  override fun getItemCount(): Int = blockhouses.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(blockhouse: BlockhouseModel, listener: BlockhouseListener) {
      itemView.blockhouseTitle.text = blockhouse.title
      itemView.description.text = blockhouse.description
      itemView.imageIcon.setImageBitmap(
          readImageFromPath(
              itemView.context,
              blockhouse.image
          )
      )
      itemView.setOnClickListener { listener.onBlockhouseClick(blockhouse) }
    }
  }
}