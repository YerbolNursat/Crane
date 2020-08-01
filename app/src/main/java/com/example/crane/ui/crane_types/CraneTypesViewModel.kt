package com.example.crane.ui.crane_types

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crane.ui.items.CraneTypeUi

class CraneTypesViewModel : ViewModel() {
    private val _items = MutableLiveData<List<CraneTypeUi>>()
    val items: LiveData<List<CraneTypeUi>> = _items

    fun requestItems() {
        val list: MutableList<CraneTypeUi> = mutableListOf()
        list.add(CraneTypeUi(1, 1, "Механизм передвижения крана"))
        list.add(CraneTypeUi(2, 1, "Механизм передвижения тали"))
        list.add(CraneTypeUi(3, 1, "Механизм подъема"))
        _items.value = list
    }

}