package com.example.radio2

import android.annotation.SuppressLint
import android.net.Uri
import android.opengl.Visibility
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.radio2.adapter.StationsAdapter
import com.example.radio2.databinding.FragmentMainMenuBinding
import com.example.radio2.model.StationsModel
import com.example.radio2.view_models.MainMenuViewModel
import com.example.radio2.view_models.PlayerViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.material.bottomappbar.BottomAppBar
import java.util.ArrayList

class MainMenu : Fragment() {

    companion object {
        fun newInstance() = MainMenu()
    }

    private val viewModel: MainMenuViewModel by viewModels()
    private val playerViewModel: PlayerViewModel by viewModels()

    private lateinit var player: ExoPlayer
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter: StationsAdapter

    lateinit var recyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)

        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerViewModel.initializePlayer(requireContext())
        initial()


    }

    private fun initial(){
        recyclerView = binding.rvStations
        adapter = StationsAdapter { position ->
            val station = myStations()[position]
            val bundle = bundleOf(
                "stationName" to station.stationName,
                "imagePath" to station.imagePath,
                "url" to station.stationUrl,
                "position" to position
            )
            bundle.putSerializable("stationsList", ArrayList(myStations()))
            findNavController().navigate(R.id.action_mainMenu_to_inner, bundle)
        }
        recyclerView.adapter = adapter
        adapter.setList(myStations())

        binding.bottomAppBar.root.visibility = if (playerViewModel.isPlaying) View.VISIBLE else View.GONE

        binding.bottomAppBar.playPauseButton.setOnClickListener {
            if (playerViewModel.isPlaying) {
                playerViewModel.pause()
            } else {
                playerViewModel.resume()
            }
        }
    }

    fun myStations(): ArrayList<StationsModel>{
        val list = ArrayList<StationsModel>()

        val station = StationsModel("retro_fm", "Ретро FM", "https://emgregion.hostingradio.ru:8064/moscow.retrofm.mp3")
        list.add(station)

        val station2 = StationsModel("russkoe_radio", "Русское радио", "https://rusradio.hostingradio.ru/rusradio128.mp3")
        list.add(station2)

        val station3 = StationsModel("mayak_radio", "Маяк", "https://icecast-gtrktambov.cdnvideo.ru/rmicecast")
        list.add(station3)

        val station4 = StationsModel("radio_of_russia", "Радио России", "https://streaming.positivity.radio/pr/calmkids/icecast.audio")
        list.add(station4)

        return list

    }


}