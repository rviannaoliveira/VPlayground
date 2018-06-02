package com.rviannaoliveira.vbasicmvvmsample.presentation

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

class ItemsFragment : Fragment(), IView {
    private lateinit var charactersAdapter : ItemsAdapter

    companion object {
        fun newInstance() =  ItemsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        setHasOptionsMenu(true)
        loadView()
        return view
    }

    private fun loadView() {
        charactersAdapter = ItemsAdapter()
        recyclewView.adapter = charactersAdapter
        recyclewView.setHasFixedSize(true)
        recyclewView.layoutManager = LinearLayoutManager(activity)
    }

    override fun loadItems(items: List<ItemSample>) {
    }

    override fun showProgressBar() = progressbar.show()

    override fun hideProgressBar() = progressbar.hide()

}