package kr.akaai.homework.base.context_view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kr.akaai.homework.R
import kr.akaai.homework.base.viewmodel.BaseViewModel

abstract class BaseFragment<out B: ViewDataBinding, out V: BaseViewModel>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> B
): Fragment() {
    private var _binding: B? = null
    val binding: B get() = _binding!!

    abstract val viewModel: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterBinding()
        observeViewModel()
    }

    abstract fun afterBinding()
    abstract fun observeViewModel()

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    protected fun startActivitySlide(intent: Intent) {
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.anim_slide_right, R.anim.anim_slide_left)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}