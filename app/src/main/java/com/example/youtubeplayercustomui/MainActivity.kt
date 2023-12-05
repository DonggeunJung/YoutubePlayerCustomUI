package com.example.youtubeplayercustomui
/* Android YouTube Player Custom UI example
 * Author : Donggeun Jung (Dennis)
 * Email : topsan72@gmail.com
 * Date : 2023.12.05
 * Reference : android-youtube-player by PierfrancescoSoffritti
 * https://github.com/PierfrancescoSoffritti/android-youtube-player
 */
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MainActivity : AppCompatActivity() {
    lateinit var youTubePlayerView: YouTubePlayerView
    lateinit var context: Context
    val videoId = "yM36wjQhWKM" //"S0Q4gqBUs7c"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        initYouTubePlayerView()
    }

    private fun initYouTubePlayerView() {
        youTubePlayerView = findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youTubePlayerView)

        val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.custom_player_ui)
                val customPlayerUiController = CustomPlayerUiController(
                    context,
                    customPlayerUi,
                    youTubePlayer,
                    youTubePlayerView
                )
                youTubePlayer.addListener(customPlayerUiController)
                youTubePlayer.loadOrCueVideo(lifecycle, videoId, 0f)
            }
        }

        val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
        youTubePlayerView.initialize(listener, options)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            youTubePlayerView.matchParent()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            youTubePlayerView.wrapContent()
        }
    }
}