package com.darrenyuan.ijkdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var ijkVideoView: IJKVideoView? = null

    private val VIDEO_PATH = "" //视频地址

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")
        ijkVideoView = findViewById(R.id.ijk_video_view)
        ijkVideoView?.videoPlayListener = object : VideoPlayListener {
            override fun onPrepared(p0: IMediaPlayer?) {
                Log.i(TAG, "onPrepared")
                ijkVideoView?.start()
            }

            override fun onCompletion(p0: IMediaPlayer?) {
                Log.i(TAG, "onCompletion")
            }

            override fun onError(p0: IMediaPlayer?, p1: Int, p2: Int): Boolean {
                Log.i(TAG, "onError, p1: $p1, p2: $p2")
                return true
            }
        }
        ijkVideoView?.setVideoPath(VIDEO_PATH)
    }
}