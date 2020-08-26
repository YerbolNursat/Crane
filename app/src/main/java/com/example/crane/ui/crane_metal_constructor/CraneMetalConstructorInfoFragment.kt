package com.example.crane.ui.crane_metal_constructor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.crane.R
import com.example.ui_components.custom_view.CustomToast
import com.example.crane.databinding.FragmentCraneMetalConstructorInfoBinding
import com.example.crane.ui.items.CraneInfoUi
import com.example.crane.ui.items.CranePartsUi
import com.example.crane.ui.items.CraneTypeUi
import com.example.ui_components.base.BaseFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_crane_metal_constructor_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CraneMetalConstructorInfoFragment : BaseFragment() {

    private val viewModel: CraneMetalConstructorInfoViewModel by viewModel()
    private lateinit var binding: FragmentCraneMetalConstructorInfoBinding
    private val groupAdapterConstr = GroupAdapter<GroupieViewHolder>()
    private val groupAdapterFormulas = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentCraneMetalConstructorInfoBinding.inflate(inflater, container, false).apply {
                viewModel = viewModel
                lifecycleOwner = this@CraneMetalConstructorInfoFragment.viewLifecycleOwner
            }
        initBackDispatcher()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.headerTitle.text = arguments?.getString("header_title")!!
        viewModel.requestItems(
            requireContext(),
            arguments?.getInt("id")!!,
            arguments?.get("craneTypeUi") as List<CraneTypeUi>?
        )
        initRecyclerView()
        initOnClickListener()

    }

    private fun initOnClickListener() {
        ic_back.setOnClickListener {
            activity?.onBackPressed()
        }
        btn_apply.setOnClickListener {
            if (viewModel.checkOnCompleteness()) {
                viewModel.setData()
                findNavController().navigate(
                    R.id.action_craneMetalConstructorFragment_to_navigation_crane_types,
                    bundleOf(
                        "craneTypeUi" to viewModel.items,
                        "id" to arguments?.getInt("id")!!,
                        "craneInfoUi" to arguments?.get("craneInfoUi") as List<CraneInfoUi>
                    )
                )
            } else {
                CustomToast(root_cl).showMessage("Заполните поля")
            }
        }
    }

    private fun initRecyclerView() {
        binding.constrRv.apply {
            adapter = groupAdapterConstr
        }
        binding.formulasRv.apply {
            adapter = groupAdapterFormulas
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.itemsConstr.observe(viewLifecycleOwner, Observer(::onItemsConstrChanged))
//        viewModel.itemsEl.observe(viewLifecycleOwner, Observer(::onItemsElChanged))

    }

    private fun onItemsConstrChanged(data: List<CranePartsUi>) {
        Timber.i("OnItemsChanged")
        groupAdapterConstr.addAll(data)
        groupAdapterConstr.notifyDataSetChanged()

    }

}