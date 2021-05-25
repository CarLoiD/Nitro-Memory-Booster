package com.carloid.nitromemorybooster.util

import com.carloid.nitromemorybooster.model.UnusedFile
import com.carloid.nitromemorybooster.view.fragment.UnusedFilesFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class KotlinUtil {
    companion object {
        private const val EXTERNAL_STORAGE_DIR = "/storage/emulated/0"

        fun listDirObjects(file: File, out: Vector<String>, minSize: Float = 0.0f, extensionList: List<String> = listOf("."), onFinishCallback: (size: Long) -> Unit) {
            CoroutineScope(Dispatchers.Main).launch {
                listDirObjectsInternal(file, out, minSize, extensionList, onFinishCallback)
            }
        }

        fun listDirObjectsUnused(out: Vector<UnusedFile>, minSize: Float = 0.0f, onFinishCallback: (size: Long) -> Unit) {
            CoroutineScope(Dispatchers.Main).launch {
                listDirObjectsUnusedInternal(out, minSize, onFinishCallback)
            }
        }

        private suspend fun listDirObjectsInternal(file: File, out: Vector<String>, minSize: Float = 0.0f, extensionList: List<String> = listOf("."), onFinishCallback: (size: Long) -> Unit) {
            withContext(Dispatchers.Default) {
                var byteSize: Long = 0

                file.walk(FileWalkDirection.TOP_DOWN).filter {
                    val fileSize = U.bytesToMegaBytes(it.length())
                    fileSize > minSize
                }.forEach {
                    val fileExtension = "." + it.path.substringAfterLast('.')
                    if (extensionList.contains(fileExtension)) {
                        byteSize += it.length()
                        out.add(it.path)
                    }
                }

                withContext(Dispatchers.Main) {
                    onFinishCallback(byteSize)
                }
            }
        }

        private suspend fun listDirObjectsUnusedInternal(out: Vector<UnusedFile>, minSize: Float = 0.0f, onFinishCallback: (size: Long) -> Unit) {
            withContext(Dispatchers.Default) {
                var byteSize: Long = 0

                var file = File(EXTERNAL_STORAGE_DIR)
                while (!file.isDirectory) {
                    file = File(EXTERNAL_STORAGE_DIR)
                }

                file.walk(FileWalkDirection.TOP_DOWN).filter {
                    val fileSizeMb = U.bytesToMegaBytes(it.length())
                    fileSizeMb > minSize
                }.forEach {
                    val strippedPath = it.path.substringAfter("$EXTERNAL_STORAGE_DIR/")

                    byteSize += it.length()
                    out.add(UnusedFile(it.name, strippedPath, it.length()))
                }

                withContext(Dispatchers.Main) {
                    onFinishCallback(byteSize)
                }
            }
        }
    }
}