package kr.akaai.homework.feature.github_webview

import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun GithubWebView(
    githubUrl: String
) {
    Box {
        AndroidView(factory = {
            WebView(it).apply {

            }
        }, update = {
            it.loadUrl(githubUrl)
        })
    }
}