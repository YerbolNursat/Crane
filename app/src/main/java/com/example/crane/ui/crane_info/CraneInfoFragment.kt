package com.example.crane.ui.crane_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.crane.R
import com.example.crane.base.BaseFragment
import com.example.crane.custom_view.CustomToast
import com.example.crane.databinding.FragmentCraneInfoBinding
import com.example.crane.databinding.FragmentCraneTypesBinding
import com.example.crane.events.Event
import com.example.crane.ui.crane_types.CraneTypesViewModel
import com.example.crane.ui.items.CraneInfoUi
import com.example.crane.ui.items.CraneTypeUi
import com.example.crane.utils.getCraneInfoResponseFromAssetFile
import com.google.android.material.tabs.TabLayout
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_crane_full_info.*
import kotlinx.android.synthetic.main.fragment_crane_info.*
import kotlinx.android.synthetic.main.fragment_crane_info.btn_apply
import kotlinx.android.synthetic.main.fragment_crane_info.ic_back
import kotlinx.android.synthetic.main.fragment_crane_info.root_cl
import org.koin.androidx.viewmodel.ext.android.viewModel

class CraneInfoFragment : BaseFragment() {

    private val viewModel: CraneInfoViewModel by viewModel()
    private lateinit var binding: FragmentCraneInfoBinding
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCraneInfoBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
            lifecycleOwner = this@CraneInfoFragment.viewLifecycleOwner
        }
        initBackDispatcher()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnClickListener()
        initRecyclerView()
    }

    private fun initOnClickListener() {
        ic_back.setOnClickListener {
            activity?.onBackPressed()
        }
        btn_apply.setOnClickListener {
            viewModel.checkOnCompleteness()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::onItemsChanged))
        viewModel.newDestination.observe(viewLifecycleOwner, Observer(::onNavigate))

    }

    private fun onNavigate(event: Event<Boolean>) {
        if (event.peek()) {
            findNavController().navigate(R.id.action_craneInfoFragment_to_craneFullInfoFragment)
        } else {
            CustomToast(root_cl).showMessage("Заполните поля")
        }
    }

    private fun onItemsChanged(data: List<CraneInfoUi>) {
        groupAdapter.clear()
        groupAdapter.addAll(data)
        groupAdapter.setOnItemClickListener { item, _ ->
            item as CraneInfoUi

        }
    }

    private fun initRecyclerView() {
        binding.craneInfoRv.apply {
            adapter = groupAdapter
        }
    }




    override fun onResume() {
        super.onResume()
        viewModel.requestItems(requireContext())
    }
}