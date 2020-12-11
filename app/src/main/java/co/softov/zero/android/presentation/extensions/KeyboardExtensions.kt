package co.softov.zero.android.presentation.extensions

import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

fun Fragment.showKeyboard() {
    WindowInsetsControllerCompat(requireActivity().window, requireView()).show(
        WindowInsetsCompat.Type.ime()
    )
}

fun Fragment.hideKeyboard() {
    WindowInsetsControllerCompat(requireActivity().window, requireView()).hide(
        WindowInsetsCompat.Type.ime()
    )
}