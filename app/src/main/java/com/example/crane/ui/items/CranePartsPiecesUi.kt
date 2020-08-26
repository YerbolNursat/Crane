package com.example.crane.ui.items

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ViewDataBinding
import com.example.crane.BR
import com.example.crane.R
import com.example.crane.databinding.ItemCranePartPiecesBinding
import com.example.ui_components.modal.ModalBottomSheetDialog
import com.xwray.groupie.databinding.BindableItem

data class CranePartPiecesUi(
    val name: String,
    val actionToCheck: (() -> Unit)
) : BindableItem<ViewDataBinding>() {
    val value = ValuePartPieces()
    lateinit var context: Context
    private val action: ((Int, String?) -> Unit) = { id, comment ->
        when (id) {
            0 -> {
                value.satisfactory = true
                value.unsatisfactory = false
            }
            1 -> {
                value.satisfactory = false
                value.unsatisfactory = true

            }
        }
        value.comments = comment
        actionToCheck()
    }

    override fun getLayout(): Int {
        return R.layout.item_crane_part_pieces
    }

    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        context = viewBinding.root.context
        when (viewBinding) {
            is ItemCranePartPiecesBinding -> {
                viewBinding.data = this
            }
        }

    }

    fun onClick() {
        val satisfactory = CraneStatusUI(
            id = 0,
            name = "Удовлетворительно"
        )
        satisfactory.value.clicked = value.satisfactory ?: false

        val unsatisfactory = CraneStatusUI(
            id = 1,
            name = "Неудовлетворительно"
        )
        unsatisfactory.value.clicked = value.unsatisfactory ?: false


        val list: MutableList<CraneChooseStateUi> = mutableListOf()
        list.add(
            CraneChooseStateUi(listOf(satisfactory, unsatisfactory), value.comments, action)
        )
        ModalBottomSheetDialog(
            items = list,
            context1 = context,
            transparent = true
        )
    }

}

class ValuePartPieces : BaseObservable() {
    @Bindable
    var filled: Boolean? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.filled)
        }

    @Bindable
    var satisfactory: Boolean? = null
        set(value) {
            field = value
            filled = true
            notifyPropertyChanged(BR.satisfactory)
        }

    @Bindable
    var unsatisfactory: Boolean? = null
        set(value) {
            field = value
            filled = true
            notifyPropertyChanged(BR.unsatisfactory)
        }

    @Bindable
    var comments: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.comments)
        }
}