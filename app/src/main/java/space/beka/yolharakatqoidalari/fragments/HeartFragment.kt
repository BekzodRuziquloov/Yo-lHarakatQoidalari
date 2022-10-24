package space.beka.yolharakatqoidalari.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import space.beka.yolharakatqoidalari.R
import space.beka.yolharakatqoidalari.adapters.RvAdapter
import space.beka.yolharakatqoidalari.adapters.RvItemClick
import space.beka.yolharakatqoidalari.databinding.FragmentHeartBinding
import space.beka.yolharakatqoidalari.db.MyDbHelper
import space.beka.yolharakatqoidalari.models.Rule

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HeartFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentHeartBinding
    lateinit var rvAdapter: RvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeartBinding.inflate(LayoutInflater.from(context))


        return binding.root
    }


    lateinit var myDbHelper: MyDbHelper
    override fun onResume() {
        super.onResume()
        myDbHelper = MyDbHelper(context)
        val list = myDbHelper.getAllLabel()
        val likeList = ArrayList<Rule>()
        for (belgi in list) {
            if (belgi.like == 1){
                likeList.add(belgi)
            }
        }
        rvAdapter = RvAdapter(likeList, object : RvItemClick {
            override fun edit(belgi: Rule) {
                findNavController().navigate(R.id.editFragment, bundleOf("label" to belgi))
            }

            override fun delete(belgi: Rule) {
                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("Are you agree?")
                dialog.setMessage("${belgi.name} Delete?")
                dialog.setPositiveButton("Yes"
                ) { dialog, which ->
                    myDbHelper.deleteLabel(belgi)
                    onResume()
                }

                dialog.setNegativeButton("No "
                ) { dialog, which ->
                    dialog.cancel()
                }
                dialog.show()
            }

            override fun like(belgi: Rule) {
                if (belgi.like == 0) {
                    belgi.like = 1
                } else {
                    belgi.like = 0
                }
                myDbHelper.editLabel(belgi)
                onResume()
            }

            override fun itemClick(belgi: Rule) {
                findNavController().navigate(R.id.showFragment, bundleOf("keyBelgi" to belgi))
            }
        })
        binding.rvHeart.adapter = rvAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HeartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}