package com.carloid.nitromemorybooster.view.fragment

import android.Manifest.permission
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.carloid.nitromemorybooster.R
import com.carloid.nitromemorybooster.databinding.FragmentMainBinding
import com.carloid.nitromemorybooster.util.KotlinUtil.Companion.listDirObjects
import com.carloid.nitromemorybooster.util.U
import java.io.File
import java.util.*

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        requestPermissions(PERMISSION_LIST, PERMISSION_REQUEST)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        components()

        return binding!!.root
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST) {
            for (grantIterator in grantResults) {
                if (grantIterator != PackageManager.PERMISSION_GRANTED) {
                    Objects.requireNonNull(activity)!!.finish()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun components() {
        val usable = usableMemory
        val free = freeMemory
        val percentage = (100.0f * (usable - free) / usable).toInt()

        binding!!.txtUsable.append(String.format(Locale.getDefault(), "%.1f GB", usable))
        binding!!.txtFree.append(String.format(Locale.getDefault(), "%.1f GB", free))
        binding!!.txtOSUsage.append(String.format(Locale.getDefault(), "%.1f GB", systemMemoryUsage))

        val percentStr = String.format(Locale.getDefault(), "%d", percentage) + "%"
        binding!!.percentage.text = percentStr

        val progressAnim = ObjectAnimator.ofInt(binding!!.progressBar, "progress", 0, percentage)
        progressAnim.duration = 1000
        progressAnim.interpolator = AccelerateDecelerateInterpolator()
        progressAnim.start()

        binding!!.cleanBtn.setOnClickListener {
            Log.d("TEST", "Click")
        }

        Handler(Looper.getMainLooper()).postDelayed({
            var externalDir = File(EXTERNAL_STORAGE_DIR)
            while (!externalDir.isDirectory) {
                externalDir = File(EXTERNAL_STORAGE_DIR)
            }

            // Image
            val imageList = Vector<String>()
            val imageFormats = listOf(".png", ".bmp", ".gif", ".webp")

            listDirObjects(externalDir, imageList, 0.0f, imageFormats) { size ->
                binding!!.txtUsedImages.text = String.format("%.1f GB", U.bytesToGigaBytes(size))
            }

            // Documents
            val documentList = Vector<String>()
            val docFormats = listOf(".pdf", ".doc", ".ppt", ".docx", ".pptx")

            listDirObjects(externalDir, documentList, 0.0f, docFormats) { size ->
                binding!!.txtUsedDocuments.text = String.format("%.1f GB", U.bytesToGigaBytes(size))
            }

            // Videos
            val videoList = Vector<String>()
            val vidFormats = listOf(".mp4", ".3gp", ".webm")

            listDirObjects(externalDir, videoList, 0.0f, vidFormats) { size ->
                binding!!.txtUsedVideos.text = String.format("%.1f GB", U.bytesToGigaBytes(size))
            }

            // Audios
            val audioList = Vector<String>()
            val audFormats = listOf(".mp3", ".ogg", ".wav")

            listDirObjects(externalDir, audioList, 0.0f, audFormats) { size ->
                binding!!.txtUsedAudios.text = String.format("%.1f GB", U.bytesToGigaBytes(size))
            }
        }, 500)
    }

    private val usableMemory: Float
        get() = U.getPathTotalSpace(EXTERNAL_STORAGE_DIR)

    private val freeMemory: Float
        get() = U.getPathFreeSpace(EXTERNAL_STORAGE_DIR)

    private val systemMemoryUsage: Float
        get() {
            val product = U.getPathTotalSpace("/product")
            val system = U.getPathTotalSpace("/system")
            return product + system
        }

    companion object {
        private const val PERMISSION_REQUEST = 10001
        private val PERMISSION_LIST = arrayOf(permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE)
        private const val EXTERNAL_STORAGE_DIR = "/storage/emulated/0"
    }
}