package com.example.crane.ui.crane_full_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.crane.base.BaseFragment
import com.example.crane.databinding.FragmentCraneFullInfoBinding
import com.example.crane.ui.items.CranePartsUi
import com.google.android.material.tabs.TabLayout
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_crane_full_info.*
import kotlinx.android.synthetic.main.fragment_crane_full_info.ic_back
import kotlinx.android.synthetic.main.fragment_crane_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CraneFullInfoFragment : BaseFragment() {

    private val viewModel: CraneFullInfoViewModel by viewModel()
    private lateinit var binding: FragmentCraneFullInfoBinding
    private val groupAdapterMech = GroupAdapter<GroupieViewHolder>()
    private val groupAdapterEl = GroupAdapter<GroupieViewHolder>()
    private val groupAdapterConstr = GroupAdapter<GroupieViewHolder>()

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
        viewModel.requestItems(requireContext(), 1)
        initRecyclerView()
        initTabLayout()
        initOnClickListener()

    }

    private fun initOnClickListener() {
        ic_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun initRecyclerView() {
        binding.mechRv.apply {
            adapter = groupAdapterMech
        }
        binding.elRv.apply {
            adapter = groupAdapterEl
        }
        binding.constrRv.apply {
            adapter = groupAdapterConstr
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.itemsMech.observe(viewLifecycleOwner, Observer(::onItemsMechChanged))
        viewModel.itemsEl.observe(viewLifecycleOwner, Observer(::onItemsElChanged))

//        viewModel.newDestination.observe(viewLifecycleOwner, Observer(::onNavigate))

    }

    private fun onItemsElChanged(data: List<CranePartsUi>) {
        groupAdapterEl.addAll(data)
        groupAdapterEl.notifyDataSetChanged()
    }

    private fun onItemsMechChanged(data: List<CranePartsUi>) {
        groupAdapterMech.addAll(data)
        groupAdapterEl.notifyDataSetChanged()
    }

    private fun initTabLayout() {
        tabLayout.addTab(tabLayout.newTab())
        tabLayout.addTab(tabLayout.newTab())
        tabLayout.addTab(tabLayout.newTab())

        tabLayout.getTabAt(0)?.text = "Механическая"
        tabLayout.getTabAt(1)?.text = "Электрическая"
        tabLayout.getTabAt(2)?.text = "Конструктор"

        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        binding.mechRv.visibility = View.VISIBLE
                        binding.mechRv.scheduleLayoutAnimation()
                        binding.elRv.visibility = View.GONE
                        binding.constrRv.visibility = View.GONE
                    }

                    1 -> {
                        binding.mechRv.visibility = View.GONE
                        binding.elRv.visibility = View.VISIBLE
                        binding.elRv.scheduleLayoutAnimation()
                        binding.constrRv.visibility = View.GONE

                    }

                    2 -> {
                        binding.mechRv.visibility = View.GONE
                        binding.elRv.visibility = View.GONE
                        binding.constrRv.visibility = View.VISIBLE
                        binding.constrRv.scheduleLayoutAnimation()

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        binding.mechRv.visibility = View.VISIBLE
                        binding.mechRv.scheduleLayoutAnimation()
                        binding.elRv.visibility = View.GONE
                        binding.constrRv.visibility = View.GONE
                    }

                    1 -> {
                        binding.mechRv.visibility = View.GONE
                        binding.elRv.visibility = View.VISIBLE
                        binding.elRv.scheduleLayoutAnimation()
                        binding.constrRv.visibility = View.GONE

                    }

                    2 -> {
                        binding.mechRv.visibility = View.GONE
                        binding.elRv.visibility = View.GONE
                        binding.constrRv.visibility = View.VISIBLE
                        binding.constrRv.scheduleLayoutAnimation()

                    }
                }

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        binding.mechRv.visibility = View.VISIBLE
                        binding.mechRv.scheduleLayoutAnimation()
                        binding.elRv.visibility = View.GONE
                        binding.constrRv.visibility = View.GONE
                    }

                    1 -> {
                        binding.mechRv.visibility = View.GONE
                        binding.elRv.visibility = View.VISIBLE
                        binding.elRv.scheduleLayoutAnimation()
                        binding.constrRv.visibility = View.GONE

                    }

                    2 -> {
                        binding.mechRv.visibility = View.GONE
                        binding.elRv.visibility = View.GONE
                        binding.constrRv.visibility = View.VISIBLE
                        binding.constrRv.scheduleLayoutAnimation()

                    }
                }

            }
        })

        tabLayout.selectTab(tabLayout.getTabAt(0))
    }

}