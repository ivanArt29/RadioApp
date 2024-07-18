package com.example.radio2

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.radio2.databinding.FragmentInnerBinding
import com.example.radio2.model.StationsModel
import com.example.radio2.view_models.InnerViewModel
import com.example.radio2.view_models.PlayerViewModel
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

    private val playerViewModel: PlayerViewModel by viewModels()

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
        var url = arguments?.getString("url")
        var position = arguments?.getInt("position")
        val stationsList = arguments?.getSerializable("stationsList") as? ArrayList<StationsModel>

        binding.button.setOnClickListener {
            if (playerViewModel.isPlaying) {
                playerViewModel.pause()
            } else {
                playerViewModel.resume()
            }
        }

        url?.let {
            playerViewModel.play(it)
        }



        fun setImage(imagePath: String)
        {
            if (imagePath != null) {
                val context = binding.imageView.context
                val imageResource = context.resources.getIdentifier(imagePath, "drawable", context.packageName)
                if (imageResource != 0) { // 0 means not found
                    binding.imageView.setImageResource(imageResource)
                } else {
                    // Handle the case where the image resource was not found
                }
            }
        }

        if (imagePath != null) {
            setImage(imagePath)
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

        if (url != null) {
            player!!.play()
        }

        // Обработчик кнопки паузы/воспроизведения
        binding.button.setOnClickListener {
            player?.let {
                if (it.isPlaying) {
                    player!!.pause()
                } else {
                    player!!.play()
                }
            }
        }

        binding.buttonNext.setOnClickListener {

            if (stationsList != null) {
                if (position!! >= stationsList.size-1)
                {
                    position = 0
                }
                else{
                    position = position!! + 1
                }

            }
            val station = stationsList?.get(position!!)
            url = station?.stationUrl
            if (station != null) {
                binding.textView.text = station.stationName
                setImage(station.imagePath)
            }
            player?.let {
                it.stop()
                it.clearMediaItems()
                val mediaItem = url?.let { MediaItem.fromUri(it) }
                if (mediaItem != null) {
                    it.setMediaItem(mediaItem)
                }
                it.prepare()
                it.play()
            }
            }


        binding.buttonPrev.setOnClickListener {

            if (position!! == 0)
            {
                if (stationsList != null) {
                    position = stationsList.size-1
                }
            }
            else{
                position = position!! - 1
            }
            val station = stationsList?.get(position!!)
            url = station?.stationUrl

            if (station != null) {
                binding.textView.text = station.stationName
                setImage(station.imagePath)
            }
            player?.let {
                it.stop()
                it.clearMediaItems()
                val mediaItem = url?.let { MediaItem.fromUri(it) }
                if (mediaItem != null) {
                    it.setMediaItem(mediaItem)
                }
                it.prepare()
                it.play()
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