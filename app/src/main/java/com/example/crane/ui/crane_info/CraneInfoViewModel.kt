package com.example.crane.ui.crane_info

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crane.entities.CraneInfoResponse
import com.example.crane.ui.items.CraneInfoSubQuestionsUi
import com.example.crane.ui.items.CraneInfoUi
import com.example.crane.utils.getCraneInfoResponseFromAssetFile

class CraneInfoViewModel : ViewModel() {
    private val _items = MutableLiveData<List<CraneInfoUi>>()
    val items: LiveData<List<CraneInfoUi>> = _items

    fun requestItems(context: Context) {
        val response = getCraneInfoResponseFromAssetFile(context)
        _items.value = transformDataToCraneInfoUi(response)
    }

    private fun transformDataToCraneInfoUi(response: CraneInfoResponse): List<CraneInfoUi> {
        return response.list.map {
            CraneInfoUi(
                required = it.required,
                question = it.question,
                subQuestions = it.subQuestions.map { subQuestion ->
                    CraneInfoSubQuestionsUi(
                        question = subQuestion.question
                    )
                }
            )
        }
    }
}