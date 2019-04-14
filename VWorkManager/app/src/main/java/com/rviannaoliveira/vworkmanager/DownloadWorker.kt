package com.rviannaoliveira.vworkmanager

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters


class DownloadWorker(context : Context, params : WorkerParameters) : Worker(context,params) {

    init {
        notificationStart()
    }

    override fun doWork(): Result {
        val x = inputData.getInt(ARG_X, 0)
        val y = inputData.getInt(ARG_Y, 0)
        val z = inputData.getInt(ARG_Z, 0)

        val fullValue = x + y + z

        val output = Data.Builder()
            .putInt(KEY_RESULT, fullValue)
            .build()


        return if (fullValue == 0) {
            Result.failure()
        } else {
            Result.success(output)
        }
    }
    companion object {
        const val ARG_X = "X"
        const val ARG_Y = "Y"
        const val ARG_Z = "Z"
        const val KEY_RESULT = "KEY_RESULT"
    }
}
