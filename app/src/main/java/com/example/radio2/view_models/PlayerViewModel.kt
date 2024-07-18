package com.example.radio2.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class PlayerViewModel: ViewModel() {
    private var _player: ExoPlayer? = null
    val player: ExoPlayer?
        get() = _player

    var isPlaying: Boolean = false
        private set



    fun initializePlayer(context: Context) {
        if (_player == null) {
            val loadControl = DefaultLoadControl.Builder()
                .setBufferDurationsMs(
                    DefaultLoadControl.DEFAULT_MIN_BUFFER_MS,
                    DefaultLoadControl.DEFAULT_MAX_BUFFER_MS,
                    DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS / 2,
                    DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS / 2
                )
                .build()

            _player = ExoPlayer.Builder(context)
                .setLoadControl(loadControl)
                .build()
        }
    }

    fun releasePlayer() {
        _player?.release()
        _player = null
    }


    fun play(url: String) {
        _player?.let {
            val mediaItem = MediaItem.fromUri(url)
            it.setMediaItem(mediaItem)
            it.prepare()
            it.play()
            isPlaying = true
        }
    }

    fun pause() {
        _player?.pause()
        isPlaying = false
    }

    fun stop() {
        _player?.stop()
        _player?.clearMediaItems()
    }

    fun resume() {
        _player?.play()
        isPlaying = true
    }
}