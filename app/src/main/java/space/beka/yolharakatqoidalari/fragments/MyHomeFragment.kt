package space.beka.yolharakatqoidalari.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.tab_item_category.view.*
import space.beka.yolharakatqoidalari.R
import space.beka.yolharakatqoidalari.adapters.RvItemClick
import space.beka.yolharakatqoidalari.adapters.ViewPagerAdapter
import space.beka.yolharakatqoidalari.databinding.FragmentMyHomeBinding
import space.beka.yolharakatqoidalari.db.MyDbHelper
import space.beka.yolharakatqoidalari.models.Rule

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyHomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentMyHomeBinding
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var myDbHelper: MyDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyHomeBinding.inflate(LayoutInflater.from(context))

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.d("OnStart", "MyHomeFragment")
    }

    override fun onResume() {
        super.onResume()
        Log.d("OnResume", "MyHomeFragment")
        myDbHelper = MyDbHelper(context)
        viewPagerAdapter = ViewPagerAdapter(context, object : RvItemClick {
            override fun edit(rule: Rule) {
                findNavController().navigate(R.id.editFragment, bundleOf("label" to rule, "adapter" to viewPagerAdapter))
            }

            override fun delete(rule: Rule) {
                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("Are you agree?")
                dialog.setMessage("${rule.name} would you delete this?")
                dialog.setPositiveButton("Yes "
                ) { dialog, which ->
                    myDbHelper.deleteLabel(rule)
                    onResume()
                }

                dialog.setNegativeButton("No"
                ) { dialog, which ->
                    dialog.cancel()
                }
                dialog.show()
            }

            override fun like(rule: Rule) {
                if (rule.like == 0) {
                    rule.like = 1
                } else {
                    rule.like = 0
                }
                myDbHelper.editLabel(rule)
                onResume()
            }

            override fun itemClick(rule: Rule) {
                findNavController().navigate(R.id.showFragment, bundleOf("keyBelgi" to rule))
            }
        })
        binding.viewPagerBelgilar.adapter = viewPagerAdapter


        binding.tabCategory.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val customView = tab?.customView
                    customView?.txt_tab_item?.background =
                        resources.getDrawable(R.drawable.tab_fon_selected)
                    customView?.txt_tab_item?.setTextColor(resources.getColor(R.color.blue))
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val customView = tab?.customView
                    customView?.txt_tab_item?.background =
                        resources.getDrawable(R.drawable.tab_fon_unselected)
                    customView?.txt_tab_item?.setTextColor(resources.getColor(R.color.white))
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })

        val tabList = ArrayList<String>()
        tabList.add("Ogohlantiruvchi")
        tabList.add("Imtiyozli")
        tabList.add("Ta'qiqlovchi")
        tabList.add("Buyuruvchi")
        TabLayoutMediator(
            binding.tabCategory, binding.viewPagerBelgilar
        )
        { tab, position ->
            val tabView =
                LayoutInflater.from(context).inflate(R.layout.tab_item_category, null, false)

            tab.customView = tabView

            tabView.txt_tab_item.text = tabList[position]

            binding.viewPagerBelgilar.setCurrentItem(tab.position, true)
        }.attach()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("result", "onActivityResult MyHomeFragment")
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}