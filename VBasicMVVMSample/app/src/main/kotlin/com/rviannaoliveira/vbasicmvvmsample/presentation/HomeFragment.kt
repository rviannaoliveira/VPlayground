package com.rviannaoliveira.vbasicmvvmsample.presentation

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rviannaoliveira.core.hide
import com.rviannaoliveira.core.show
import com.rviannaoliveira.vbasicmvvmsample.R
import com.rviannaoliveira.vbasicmvvmsample.data.repository.mapper.ItemSample
import com.rviannaoliveira.vbasicmvvmsample.domain.ItemsContract.IView
import kotlinx.android.synthetic.main.fragment_list.*

class HomeFragment : Fragment(), IView {
    private lateinit var charactersAdapter : HomeAdapter
    private lateinit var viewModel: HomeViewModel

    companion object {
        fun newInstance() =  HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        charactersAdapter = HomeAdapter()
        recyclewView.adapter = charactersAdapter
        recyclewView.setHasFixedSize(true)
        recyclewView.layoutManager = LinearLayoutManager(activity)
    }

    override fun loadItems(items: List<ItemSample>) {
    }

    override fun showProgressBar() = progressbar.show()

    override fun hideProgressBar() = progressbar.hide()

}