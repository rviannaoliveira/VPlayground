package com.example.dynamicfeatureexample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dynamicfeatureexample.databinding.ActivityMainBinding
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

class MainActivity : AppCompatActivity(),
    SplitInstallStateUpdatedListener {

    private lateinit var manager: SplitInstallManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //It will take care of your request to download the module
        manager = SplitInstallManagerFactory.create(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.action.setOnClickListener {
            startActivity(
                Intent().setClassName(
                    BuildConfig.APPLICATION_ID,
                    "br.com.rviannaoliveira.dynamic_feature.MyTestDynamicFeature"
                )
            )
        }
    }


    override fun onStart() {
        super.onStart()
        //When open the screen will download but you can put this in an action
        loadAndLaunchModule()
    }

    private fun loadAndLaunchModule() {
        val name = "dynamic_feature"
        //if it is already installed, it will not ask to download it again, as it is not needed since it is already installed
        if (manager.installedModules.contains(name)) {
            Log.d(">>>>>>", "Already installed")
            return
        }
        Log.d(">>>>>>", "still wasnt install")

        // Create request to install a feature module by name.
        val request = SplitInstallRequest.newBuilder()
            .addModule(name)
            .build()

        //Start request to install a feature module by name.
        manager.startInstall(request)
            .addOnSuccessListener {
                println(">>>startInstall>>>Success $it")
            }
            .addOnFailureListener {
                println(">>>>>>Failed $it")
            }
            .addOnCompleteListener {
                println(">>>>>>C $it")
            }
    }

    override fun onResume() {
        super.onResume()
        //listens to events sent to download
        manager.registerListener(this)
    }

    override fun onPause() {
        super.onPause()
        manager.unregisterListener(this)
    }

    //My activity is listening for these events to react on the screen
    override fun onStateUpdate(state: SplitInstallSessionState) {
        val text = when (state.status()) {
            SplitInstallSessionStatus.DOWNLOADING -> {
                "DOWNLOADING ${
                    state.bytesDownloaded().byteToMega()
                } / ${state.totalBytesToDownload().byteToMega()} MB"
            }
            SplitInstallSessionStatus.DOWNLOADED -> "DOWNLOADED"
            SplitInstallSessionStatus.INSTALLED -> "INSTALLED"
            SplitInstallSessionStatus.INSTALLING -> "Installing"
            SplitInstallSessionStatus.FAILED -> "Failed"
            SplitInstallSessionStatus.PENDING -> "Pending"
            else -> {
                "Other ${state.status()}"
            }
        }

        binding.status.text = text
        println(">>>>>> $text")
    }
}