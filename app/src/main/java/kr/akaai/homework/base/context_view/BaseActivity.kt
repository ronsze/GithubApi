package kr.akaai.homework.base.context_view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import kr.akaai.homework.R
import kr.akaai.homework.base.viewmodel.BaseViewModel

abstract class BaseActivity<out B: ViewDataBinding, out V: BaseViewModel>(
    private val inflate: (LayoutInflater) -> B
): AppCompatActivity() {
    private var _binding: B? = null
    val binding: B get() = _binding!!

    abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        afterBinding()
        observeViewModel()
        viewModel.baseEvent.observe(this) {
            handleBaseEvent(it)
        }
    }

    abstract fun afterBinding()
    abstract fun observeViewModel()
    private fun handleBaseEvent(event: BaseViewModel.BaseEvent) {
        when (event) {
            is BaseViewModel.BaseEvent.ShowToast -> showToast(event.msg)
        }
    }

    protected fun showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    protected fun startActivitySlide(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_right, R.anim.anim_slide_left)
    }

    protected fun backSlideFinish() {
        super.finish()
        overridePendingTransition(R.anim.backpressed_anim_slide_left, R.anim.backpressed_anim_slide_right)
    }
}