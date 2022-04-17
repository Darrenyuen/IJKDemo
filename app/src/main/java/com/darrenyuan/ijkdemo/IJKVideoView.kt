package com.darrenyuan.ijkdemo

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import android.widget.FrameLayout
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class IJKVideoView : FrameLayout {

    private val TAG = "IJKVideoView"

    private var mSurfaceView: SurfaceView? = null
    private var mPlayer: IMediaPlayer? = null
    var videoPlayListener: VideoPlayListener? = null

    private var path: String? = null

    constructor(context: Context) : super(context, null) {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet, ) {
        initVideoView(context)
    }

    constructor(
        context: Context,
        attributeSet: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attributeSet, defStyleAttr) {
        initVideoView(context)
    }

    //添加surfaceview用于渲染视频
    private fun initVideoView(context: Context) {
        mSurfaceView = SurfaceView(context)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER)
        mSurfaceView?.layoutParams = lp
        mSurfaceView?.holder?.addCallback(SurfaceCallback())
        this.addView(mSurfaceView)
    }

    fun setVideoPath(path: String) {
        Log.i(TAG, "path: $path")
        this.path = path
    }

    private fun loadVideo() {
        if (mPlayer != null) {
            mPlayer?.stop()
            mPlayer?.release()
        }
        mPlayer = IjkMediaPlayer()
        videoPlayListener?.let {
            mPlayer?.setOnPreparedListener(it)
            mPlayer?.setOnErrorListener(it)
        }
        mPlayer?.dataSource = path
        mPlayer?.setDisplay(mSurfaceView?.holder)
        mPlayer?.prepareAsync()
    }

    fun start() {
        mPlayer?.start()
    }

    fun pause() {
        mPlayer?.pause()
    }

    fun stop() {
        mPlayer?.stop()
    }

    fun release() {
        mPlayer?.reset()
        mPlayer?.release()
        mPlayer?.reset()
    }

    fun reset() {
        mPlayer?.reset()
    }

    //surfaceView监听器
    private inner class SurfaceCallback : SurfaceHolder.Callback {

        override fun surfaceCreated(holder: SurfaceHolder) {
            Log.i(TAG, "surfaceCreated")
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            Log.i(TAG, "surfaceChanged")
            loadVideo()
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            Log.i(TAG, "surfaceDestroyed")
        }
    }


}

interface VideoPlayListener : IMediaPlayer.OnPreparedListener, IMediaPlayer.OnCompletionListener, IMediaPlayer.OnErrorListener