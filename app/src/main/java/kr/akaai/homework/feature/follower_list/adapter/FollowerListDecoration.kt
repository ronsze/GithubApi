package kr.akaai.homework.feature.follower_list.adapter

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FollowerListDecoration(
    private val context: Context
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val lp: GridLayoutManager.LayoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val spanIndex: Int =  lp.spanIndex
        val position = parent.getChildAdapterPosition(view)

        when (position) {
            0, 1, 2 -> outRect.top = dpToPx(10f)
        }
        outRect.bottom = dpToPx(10f)
        if(spanIndex == 0) {
            // left item
            outRect.left = dpToPx(10f)
            outRect.right = dpToPx(5f)
        } else if(spanIndex == 1) {
            //  right item
            outRect.left = dpToPx(5f)
            outRect.right = dpToPx(10f)
        }
    }

    private fun dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
}