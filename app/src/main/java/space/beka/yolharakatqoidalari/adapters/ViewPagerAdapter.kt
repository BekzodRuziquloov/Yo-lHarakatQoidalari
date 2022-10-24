package space.beka.yolharakatqoidalari.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_rv2.view.*
import kotlinx.android.synthetic.main.item_vp_2.view.*
import space.beka.yolharakatqoidalari.databinding.ItemRv2Binding
import space.beka.yolharakatqoidalari.databinding.ItemRvBinding
import space.beka.yolharakatqoidalari.databinding.ItemVp2Binding
import space.beka.yolharakatqoidalari.db.MyDbHelper
import space.beka.yolharakatqoidalari.models.Rule
import java.io.Serializable


class ViewPagerAdapter(val context: Context?, val rvItemClick: RvItemClick) : RecyclerView.Adapter<ViewPagerAdapter.Vh>(),
    Serializable {

    inner class Vh(var itemView: ItemRvBinding) : RecyclerView.ViewHolder(itemView.root) {
        fun onBind(position: Int) {
            val myDbHelper = MyDbHelper(context)
            val list = myDbHelper.getAllLabel()
            val ogohList = ArrayList<Rule>()
            val imtiyozList = ArrayList<Rule>()
            val taqiqList = ArrayList<Rule>()
            val buyurList = ArrayList<Rule>()
            for (belgi in list) {
                when(belgi.category){
                    0-> ogohList.add(belgi)
                    1-> imtiyozList.add(belgi)
                    2-> taqiqList.add(belgi)
                    3-> buyurList.add(belgi)
                }
            }
            when (position) {
                0 -> itemView.rv_belgilar.adapter = RvAdapter(ogohList, rvItemClick)
                1 -> itemView.rv_belgilar.adapter = RvAdapter(imtiyozList, rvItemClick)
                2 -> itemView.rv_belgilar.adapter = RvAdapter(taqiqList, rvItemClick)
                3 -> itemView.rv_belgilar.adapter = RvAdapter(buyurList, rvItemClick)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = 4
}