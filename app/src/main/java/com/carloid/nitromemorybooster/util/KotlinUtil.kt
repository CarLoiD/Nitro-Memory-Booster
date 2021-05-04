package com.carloid.nitromemorybooster.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class KotlinUtil {
    companion object {
        fun listDirObjects(file: File, out: Vector<String>, minSize: Float = 0.0f, extensionList: List<String> = listOf("."), onFinishCallback: (size: Long) -> Unit) {
            CoroutineScope(Dispatchers.Main).launch {
                listDirObjectsInternal(file, out, minSize, extensionList, onFinishCallback)
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
    }
}