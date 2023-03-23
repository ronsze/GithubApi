package kr.akaai.homework.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kr.akaai.homework.R

abstract class BaseFragment<T: ViewDataBinding, V: BaseViewModel>: Fragment() {
    protected lateinit var binding: T
    abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
    }

    abstract fun observeViewModel()

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    protected fun startActivitySlide(intent: Intent) {
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.anim_slide_right, R.anim.anim_slide_left)
    }
}