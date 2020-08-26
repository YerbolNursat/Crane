package com.example.crane.ui.crane_types

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crane.R
import com.example.crane.base.BaseFragment
import com.example.crane.custom_view.CustomToast
import com.example.crane.databinding.FragmentCraneTypesBinding
import com.example.crane.ui.MainActivity
import com.example.crane.ui.items.CraneInfoUi
import com.example.crane.ui.items.CraneTypeUi
import com.example.crane.utils.*
import com.example.data.common.SharedPreferencesSetting
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_crane_types.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class CraneTypesFragment : BaseFragment() {

    private val viewModel: CraneTypesViewModel by viewModel()
    private lateinit var binding: FragmentCraneTypesBinding
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val sharedPreferencesSetting: SharedPreferencesSetting by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCraneTypesBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
            lifecycleOwner = this@CraneTypesFragment.viewLifecycleOwner
        }
        initBackDispatcher()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.requestItems(
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
                if (isStoragePermissionGranted(requireContext(), requireActivity())) {
                    val action: (() -> Unit) = {
                        viewModel.loadingChecker.value = false
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)

                        activity?.finish()
                    }

                    viewModel.loadingChecker.value = true
                    saveFileInPdf(
                        requireContext(),
                        root_cl,
                        sharedPreferencesSetting.firstName + " " + sharedPreferencesSetting.secondName + " " + sharedPreferencesSetting.lastName + " " + setTime(),
                        content = "<pre>" +
                                prepareInfoPdfData(
                                    arguments?.get("craneInfoUi") as List<CraneInfoUi>,
                                    sharedPreferencesSetting.firstName!!,
                                    sharedPreferencesSetting.secondName!!,
                                    sharedPreferencesSetting.lastName!!,
                                    sharedPreferencesSetting.jobPosition!!
                                ) +
                                "<br>" +
                                "<br>" +
                                "<br>" +
                                prepareCraneStatusPdfData(viewModel.items.value!!)
                                + "</pre>",
                        action = action

                    )

                }
            } else {
                CustomToast(root_cl).showMessage("Заполните поля")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::onItemsChanged))

    }

    private fun onItemsChanged(data: List<CraneTypeUi>) {
        groupAdapter.clear()
        groupAdapter.addAll(data)
        groupAdapter.setOnItemClickListener { item, _ ->
            item as CraneTypeUi
            if (item.id != 4) {
                findNavController().navigate(
                    R.id.action_navigation_crane_types_to_craneFullInfoFragment,
                    bundleOf(
                        "id" to item.id,
                        "header_title" to item.name,
                        "craneInfoUi" to arguments?.get("craneInfoUi") as List<CraneInfoUi>,
                        "craneTypeUi" to viewModel.items.value

                    )
                )
            } else {
                findNavController().navigate(
                    R.id.action_navigation_crane_types_to_craneMetalConstructorFragment,
                    bundleOf(
                        "id" to item.id,
                        "header_title" to item.name,
                        "craneInfoUi" to arguments?.get("craneInfoUi") as List<CraneInfoUi>,
                        "craneTypeUi" to viewModel.items.value

                    )
                )
            }
        }
    }


    private fun initRecyclerView() {
        binding.craneTypesRv.apply {
            groupAdapter.spanCount = 2
            adapter = groupAdapter
            layoutManager = GridLayoutManager(context, groupAdapter.spanCount)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setTime(): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 1)
        val format1 = SimpleDateFormat("yyyy-MM-dd - HH:mm:ss")
        return format1.format(cal.time)
    }

    override fun onResume() {
        super.onResume()
    }

}