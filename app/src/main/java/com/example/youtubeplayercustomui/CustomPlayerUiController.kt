package com.example.youtubeplayercustomui

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class CustomPlayerUiController(
    private val context: Context?,
    customPlayerUi: View,
    private val youTubePlayer: YouTubePlayer,
    private val youTubePlayerView: YouTubePlayerView?
) : AbstractYouTubePlayerListener() {
    private var playerTracker: YouTubePlayerTracker = YouTubePlayerTracker()

    // panel is used to intercept clicks on the WebView, I don't want the user to be able to click the WebView directly.
    private var panel: View? = null
    private var videoCurrentTimeTextView: TextView? = null
    private var videoDurationTextView: TextView? = null
    private var fullscreen = false

    init {
        youTubePlayer.addListener(playerTracker)
        initViews(customPlayerUi)
    }

    private fun initViews(playerUi: View) {
        panel = playerUi.findViewById<View>(R.id.panel)
        videoCurrentTimeTextView = playerUi.findViewById<TextView>(R.id.video_current_time)
        videoDurationTextView = playerUi.findViewById<TextView>(R.id.video_duration)
        val playPauseButton = playerUi.findViewById<Button>(R.id.play_pause_button)
        val enterExitFullscreenButton =
            playerUi.findViewById<Button>(R.id.enter_exit_fullscreen_button)
        playPauseButton.setOnClickListener { view: View? -> if (playerTracker.state == PlayerState.PLAYING) youTubePlayer!!.pause() else youTubePlayer!!.play() }
        enterExitFullscreenButton.setOnClickListener { view: View? ->
            if (fullscreen) youTubePlayerView!!.wrapContent() else youTubePlayerView!!.matchParent()
            fullscreen = !fullscreen
        }
    }

    override fun onReady(youTubePlayer: YouTubePlayer) {
    }

    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerState) {
        if (state == PlayerState.PLAYING || state == PlayerState.PAUSED || state == PlayerState.VIDEO_CUED) {
            val color = ContextCompat.getColor(context!!, android.R.color.transparent)
            panel!!.setBackgroundColor(color)
        }
        else if (state == PlayerState.BUFFERING) {
            val color = ContextCompat.getColor(context!!, android.R.color.transparent)
            panel!!.setBackgroundColor(color)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
        videoCurrentTimeTextView!!.text = second.toString() + ""
    }

    @SuppressLint("SetTextI18n")
    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
        videoDurationTextView!!.text = duration.toString() + ""
    }
}