package com.carloid.nitromemorybooster.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.carloid.nitromemorybooster.R
import com.carloid.nitromemorybooster.adapter.UnusedFileAdapter
import com.carloid.nitromemorybooster.databinding.FragmentUnusedFilesBinding
import com.carloid.nitromemorybooster.model.UnusedFile
import com.carloid.nitromemorybooster.util.KotlinUtil
import com.carloid.nitromemorybooster.util.U
import java.util.*

class UnusedFilesFragment : Fragment() {

    private var binding: FragmentUnusedFilesBinding? = null
    private var adapter: UnusedFileAdapter? = null
    private val files = Vector<UnusedFile>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_unused_files, container, false)
        components()

        return binding!!.root
    }

    @SuppressLint("SetTextI18n")
    fun components() {
        val dialog = ProgressDialog(context)
        dialog.setMessage("Escaneando arquivos...\nAguarde por favor")
        dialog.setCancelable(false)
        dialog.show()

        KotlinUtil.listDirObjectsUnused(files, 100.0f) { size ->
            binding!!.txtSelectAllSize.text = U.bytesToHuman(size)

            // Setup recycler adapter
            adapter = UnusedFileAdapter(files) { position, checked ->
                if (!checked) {
                    binding!!.checkSelectAll.isChecked = false
                }

                files[position].delete = checked
                Log.d("CHECKED", "Position: $position Checked: $checked")
            }

            // Bind adapter
            binding!!.unusedFilesRecycler.adapter = adapter
            binding!!.unusedFilesRecycler.setHasFixedSize(false)
            binding!!.unusedFilesRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            binding!!.checkSelectAll.setOnCheckedChangeListener { _, isChecked ->
                val numItems = adapter!!.itemCount - 1
                for (index in 0..numItems) {
                    files[index].delete = isChecked         // Also update on outside
                    adapter!!.changeCheckedItem(index, isChecked)
                }
            }

            binding!!.btnClear.setOnClickListener {
                val numItems = adapter!!.itemCount - 1
                var totalBytesDeleted : Long = 0

                for (index in 0..numItems) {
                    if (files[index].delete) {
                        totalBytesDeleted += files[index].size
                        U.deleteFile(EXTERNAL_STORAGE_DIR + "/" + files[index].path)
                        Log.d("DELETE", "File: " + files[index].name)
                    }
                }

                Log.d("DELETE", "TotalSize: " + U.bytesToHuman(totalBytesDeleted))

                val successDialog = U.dialogCreate(context!!, R.layout.dialog_success_deletion, false)
                successDialog.findViewById<TextView>(R.id.amountDeleted).text = U.bytesToHuman(totalBytesDeleted) + " foram deletados!"
                successDialog.show()

                Handler(Looper.getMainLooper()).postDelayed({
                    gotoMainFragment()
                    successDialog.dismiss()
                }, 3000)
            }

            dialog.dismiss()
        }
    }

    private fun gotoMainFragment() {
        val mainFragment = MainFragment()

        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.replace(R.id.main_fragment_area, mainFragment)
        transaction.commit()
    }

    companion object {
        private const val EXTERNAL_STORAGE_DIR = "/storage/emulated/0"
    }
}