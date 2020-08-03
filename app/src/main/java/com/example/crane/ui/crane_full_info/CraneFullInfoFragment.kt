package com.example.crane.ui.crane_full_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crane.base.BaseFragment
import com.example.crane.databinding.FragmentCraneFullInfoBinding
import com.example.crane.databinding.FragmentCraneInfoBinding
import com.example.crane.ui.crane_info.CraneInfoViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class CraneFullInfoFragment : BaseFragment() {

    private val viewModel: CraneInfoViewModel by viewModel()
    private lateinit var binding: FragmentCraneFullInfoBinding
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCraneFullInfoBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
            lifecycleOwner = this@CraneFullInfoFragment.viewLifecycleOwner
        }
        initBackDispatcher()
        return binding.root
    }
}