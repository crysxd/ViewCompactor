package de.crysxd.viewcompactor

import android.view.View
import android.view.ViewGroup

class ViewCompactor(
    private val view: ViewGroup,
    val reset: () -> Unit,
    val compact: (Int) -> Boolean
) {

    init {
        var first = true
        view.addOnLayoutChangeListener { _, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            val oldHeight = oldBottom - oldTop
            val oldWidth = oldRight - oldLeft
            val height = bottom - top
            val width = right - left

            if (first || (oldHeight != 0 && (height != oldHeight || width != oldWidth))) {
                first = false
                var round = -1
                reset()

                // Compact step by step until height is less than maxHeight or we have no more steps left
                while (isViewTooBig(height, width)) {
                    if (!compact(round++)) {
                        break
                    }
                }

                view.forceLayout()
                view.invalidate()
            }
        }
    }

    private fun isViewTooBig(maxHeight: Int, width: Int): Boolean {
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)

        view.measure(widthMeasureSpec, heightMeasureSpec)
        return view.measuredHeight > maxHeight
    }
}
