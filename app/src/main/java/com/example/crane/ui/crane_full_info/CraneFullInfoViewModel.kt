package com.example.crane.ui.crane_full_info

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crane.ui.items.CranePartPiecesUi
import com.example.crane.ui.items.CranePartsUi
import com.example.crane.ui.items.CraneTypeUi
import com.example.crane.utils.getCraneElInfoResponseFromAssetFile
import com.example.crane.utils.getCraneMechInfoResponseFromAssetFile
import com.example.domain.entities.CraneElInfo
import com.example.domain.entities.CraneMechInfo

class CraneFullInfoViewModel : ViewModel() {
    private val _itemsMech = MutableLiveData<List<CranePartsUi>>()
    val itemsMech: LiveData<List<CranePartsUi>> = _itemsMech

    private val _itemsEl = MutableLiveData<List<CranePartsUi>>()
    val itemsEl: LiveData<List<CranePartsUi>> = _itemsEl

    var items: MutableList<CraneTypeUi> = mutableListOf()
    var id = 1

    private val actionToCheckMechPieces: (() -> Unit) = {
        _itemsMech.value?.forEach {
            var temp = false
            it.pieces.forEach {
                if (it.value.filled == null || it.value.filled == false)
                    temp = true
            }
            it.value.filled = temp
        }
    }
    private val actionToCheckElPieces: (() -> Unit) = {
        _itemsEl.value?.forEach {
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
            items.forEach {
                if (it.id == id) {
                    if (!it.value.cranePartsUiMech.isNullOrEmpty() && !it.value.cranePartsUiEl.isNullOrEmpty()) {
                        _itemsMech.value = it.value.cranePartsUiMech
                        _itemsEl.value = it.value.cranePartsUiEl
                        return
                    }
                }
            }
        }
        val responseMech = getCraneMechInfoResponseFromAssetFile(context, this.id)
        responseMech?.let {
            _itemsMech.value = transformDataToCranePartsUi(responseMech)
        }
        val responseEl = getCraneElInfoResponseFromAssetFile(context, this.id)
        responseEl?.let {
            _itemsEl.value = transformDataToCranePartsUi(responseEl)
        }

    }

    private fun transformDataToCranePartsUi(response: CraneMechInfo): List<CranePartsUi> {
        return response.craneParts.map {
            CranePartsUi(
                name = it.name,
                pieces = it.pieces.map { pieces ->
                    CranePartPiecesUi(
                        pieces.name,
                        actionToCheckMechPieces

                    )
                }
            )
        }
    }

    private fun transformDataToCranePartsUi(response: CraneElInfo): List<CranePartsUi> {
        return response.craneParts.map {
            CranePartsUi(
                name = it.name,
                pieces = it.pieces.map { pieces ->
                    CranePartPiecesUi(
                        pieces.name,
                        actionToCheckElPieces
                    )
                }

            )
        }
    }

    fun checkOnCompleteness(): Boolean {
        _itemsEl.value?.forEach {
            if (it.value.filled) {
                return false
            }
        }
        _itemsMech.value?.forEach {
            if (it.value.filled) {
                return false
            }
        }
        return true
    }

    fun setData() {
        items.forEach {
            if (it.id == id) {
                it.value.cranePartsUiMech = itemsMech.value
                it.value.cranePartsUiEl = itemsEl.value
            }
        }
    }
}