package com.example.crane.ui.crane_types

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.crane.ui.items.CranePartsUi
import com.example.crane.ui.items.CraneTypeUi

class CraneTypesViewModel : ViewModel() {
    private val _items = MutableLiveData<List<CraneTypeUi>>()
    val items: LiveData<List<CraneTypeUi>> = _items

    val loadingChecker = MutableLiveData<Boolean>()
    val loading = Transformations.map(loadingChecker) {
        it
    }

    fun requestItems(
        craneTypeUi: List<CraneTypeUi>?
    ) {
        val list: MutableList<CraneTypeUi> = mutableListOf()
        list.add(CraneTypeUi(1, "Механизм передвижения крана"))
        list.add(CraneTypeUi(2, "Механизм передвижения тали"))
        list.add(CraneTypeUi(3, "Механизм подъема"))
        list.add(CraneTypeUi(4, "Металлоконструкций"))

        list.forEach {
            craneTypeUi?.forEach { craneTypeUi ->
                if (it.id == craneTypeUi.id) {
                    it.value.cranePartsUiMech = craneTypeUi.value.cranePartsUiMech
                    it.value.cranePartsUiEl = craneTypeUi.value.cranePartsUiEl
                    it.value.cranePartsUiConstr = craneTypeUi.value.cranePartsUiConstr
                }

            }
        }

        _items.value = list

    }

    fun checkOnCompleteness(): Boolean {
        _items.value?.forEach {
            if (it.value.cranePartsUiConstr.isNullOrEmpty() && it.value.cranePartsUiEl.isNullOrEmpty() &&
                it.value.cranePartsUiMech.isNullOrEmpty()
            ) {
                return false
            }
        }
        return true
    }
}