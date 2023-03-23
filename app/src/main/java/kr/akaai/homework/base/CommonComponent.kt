package kr.akaai.homework.base

import android.app.Application
import android.content.SharedPreferences
import javax.inject.Inject

class CommonComponent @Inject constructor(
    val application: Application,
    val sharedPreferences: SharedPreferences
)