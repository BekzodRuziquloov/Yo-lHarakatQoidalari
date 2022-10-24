package space.beka.yolharakatqoidalari.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import space.beka.yolharakatqoidalari.fragments.AppInfoFragment
import space.beka.yolharakatqoidalari.fragments.HeartFragment
import space.beka.yolharakatqoidalari.fragments.HomeFragment
import space.beka.yolharakatqoidalari.fragments.MyHomeFragment

class CategoryAdapter(fragmentActivity: FragmentActivity?)
    : FragmentStateAdapter(fragmentActivity!!){
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        Log.d("onPosition", "$position")
        return when(position){
            0 -> MyHomeFragment()
            1 -> HeartFragment()
            2 -> AppInfoFragment()
            else -> HomeFragment()
        }
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        Log.d("onBindPosition", "$position")
    }


}