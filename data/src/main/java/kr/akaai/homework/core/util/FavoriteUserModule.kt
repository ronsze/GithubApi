package kr.akaai.homework.core.util

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.akaai.homework.core.util.Functions.toArrayList
import kr.akaai.homework.core.util.Functions.toJSONArray
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.net.URL

class FavoriteUserModule(
    private val context: Context,
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val IMAGE_FILE_PREFIX = "profile-image"
        const val FAVORITE_USER_LIST = "favorite_user_list"
    }

    suspend fun addUser(userId: String, imageSource: String): Throwable? {
        return withContext(Dispatchers.Default) {
            val fileName = "$IMAGE_FILE_PREFIX-$userId.jpg"
            val task = withContext(Dispatchers.IO) { saveUserImage(fileName, imageSource) }

            if (task != null) {
                withContext(Dispatchers.IO) { deleteUserImage() }
                task
            } else {
                saveFavoriteUserList(userId)
            }
        }
    }

    private fun saveFavoriteUserList(userId: String): Throwable? {
        val list: ArrayList<String> = getFavoriteUserList()

        return if (list.indexOf(userId) == -1) {
            list.add(userId)
            sharedPreferences.edit {
                putString(FAVORITE_USER_LIST, list.toJSONArray().toString())
            }
            null
        } else {
            Exception("Already added user")
        }
    }

    fun getFavoriteUserList(): ArrayList<String> {
        val str = sharedPreferences.getString(FAVORITE_USER_LIST, "")
        return if (str.isNullOrEmpty()) ArrayList()
        else JSONArray(str).toArrayList()
    }

    private fun saveUserImage(fileName: String, source: String): Throwable? {
        return try {
            val url = URL(source)
            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())

            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)

            val fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            fos.write(stream.toByteArray())
            fos.close()

            stream.close()
            null
        } catch (e: Exception) {
            e
        }
    }

    fun getFavoriteUserImage(userId: String): Bitmap? {
        return try {
            val fileName = "$IMAGE_FILE_PREFIX-$userId.jpg"

            val fis = context.openFileInput(fileName)
            val data = fis.readBytes()
            fis.close()

            BitmapFactory.decodeByteArray(data, 0, data.size)
        } catch (e: Exception) {
            null
        }
    }

    private fun deleteUserImage() {}
}