package com.example.crane.ui.items

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ViewDataBinding
import com.example.crane.BR
import com.example.crane.R
import com.example.crane.databinding.ItemCranePartsBinding
import com.xwray.groupie.databinding.BindableItem

data class CranePartsUi(
    val name: String,
    val pieces: List<CranePartPiecesUi>
) : BindableItem<ViewDataBinding>() {
    val value = ValueParts()

    override fun getLayout(): Int {
        return R.layout.item_crane_parts
    }

    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemCranePartsBinding -> {
                viewBinding.data = this
                val rvView = viewBinding.rvServices
                viewBinding.toShow.setOnClickListener {
                    when (rvView.visibility) {
                        View.VISIBLE -> {
                            rvView.visibility = View.GONE
                            value.openView = false
                        }
                        View.GONE -> {
                            rvView.visibility = View.VISIBLE
                            rvView.scheduleLayoutAnimation()
                            value.openView = true
                        }
                    }
                }
            }
        }
    }

}

class ValueParts : BaseObservable() {
    @Bindable
    var openView: Boolean? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.openView)
        }

    @Bindable
    var filled: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.filled)
        }
}