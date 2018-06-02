package com.rviannaoliveira.vbasicmvvmsample.domain

import com.rviannaoliveira.vbasicmvvmsample.data.repository.mapper.ItemSample
import io.reactivex.Single

interface ItemsContract{
    interface IView{
        fun showProgressBar()
        fun hideProgressBar()
        fun loadItems(items : List<ItemSample>)
    }

    interface IPresenter{
        fun loadItems()
    }

    interface IInteractor{
        fun loadItems() : Single<ItemSample>
    }

}