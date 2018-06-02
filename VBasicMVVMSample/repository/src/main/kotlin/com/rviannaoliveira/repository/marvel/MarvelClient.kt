package com.rviannaoliveira.repository.marvel

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class MarvelClient {

    companion object {
        fun createParametersDefault(chain: Interceptor.Chain): Response {
            val timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
            var request = chain.request()
            val builder = request.url().newBuilder()

            builder.addQueryParameter("apikey", ApiConstant.publicKey)
                    .addQueryParameter("hash", MarvelHashGenerate.generate(timeStamp, ApiConstant.privateKey, ApiConstant.publicKey))
                    .addQueryParameter("ts", timeStamp.toString())

            request = request.newBuilder().url(builder.build()).build()
            return chain.proceed(request)

        }
    }
}