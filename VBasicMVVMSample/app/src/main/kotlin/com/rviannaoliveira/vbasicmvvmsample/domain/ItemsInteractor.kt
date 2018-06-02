package com.rviannaoliveira.vbasicmvvmsample.domain

import com.rviannaoliveira.vbasicmvvmsample.data.repository.mapper.ItemSample
import com.rviannaoliveira.vbasicmvvmsample.domain.ItemsContract.IInteractor
import io.reactivex.Single

class ItemsInteractor : IInteractor{
    override fun loadItems(): Single<ItemSample> {
        return Single.just(null)
    }

}