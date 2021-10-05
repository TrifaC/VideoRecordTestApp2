package com.android.practice.videorecordtestapp2

import android.app.Service
import android.content.Intent
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.os.IBinder
import android.util.Log

class ScreenRecordService: Service() {

    companion object {
        private const val LOG_TAG = "ScreenRecordService"
    }

    private var mScreenWidth: Int = 0
    private var mScreenHeight: Int = 0
    private var mScreenDensity: Int = 0
    private var mResultCode: Int = 0
    private lateinit var mResultData: Intent
    private var isVideoSD: Boolean = false
    private var isAudio: Boolean = false

    private lateinit var mMediaProjection: MediaProjection
    private lateinit var mMediaRecorder: MediaRecorder
    private lateinit var mVirtualDisplay: VirtualDisplay


//------------------------------------- Override Function ------------------------------------------


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(LOG_TAG, "onCreate(): run.")
    }

    /********************************** All media projection related function should be stop ******/
    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG_TAG, "onDestroy(): run.")
        mVirtualDisplay.release()
        mMediaRecorder.setOnErrorListener(null)
        mMediaProjection.stop()
        mMediaRecorder.reset()
        mMediaProjection.stop()
    }

    /********************************** After Press the recording function ************************/
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(LOG_TAG, "onStartCommand(): run.")
        mResultCode = intent!!.getIntExtra("code", -1)
        mResultData = intent.getParcelableExtra("data")!!
        mScreenWidth = intent.getIntExtra("width", 720);
        mScreenHeight = intent.getIntExtra("height", 1280);
        mScreenDensity = intent.getIntExtra("density", 1);
        isVideoSD = intent.getBooleanExtra("quality", true);
        isAudio = intent.getBooleanExtra("audio", true);

        return super.onStartCommand(intent, flags, startId)
    }


//------------------------------------- Media Components Creation ----------------------------------

    /********************************** MediaProjection *******************************************/

    /********************************** MediaRecorder *********************************************/

    /********************************** VirtualDisplay ********************************************/


}