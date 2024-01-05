package com.abdosharaf.stopwatch

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.abdosharaf.stopwatch.service.StopwatchService
import com.abdosharaf.stopwatch.ui.theme.Dark
import com.abdosharaf.stopwatch.ui.theme.StopwatchTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var showSplash = true

    private var isBound by mutableStateOf(false)
    private lateinit var stopwatchService: StopwatchService

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as StopwatchService.StopwatchBinder
            stopwatchService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, StopwatchService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            showSplash
        }

        setContent {
            StopwatchTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(color = Dark)
                systemUiController.setNavigationBarColor(color = Color.Black)

                if (isBound) {
                    MainContent(stopwatchService = stopwatchService) {
                        showSplash = false
                    }
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermissions() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                Log.d("MainActivity", "$result")
            }
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }
}