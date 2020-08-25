package com.example.crane.ui.items

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ViewDataBinding
import com.example.crane.BR
import com.example.crane.R
import com.example.crane.databinding.ItemCraneTypeBinding
import com.xwray.groupie.databinding.BindableItem

data class CraneTypeUi(
    val id: Int,
    val name: String
) : BindableItem<ViewDataBinding>() {
    val value = ValueType()
    override fun getLayout(): Int {
        return R.layout.item_crane_type
    }

    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemCraneTypeBinding -> {
                viewBinding.data = this
                if (value.cranePartsUiConstr.isNullOrEmpty() && value.cranePartsUiEl.isNullOrEmpty() && value.cranePartsUiMech.isNullOrEmpty()) {
                    viewBinding.checkTv.text = "Заполните"
                } else {
                    viewBinding.checkTv.text = "Заполнено"
                }
            }
        }
    }

}

class ValueType : BaseObservable() {
    @Bindable
    var cranePartsUiConstr: List<CranePartsUi>? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.cranePartsUiConstr)
        }

    @Bindable
    var cranePartsUiMech: List<CranePartsUi>? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.cranePartsUiMech)
        }

    @Bindable
    var cranePartsUiEl: List<CranePartsUi>? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.cranePartsUiEl)
        }

}