package space.beka.yolharakatqoidalari.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import space.beka.yolharakatqoidalari.R
import space.beka.yolharakatqoidalari.databinding.FragmentShowBinding
import space.beka.yolharakatqoidalari.models.Rule

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ShowFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding:FragmentShowBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentShowBinding.inflate(LayoutInflater.from(context))
        val belgi = arguments?.getSerializable("keyBelgi") as Rule

        binding.imageShow.setImageURI(Uri.parse(belgi.imagePath))
        binding.txtShowNomi.text = belgi.name
        binding.txtAboutShow.text = belgi.matni

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.d("OnStart", "ShowBelgiFragment")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}