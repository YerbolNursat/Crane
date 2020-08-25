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
import com.example.crane.events.Event
import com.example.crane.ui.items.CraneInfoUi
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_crane_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


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
        viewModel.requestItems(requireContext())
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
            hideKeyBoard()
            findNavController().navigate(
                R.id.action_craneInfoFragment_to_navigation_crane_types,
                bundleOf("craneInfoUi" to viewModel.items.value)
            )
        } else {
            CustomToast(root_cl).showMessage("Заполните поля")
        }

    }

    private fun onItemsChanged(data: List<CraneInfoUi>) {
        Timber.i("OnItemsChanged")
        groupAdapter.clear()
        groupAdapter.addAll(data)
    }

    private fun initRecyclerView() {
        binding.craneInfoRv.apply {
            adapter = groupAdapter
        }
    }

}