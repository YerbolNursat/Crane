package com.example.crane.ui.base

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.crane.modal.ModalBottomSheetDialog

abstract class BaseFragment :Fragment(){
    lateinit var modalBottomSheetDialogService: ModalBottomSheetDialog

    fun hideKeyBoard() {
        val imm =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
    fun bottomIsShowing(): Boolean {
        return try {
            modalBottomSheetDialogService.isShowing
        } catch (e: Exception) {
            false
        }
    }

}