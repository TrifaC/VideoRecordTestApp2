package com.android.practice.videorecordtestapp2

import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "Main Activity"
        const val VIRTUAL_DISPLAY_TAG = "VirtualDisplay"
        const val RECORD_STATUS = "record_status"
        const val REQUEST_CODE = 1000
    }

    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button
    private lateinit var infoTV: TextView

    private var mScreenWidth: Int = 0
    private var mScreenHeight: Int = 0
    private var mScreenDensity: Int = 0

    private var isStarted: Boolean = false

    /** Hard Code the Resolution and Audio Choice first. */
    private var isVideoSD: Boolean = true
    private var isAudio = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(LOG_TAG, "onCreate run.")

        initView()
        getScreenInfo()
    }

//------------------------------------- Override Function ------------------------------------------


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBoolean(RECORD_STATUS, isStarted)
    }


//------------------------------------- View Handling ----------------------------------------------


    /********************************** Initialize the view. **************************************/
    private fun initView(){
        startBtn = findViewById(R.id.startBtn)
        stopBtn = findViewById(R.id.stopBtn)
        infoTV = findViewById(R.id.infoTV)

        if (isStarted) {
            statusStarted()
            // startScreenRecording()
        } else {
            statusStopped()
            // stopScreenRecording()
        }

        startBtn.setOnClickListener(listener)
        stopBtn.setOnClickListener(listener)
    }


    /********************************** Set Click listener ****************************************/
    private val listener = View.OnClickListener { view ->
        when (view.id) {
            R.id.startBtn -> {

            }
            R.id.stopBtn -> {

            }
        }
    }


//------------------------------------- Status Handling --------------------------------------------


    /********************************** Start Recording *******************************************/
    private fun statusStarted() {
        infoTV.text = "Start Recording."
    }


    /********************************** Stop Recording ********************************************/
    private fun statusStopped() {
        infoTV.text = "Stop Recording."
    }

    /********************************** Start Screen Recording ************************************/
    private fun startScreenRecording() {
        val mMediaProjectionManager: MediaProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        val permissionIntent: Intent = mMediaProjectionManager.createScreenCaptureIntent()
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            onActivityResult(REQUEST_CODE, result)
        }.launch(intent)
    }
    private fun onActivityResult(requestCode: Int, result: ActivityResult?) {
        if (requestCode == REQUEST_CODE) {
            if (result?.resultCode == RESULT_OK) {

            }
        }
    }


//------------------------------------- Get Screen Information -------------------------------------


    /********************************** Get Screen Information ************************************/
    private fun getScreenInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val mMetrics = windowManager.currentWindowMetrics
            mScreenHeight = mMetrics.bounds.height()
            mScreenWidth = mMetrics.bounds.width()
            mScreenDensity = resources.displayMetrics.densityDpi
        } else {
            val mMetrics: DisplayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(mMetrics)
            mScreenHeight = mMetrics.heightPixels
            mScreenWidth = mMetrics.widthPixels
            mScreenDensity = mMetrics.densityDpi
        }
        Log.i(LOG_TAG, "getScreenInfo: The screen width is $mScreenWidth")
        Log.i(LOG_TAG, "getScreenInfo: The screen height is $mScreenHeight")
        Log.i(LOG_TAG, "getScreenInfo: The screen density is $mScreenDensity")
    }
}