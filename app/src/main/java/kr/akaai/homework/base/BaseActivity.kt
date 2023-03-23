package kr.akaai.homework.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import kr.akaai.homework.R

abstract class BaseActivity<T: ViewDataBinding, V: BaseViewModel>: AppCompatActivity() {
    protected lateinit var binding: T
    abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        observeViewModel()
    }

    abstract fun initDataBinding()
    abstract fun observeViewModel()

    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun startActivitySlide(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_right, R.anim.anim_slide_left)
    }

    protected fun backSlideFinish() {
        super.finish()
        overridePendingTransition(R.anim.backpressed_anim_slide_left, R.anim.backpressed_anim_slide_right)
    }
}