package com.example.crane.ui.crane_metal_constructor

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crane.ui.items.CranePartPiecesUi
import com.example.crane.ui.items.CranePartsUi
import com.example.crane.ui.items.CraneTypeUi
import com.example.crane.utils.getCraneConstrInfoResponseFromAssetFile
import com.example.domain.entities.CraneConstrInfo

class CraneMetalConstructorInfoViewModel : ViewModel() {
    private val _itemsConstr = MutableLiveData<List<CranePartsUi>>()
    val itemsConstr: LiveData<List<CranePartsUi>> = _itemsConstr

    var items: MutableList<CraneTypeUi> = mutableListOf()
    var id = 1
    private val actionToCheckConstrPieces: (() -> Unit) = {
        _itemsConstr.value?.forEach {
            var temp = false
            it.pieces.forEach {
                if (it.value.filled == null || it.value.filled == false)
                    temp = true
            }
            it.value.filled = temp
        }
    }

    fun requestItems(
        context: Context,
        id: Int,
        list: List<CraneTypeUi>?

    ) {
        this.id = id
        list?.let {
            items = it as MutableList<CraneTypeUi>
            it.forEach {
                if (it.id == id) {
                    if (!it.value.cranePartsUiConstr.isNullOrEmpty()) {
                        _itemsConstr.value = it.value.cranePartsUiConstr
                        return
                    }
                }
            }
        }
        val responseConstr = getCraneConstrInfoResponseFromAssetFile(context)
        responseConstr?.let {
            _itemsConstr.value = transformDataToCranePartsUi(responseConstr)
        }

    }

    private fun transformDataToCranePartsUi(response: CraneConstrInfo): List<CranePartsUi> {
        return response.list.map {
            CranePartsUi(
                name = it.name,
                pieces = it.pieces.map { pieces ->
                    CranePartPiecesUi(
                        pieces.name,
                        actionToCheckConstrPieces

                    )
                }
            )
        }
    }

    fun checkOnCompleteness(): Boolean {
        _itemsConstr.value?.forEach {
            if (it.value.filled) {
                return false
            }
        }
        return true
    }

    fun setData() {
        items.forEach {
            if (it.id == id ) {
                it.value.cranePartsUiConstr = itemsConstr.value
            }
        }
    }
}
