package br.com.uol.ps.pagvendas.coreshared.networking

import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class NetworkResponseAdapter(
    private val responseType: Type,
    private val moshi: Moshi
): CallAdapter<Type, Any> {

    override fun responseType(): Type = responseType
    override fun adapt(call: Call<Type>) = NetworkResponseCall(call,moshi)
}

