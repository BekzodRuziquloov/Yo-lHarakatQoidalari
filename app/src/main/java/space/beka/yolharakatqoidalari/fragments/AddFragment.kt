package space.beka.yolharakatqoidalari.fragments

import android.Manifest
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import space.beka.yolharakatqoidalari.databinding.FragmentAddBinding
import space.beka.yolharakatqoidalari.db.MyDbHelper
import space.beka.yolharakatqoidalari.models.Rule
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentAddBinding
    lateinit var myDbHelper: MyDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(LayoutInflater.from(context))


        binding.imageAdd.setOnClickListener {

                getImageContent.launch("image/*")

                    AlertDialog.Builder(context)
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->

                        } //ask again
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss();
                        }
                        .show();
                }



        return binding.root
    }



    var absolutePath = ""
    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it ?: return@registerForActivityResult
            binding.imageAdd.setImageURI(it)
            val inputStream = requireActivity().contentResolver.openInputStream(it)
            val title = SimpleDateFormat("yyyyMMdd_hhmmss").format(Date())
            val file = File(requireActivity().filesDir, "$title.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)
            inputStream?.close()
            fileOutputStream.close()
            absolutePath = file.absolutePath
        }

    override fun onStart() {
        super.onStart()
        Log.d("OnStart", "QoshishFragment")
        myDbHelper = MyDbHelper(context)
        binding.btnSave.setOnClickListener {

            val labelName = binding.edtBelgiNomi.text.toString().trim()
            val labelTarifi = binding.edtBelgiTarifi.text.toString().trim()
            val category = binding.spinnerTuri.selectedItemPosition

            if (absolutePath!="" && labelName!="" && labelTarifi!=""){
                myDbHelper.addLabel(Rule(labelName, labelTarifi, absolutePath, 0, category))
                Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Ma'lumot yetarli emas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}