package com.example.crane.ui.crane_types

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.crane.ui.base.BaseFragment
import com.example.crane.databinding.FragmentCraneTypesBinding
import com.example.crane.ui.items.CraneTypeUi
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class CraneTypesFragment : BaseFragment() {

    private val viewModel: CraneTypesViewModel by viewModel()
    private lateinit var binding: FragmentCraneTypesBinding
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCraneTypesBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
            lifecycleOwner = this@CraneTypesFragment.viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::onItemsChanged))

    }

    private fun onItemsChanged(data: List<CraneTypeUi>) {
        groupAdapter.clear()
        groupAdapter.addAll(data)
    }


    private fun initRecyclerView() {
        binding.craneTypesRv.apply {
            adapter = groupAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestItems()
    }
}