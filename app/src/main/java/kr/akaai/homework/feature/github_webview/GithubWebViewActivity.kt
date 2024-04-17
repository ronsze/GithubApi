package kr.akaai.homework.feature.github_webview

import androidx.activity.viewModels
import kr.akaai.homework.Constants
import kr.akaai.homework.Constants.GITHUB_URL
import kr.akaai.homework.base.context_view.BaseActivity
import kr.akaai.homework.databinding.ActivityGithubWebViewBinding

class GithubWebViewActivity : BaseActivity<ActivityGithubWebViewBinding, GithubWebViewViewModel>(ActivityGithubWebViewBinding::inflate) {
    override val viewModel: GithubWebViewViewModel by viewModels()

    override fun afterBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        loadWebView(intent.getStringExtra(GITHUB_URL) ?: "")
    }

    override fun observeViewModel() {}

    private fun loadWebView(url: String) {
        binding.webView.apply {
            loadUrl(url)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backSlideFinish()
    }


}