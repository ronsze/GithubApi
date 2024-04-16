package kr.akaai.homework.feature.github_webview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kr.akaai.homework.R
import kr.akaai.homework.base.context_view.BaseActivity
import kr.akaai.homework.databinding.ActivityGithubWebViewBinding

class GithubWebViewActivity : BaseActivity<ActivityGithubWebViewBinding, GithubWebViewViewModel>() {
    companion object {
        const val GITHUB_URL = "github_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_github_web_view)
        super.onCreate(savedInstanceState)
    }

    override val viewModel: GithubWebViewViewModel by viewModels()

    override fun initDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        loadWebView(intent.getStringExtra(GITHUB_URL) ?: "")
    }

    private fun loadWebView(url: String) {
        binding.webView.apply {
            loadUrl(url)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backSlideFinish()
    }

    override fun observeViewModel() {

    }
}