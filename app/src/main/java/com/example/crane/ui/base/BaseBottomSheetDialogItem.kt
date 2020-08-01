package com.example.crane.ui.base

import androidx.databinding.ViewDataBinding
import com.xwray.groupie.databinding.BindableItem

abstract class BaseBottomSheetDialogItem : BindableItem<ViewDataBinding>() {
    var actionForClose: (() -> Unit)? = null
}