package com.example.crane.ui.crane_full_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.crane.R
import com.example.ui_components.base.BaseFragment
import com.example.ui_components.custom_view.CustomToast
import com.example.crane.databinding.FragmentCraneFullInfoBinding
import com.example.crane.ui.items.CraneInfoUi
import com.example.crane.ui.items.CranePartsUi
import com.example.crane.ui.items.CraneTypeUi
import com.google.android.material.tabs.TabLayout
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_crane_full_info.*
import timber.log.Timber
import org.koin.androidx.viewmodel.ext.android.viewModel

class CraneFullInfoFragment : BaseFragment() {

    private val viewModel: CraneFullInfoViewModel by viewModel()
    private lateinit var binding: FragmentCraneFullInfoBinding
    private val groupAdapterMech = GroupAdapter<GroupieViewHolder>()
    private val groupAdapterEl = GroupAdapter<GroupieViewHolder>()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            Timber.i(it.getInt("id").toString())
            Timber.i(it.get("craneTypeUi").toString())
            Timber.i(it.getString("header_title").toString())
        }
        binding.headerTitle.text = arguments?.getString("header_title")!!
        viewModel.requestItems(
            requireContext(),
            arguments?.getInt("id")!!,
            arguments?.get("craneTypeUi") as List<CraneTypeUi>?
        )
        initRecyclerView()
        initTabLayout()
        initOnClickListener()

    }

    private fun initOnClickListener() {
        ic_back.setOnClickListener {
            activity?.onBackPressed()
        }
        btn_apply.setOnClickListener {
            if (viewModel.checkOnCompleteness()){
                viewModel.setData()
                findNavController().navigate(
                    R.id.action_craneFullInfoFragment_to_navigation_crane_types,
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
        binding.mechRv.apply {
            adapter = groupAdapterMech
        }
        binding.elRv.apply {
            adapter = groupAdapterEl
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.itemsMech.observe(viewLifecycleOwner, Observer(::onItemsMechChanged))
        viewModel.itemsEl.observe(viewLifecycleOwner, Observer(::onItemsElChanged))


    }

    private fun onItemsElChanged(data: List<CranePartsUi>) {
        groupAdapterEl.addAll(data)
        groupAdapterEl.notifyDataSetChanged()
    }

    private fun onItemsMechChanged(data: List<CranePartsUi>) {
        groupAdapterMech.addAll(data)
        groupAdapterMech.notifyDataSetChanged()
    }

    private fun initTabLayout() {
        tabLayout.addTab(tabLayout.newTab())
        tabLayout.addTab(tabLayout.newTab())

        tabLayout.getTabAt(0)?.text = "Механическая"
        tabLayout.getTabAt(1)?.text = "Электрическая"

        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        binding.mechRv.visibility = View.VISIBLE
                        binding.mechRv.scheduleLayoutAnimation()
                        binding.elRv.visibility = View.GONE
                    }

                    1 -> {
                        binding.mechRv.visibility = View.GONE
                        binding.elRv.visibility = View.VISIBLE
                        binding.elRv.scheduleLayoutAnimation()

                    }

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        binding.mechRv.visibility = View.VISIBLE
                        binding.mechRv.scheduleLayoutAnimation()
                        binding.elRv.visibility = View.GONE
                    }

                    1 -> {
                        binding.mechRv.visibility = View.GONE
                        binding.elRv.visibility = View.VISIBLE
                        binding.elRv.scheduleLayoutAnimation()
                    }

                }

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        binding.mechRv.visibility = View.VISIBLE
                        binding.mechRv.scheduleLayoutAnimation()
                        binding.elRv.visibility = View.GONE
                    }

                    1 -> {
                        binding.mechRv.visibility = View.GONE
                        binding.elRv.visibility = View.VISIBLE
                        binding.elRv.scheduleLayoutAnimation()

                    }

                }

            }
        })

        tabLayout.selectTab(tabLayout.getTabAt(0))
    }

}