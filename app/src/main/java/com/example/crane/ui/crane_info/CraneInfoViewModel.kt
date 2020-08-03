package com.example.crane.ui.crane_info

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.crane.entities.CraneInfoResponse
import com.example.crane.events.Event
import com.example.crane.ui.items.CraneInfoSubQuestionsUi
import com.example.crane.ui.items.CraneInfoUi
import com.example.crane.utils.getCraneInfoResponseFromAssetFile
import com.hadilq.liveevent.LiveEvent

class CraneInfoViewModel : ViewModel() {
    private val _items = MutableLiveData<List<CraneInfoUi>>()
    val items: LiveData<List<CraneInfoUi>> = _items

    val newDestination = LiveEvent<Event<Boolean>>()

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

    fun checkOnCompleteness() {
//        _items.value?.forEach {
//            if (it.required) {
//                if (it.value.answer.isNullOrEmpty()) {
//                    newDestination.value = Event(false)
//                    return
//                }
//                it.subQuestions.forEach { subQuestion ->
//                    if (subQuestion.value.answer.isNullOrEmpty()) {
//                        newDestination.value = Event(false)
//                        return
//                    }
//                }
//            }
//        }
        newDestination.value = Event(true)
    }
}