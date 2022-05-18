package com.example.awen

import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.Environment
import android.os.IBinder
import android.util.Log
import java.io.File
import java.io.IOException
import java.util.*


/**
 * 录音主要是利用 MediaRecoder 这个类，进行声音的记录
 *
 * @Author: iwen 卡夫卡
 * @CreateDate: 2022/5/17
 * @Package: com.example.awen
 */
class RecordingService : Service(){

    private val TAG = "RecordingService"

    private var mFileName: String? = null
    private var mFilePath: String? = null

    private var mRecorder: MediaRecorder? = null

    private var mStartingTimeMillis: Long = 0
    private var mElapsedMillis: Long = 0
    private var mIncrementTimerTask: TimerTask? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startRecording()
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        if (mRecorder != null) {
            stopRecording();
        }
        super.onDestroy();
    }

    /**
     * 开始录音
     */
    fun startRecording(){
        setFileNameAndPath()
        mRecorder = MediaRecorder()
        mRecorder.let {
            mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4) // 录音文件保存的格式，这里保存为 mp4
            mRecorder!!.setOutputFile(mFilePath) // 设置录音文件的保存路径
            mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mRecorder!!.setAudioChannels(1)
            // 设置录音文件的清晰度
            mRecorder!!.setAudioSamplingRate(44100);
            mRecorder!!.setAudioEncodingBitRate(192000);

            try {
                mRecorder!!.prepare()
                mRecorder!!.start()
                mStartingTimeMillis = System.currentTimeMillis()
            }catch (e: IOException){
                Log.e(TAG, e.toString())
            }
        }
    }

    /**
     * 停止录音
     */
    fun stopRecording(){
        mRecorder!!.stop()
        mElapsedMillis = (System.currentTimeMillis() - mStartingTimeMillis)
        mRecorder!!.release()
        getSharedPreferences("sp_name_audio", MODE_PRIVATE)
            .edit()
            .putString("audio_path", mFilePath)
            .putLong("elpased", mElapsedMillis)
            .apply();
        if (mIncrementTimerTask != null) {
            mIncrementTimerTask!!.cancel();
            mIncrementTimerTask = null;
        }
        mRecorder = null;
    }


    /**
     * 设置录音文件的名字和保存路径
     */
    fun setFileNameAndPath(){
        var count = 0
        var f: File
        do {
            count++
            mFileName = getString(R.string.default_file_name) + "_" + System.currentTimeMillis() + ".mp4"
            mFilePath = Environment.getExternalStorageDirectory().absolutePath
            mFilePath += "/SoundRecorder/" + mFileName
            f = File(mFilePath)
        } while (f.exists() && !f.isDirectory)
    }
}