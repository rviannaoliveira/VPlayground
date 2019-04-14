package com.rviannaoliveira.vworkmanager

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager

class MainViewModel : ViewModel() {
     val livedataWorker : LiveData<List<WorkInfo>>  =
        WorkManager
            .getInstance()
            .getWorkInfosByTagLiveData(MainActivity.TAG_DOWNLOAD_WORKER)

    fun runWorker(){

        val data = Data.Builder()
        data.putInt(DownloadWorker.ARG_X, 1)
            .putInt(DownloadWorker.ARG_Y, 2)
            .putInt(DownloadWorker.ARG_Z, 3)

        val request = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
            .setInputData(data.build())
            .addTag(MainActivity.TAG_DOWNLOAD_WORKER)
            .build()
        WorkManager.getInstance().enqueue(request)

        WorkManager
            .getInstance()
            .getWorkInfosByTagLiveData(MainActivity.TAG_DOWNLOAD_WORKER)
    }




}