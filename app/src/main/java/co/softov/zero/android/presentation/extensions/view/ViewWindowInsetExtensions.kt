package co.softov.zero.android.presentation.extensions.view

import android.view.View
import android.view.ViewGroup
import androidx.core.view.*

fun View.addWindowInsetToPadding(
    @WindowInsetsCompat.Type.InsetsType insetType: Int = WindowInsetsCompat.Type.systemBars(),
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false
) {
    val (initialLeft, initialTop, initialRight, initialBottom) =
        listOf(paddingLeft, paddingTop, paddingRight, paddingBottom)

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        view.updatePadding(
            left = initialLeft + (if (left) insets.getInsets(insetType).left else 0),
            top = initialTop + (if (top) insets.getInsets(insetType).top else 0),
            right = initialRight + (if (right) insets.getInsets(insetType).right else 0),
            bottom = initialBottom + (if (bottom) insets.getInsets(insetType).bottom else 0)
        )

        insets
    }
}

fun View.addWindowInsetToMargin(
    @WindowInsetsCompat.Type.InsetsType insetType: Int = WindowInsetsCompat.Type.systemBars(),
    left: Boolean = false,
    top: Boolean = false,
    right: Boolean = false,
    bottom: Boolean = false
) {
    val (initialLeft, initialTop, initialRight, initialBottom) =
        listOf(marginLeft, marginTop, marginRight, marginBottom)

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        view.updateLayoutParams {
            (this as? ViewGroup.MarginLayoutParams)?.let {
                updateMargins(
                    left = initialLeft + (if (left) insets.getInsets(insetType).left else 0),
                    top = initialTop + (if (top) insets.getInsets(insetType).top else 0),
                    right = initialRight + (if (right) insets.getInsets(insetType).right else 0),
                    bottom = initialBottom + (if (bottom) insets.getInsets(insetType).bottom else 0)
                )
            }
        }

        insets
    }
}