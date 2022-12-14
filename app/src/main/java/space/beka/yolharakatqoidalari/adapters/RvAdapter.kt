package space.beka.yolharakatqoidalari.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_rv.view.*
import space.beka.yolharakatqoidalari.R
import space.beka.yolharakatqoidalari.databinding.ItemRvBinding
import space.beka.yolharakatqoidalari.models.Rule


class RvAdapter(var list:List<Rule>, val rvItemClick: RvItemClick) : RecyclerView.Adapter<RvAdapter.Vh>(){

    inner class Vh(var itemView: ItemRvBinding): RecyclerView.ViewHolder(itemView.root){
        fun onBind(position: Int, belgi: Rule){
            itemView.image_rv.setImageURI(Uri.parse(belgi.imagePath))
            itemView.txt_belgi_nomi.text = belgi.name

            if (belgi.like == 1){
                itemView.image_like.setImageResource(R.drawable.ic_like)
            }else{
                itemView.image_like.setImageResource(R.drawable.ic_not_like)
            }

            itemView.setOnClickListener {
                rvItemClick.itemClick(belgi)
            }
            itemView.edit.setOnClickListener { rvItemClick.edit(belgi) }
            itemView.delete.setOnClickListener { rvItemClick.delete(belgi) }
            itemView.image_like.setOnClickListener {
                rvItemClick.like(belgi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(position, list[position])
    }

    override fun getItemCount(): Int = list.size
}

interface RvItemClick{
    fun edit(rule: Rule)
    fun delete(rule: Rule)
    fun like(rule: Rule)
    fun itemClick(rule: Rule)
}