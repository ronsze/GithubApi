package kr.akaai.homework.core.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import org.json.JSONArray
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.net.URL

object Functions {
    fun getCacheImage(file: File, source: String): Bitmap? {
        return try {
            if (file.exists() || source.isEmpty()) {
                BitmapFactory.decodeStream(FileInputStream(file))
            } else {
                val url = URL(source)
                val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                createCacheImageFile(file, bitmap)
                bitmap
            }
        } catch (e: Exception) {
            Log.e("bitmap Create Fail", "${e}")
            null
        }
    }

    private fun createCacheImageFile(file: File, bitmap: Bitmap) {
        file.createNewFile()
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)
        out.close()
    }



    fun ArrayList<String>.toJSONArray(): JSONArray {
        val jsonArray = JSONArray()
        for (i in this) {
            jsonArray.put(i)
        }
        return jsonArray
    }

    fun JSONArray.toArrayList(): ArrayList<String> {
        val arrayList = ArrayList<String>()

        for (i in 0 until this.length()) {
            arrayList.add(this.getString(i))
        }

        return arrayList
    }
}