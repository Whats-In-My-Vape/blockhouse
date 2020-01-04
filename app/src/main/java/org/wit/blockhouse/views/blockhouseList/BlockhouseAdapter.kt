package org.wit.blockhouse.views.blockhouseList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_blockhouse.view.*
import org.wit.blockhouse.R
import com.bumptech.glide.Glide
import org.wit.blockhouse.models.BlockhouseModel
import kotlinx.android.synthetic.main.card_blockhouse.view.favourite
import kotlinx.android.synthetic.main.card_blockhouse.view.description
import kotlinx.android.synthetic.main.card_blockhouse.view.blockhouseName
import kotlinx.android.synthetic.main.card_blockhouse.view.ratingBar2

interface BlockhouseListener {
    fun onBlockhouseClick(blockhouse: BlockhouseModel)

}

class BlockhouseAdapter constructor(
    private var blockhouses: List<BlockhouseModel>,
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
            itemView.blockhouseName.text = blockhouse.title
            itemView.description.text = blockhouse.description
            // set status of the favourite checkbox
            if (blockhouse.favourite) {
                itemView.favourite.setImageResource(R.drawable.ic_check_box_outline_blank_24px)
            } else {
                itemView.favourite.setImageResource(R.drawable.ic_check_box_24px)
            }
            itemView.ratingBar2.rating = blockhouse.rating
            itemView.setOnClickListener { listener.onBlockhouseClick(blockhouse) }
            Glide.with(itemView.context).load(blockhouse.image).into(itemView.imageIcon)
        }
    }
}