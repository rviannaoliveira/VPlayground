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
import com.rviannaoliveira.vbasicmvvmsample.di.AppInjector
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_list.*




class HomeFragment : Fragment() {
    private lateinit var adapter : HomeAdapter
    private lateinit var viewModel: HomeViewModel
    private val disposable : CompositeDisposable = CompositeDisposable()

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
        disposable.add(viewModel.getViewState().subscribe { handler(it) })
        viewModel.loadCharacters()
    }

    private fun handler(state: HomeViewState) {
        if(state.isLoading){
            showProgressBar()
        }else{
            hideProgressBar()
        }

        adapter.loadMarvelCharacters(state.list)
    }

    private fun setupView() {
        adapter = HomeAdapter()
        recyclewView.adapter = adapter
        recyclewView.setHasFixedSize(true)
        recyclewView.layoutManager = LinearLayoutManager(activity)
    }

    private fun showProgressBar() = progressbar.show()

    private fun hideProgressBar() = progressbar.hide()

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }
}