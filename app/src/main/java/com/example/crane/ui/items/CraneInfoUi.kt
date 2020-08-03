package com.example.crane.ui.items

import androidx.databinding.ViewDataBinding
import com.example.crane.R
import com.example.crane.databinding.ItemCraneInfoBinding
import com.example.crane.databinding.ItemCraneInfoSubquestionsBinding
import com.example.crane.databinding.ItemCraneTypeBinding
import com.xwray.groupie.databinding.BindableItem

data class CraneInfoUi(
    val required: Boolean,
    val question: String,
    val subQuestions: List<CraneInfoSubQuestionsUi>
) : BindableItem<ViewDataBinding>() {

    override fun getLayout(): Int {
        return R.layout.item_crane_info
    }

    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemCraneInfoBinding -> {
                viewBinding.data = this
            }
        }
    }

}

data class CraneInfoSubQuestionsUi(
    val question: String
) : BindableItem<ViewDataBinding>() {

    override fun getLayout(): Int {
        return R.layout.item_crane_info_subquestions
    }

    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemCraneInfoSubquestionsBinding -> {
                viewBinding.data = this
            }

        }
    }

}
