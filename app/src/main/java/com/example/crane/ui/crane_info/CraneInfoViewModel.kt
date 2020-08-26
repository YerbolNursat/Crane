package com.example.crane.ui.crane_info

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.ui_components.events.Event
import com.example.crane.ui.items.CraneInfoSubQuestionsUi
import com.example.crane.ui.items.CraneInfoUi
import com.example.crane.utils.getCraneInfoResponseFromAssetFile
import com.example.domain.entities.CraneInfoResponse
import com.example.domain.usecases.questions.CreateQuestionUseCase
import com.example.domain.usecases.questions.GetAllQuestionsUseCase
import com.hadilq.liveevent.LiveEvent
import timber.log.Timber

class CraneInfoViewModel(
    val createQuestionUseCase: CreateQuestionUseCase,
    val getAllQuestionsUseCase: GetAllQuestionsUseCase
) : ViewModel() {
    private val _items = MutableLiveData<List<CraneInfoUi>>()
    val items: LiveData<List<CraneInfoUi>> = _items

    val newDestination = LiveEvent<Event<Boolean>>()

    fun requestItems(
        context: Context
    ) {
        val response = getCraneInfoResponseFromAssetFile(context)
        _items.value = transformDataToCraneInfoUi(response)
//        response.list.forEachIndexed { index, question ->
//            createQuestionUseCase(viewModelScope, question)
//            Timber.i("Created")
//        }
        getAllQuestionsUseCase(viewModelScope, Unit) {
            Timber.i(it.toString())
        }


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
        _items.value?.forEach {
            if (it.required) {
                if (it.answer.isNullOrEmpty() && it.subQuestions.isEmpty()) {
                    newDestination.value = Event(false)
                    return
                }
                it.subQuestions.forEach { subQuestion ->
                    if (subQuestion.answer.isNullOrEmpty()) {
                        newDestination.value = Event(false)
                        return
                    }
                }
            }
        }
        newDestination.value = Event(true)
    }

}