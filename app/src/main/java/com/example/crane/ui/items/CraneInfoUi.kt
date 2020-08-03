package com.example.crane.ui.items

import androidx.core.widget.doOnTextChanged
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crane.BR
import com.example.crane.R
import com.example.crane.databinding.ItemCraneInfoBinding
import com.example.crane.databinding.ItemCraneInfoSubquestionsBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.databinding.BindableItem

data class CraneInfoUi(
    val required: Boolean,
    val question: String,
    val subQuestions: List<CraneInfoSubQuestionsUi>
) : BindableItem<ViewDataBinding>() {
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    lateinit var binding: ItemCraneInfoBinding
    private val value = ValueQuestion()
    override fun getLayout(): Int {
        return R.layout.item_crane_info
    }

    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemCraneInfoBinding -> {
                viewBinding.data = this
                binding = viewBinding
                initRecyclerView()
                binding.answerEditText.doOnTextChanged { text, _, _, _ ->
                    value.answer = text.toString()
                }
            }
        }
    }

    private fun initRecyclerView() {
        groupAdapter.addAll(subQuestions)
        binding.subQuestionsRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter
            adapter?.notifyDataSetChanged()
        }
    }

}

data class CraneInfoSubQuestionsUi(
    val question: String
) : BindableItem<ViewDataBinding>() {
    lateinit var binding: ItemCraneInfoSubquestionsBinding
    private val value = ValueQuestion()

    override fun getLayout(): Int {
        return R.layout.item_crane_info_subquestions
    }

    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemCraneInfoSubquestionsBinding -> {
                viewBinding.data = this
                binding = viewBinding
                binding.answerEditText.doOnTextChanged { text, _, _, _ ->
                    value.answer = text.toString()
                }

            }

        }
    }

}

class ValueQuestion : BaseObservable() {
    @Bindable
    var answer: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.answer)
        }

}
