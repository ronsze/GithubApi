package kr.akaai.homework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kr.akaai.homework.Constants.USER_INFO
import kr.akaai.homework.feature.home.HomeViewModel
import kr.akaai.homework.model.github.UserInfo

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeWorkApp()
        }
    }
}