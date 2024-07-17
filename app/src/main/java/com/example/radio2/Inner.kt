package com.example.radio2

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.radio2.databinding.FragmentInnerBinding
import com.example.radio2.view_models.InnerViewModel
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class Inner : Fragment() {

    private lateinit var binding: FragmentInnerBinding
    private var mediaPlayer: MediaPlayer? = null

    private var player: SimpleExoPlayer? = null

    companion object {
        fun newInstance() = Inner()
    }

    private val viewModel: InnerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInnerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("DiscouragedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stationName = arguments?.getString("stationName")
        val imagePath = arguments?.getString("imagePath")
        val url = arguments?.getString("url")



        if (imagePath != null) {
            val context = binding.imageView.context
            val imageResource = context.resources.getIdentifier(imagePath, "drawable", context.packageName)
            if (imageResource != 0) { // 0 means not found
                binding.imageView.setImageResource(imageResource)
            } else {
                // Handle the case where the image resource was not found
            }
        }

        binding.textView.text = stationName

        val loadControl = DefaultLoadControl.Builder()
            .setBufferDurationsMs(
                DefaultLoadControl.DEFAULT_MIN_BUFFER_MS,
                DefaultLoadControl.DEFAULT_MAX_BUFFER_MS,
                DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS / 2,
                DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS / 2
            )
            .build()

        player = SimpleExoPlayer.Builder(requireContext())
            .setLoadControl(loadControl)
            .build().apply {

                val mediaItem = url?.let { MediaItem.fromUri(it) }
                if (mediaItem != null) {
                    setMediaItem(mediaItem)
                }
                prepare()
                play()
            }

        binding.button.setOnClickListener {
            player?.let {
                if (it.isPlaying) {
                    it.pause()
                } else {
                    it.play()
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }

    }



//    override fun onDestroy() {
//        super.onDestroy()
//        mediaPlayer?.release()
//    }
//}