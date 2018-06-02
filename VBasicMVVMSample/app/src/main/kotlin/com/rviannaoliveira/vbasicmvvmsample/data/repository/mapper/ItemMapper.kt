package com.rviannaoliveira.vbasicmvvmsample.data.repository.mapper

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemSample(val name : String, val image : String?) : Parcelable


