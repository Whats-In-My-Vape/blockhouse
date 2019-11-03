package org.wit.blockhouse.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_blockhouse.view.*
import org.wit.blockhouse.R
import org.wit.blockhouse.helpers.readImageFromPath
import org.wit.blockhouse.models.BlockhouseModel
import java.text.DecimalFormat

interface BlockhouseListener {
    fun onBlockhouseClick(blockhouse: BlockhouseModel)
    fun del(blockhouse: BlockhouseModel)
}

class BlockhouseAdapter constructor(
    private var blockhouses : List<BlockhouseModel>,
    private val listener: BlockhouseListener
) : RecyclerView.Adapter<BlockhouseAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
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
            itemView.cardCheck.text = "Visited: ${blockhouse.check_box}"

            val lt = "LAT: ${DecimalFormat("#.##").format(blockhouse.location.lat)}"
            val lg = "LAT: ${DecimalFormat("#.##").format(blockhouse.location.lng)}"
            itemView.cardLocation.text = "$lt | $lg"

            itemView.setOnClickListener { listener.onBlockhouseClick(blockhouse) }
            itemView.delete_blockhouse.setOnClickListener { listener.del(blockhouse) }

            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, blockhouse.image))
        }
    }
}