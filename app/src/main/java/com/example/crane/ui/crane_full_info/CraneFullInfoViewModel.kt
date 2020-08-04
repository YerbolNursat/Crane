package com.example.crane.ui.crane_full_info

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crane.entities.CraneElInfo
import com.example.crane.entities.CraneMechInfo
import com.example.crane.events.Event
import com.example.crane.ui.items.CranePartPiecesUi
import com.example.crane.ui.items.CranePartsUi
import com.example.crane.utils.getCraneElInfoResponseFromAssetFile
import com.example.crane.utils.getCraneMechInfoResponseFromAssetFile
import com.hadilq.liveevent.LiveEvent

class CraneFullInfoViewModel : ViewModel() {
    private val _itemsMech = MutableLiveData<List<CranePartsUi>>()
    val itemsMech: LiveData<List<CranePartsUi>> = _itemsMech

    private val _itemsEl = MutableLiveData<List<CranePartsUi>>()
    val itemsEl: LiveData<List<CranePartsUi>> = _itemsEl

    val newDestination = LiveEvent<Event<Boolean>>()

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

    fun requestItems(context: Context, id: Int) {
        val responseMech = getCraneMechInfoResponseFromAssetFile(context, id)
        val responseEl = getCraneElInfoResponseFromAssetFile(context, id)
        responseMech?.let {
            _itemsMech.value = transformDataToCranePartsUi(responseMech)
        }
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
}