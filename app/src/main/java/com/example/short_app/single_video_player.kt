package com.example.short_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.short_app.databinding.ActivitySingleVideoPlayerBinding
import com.example.short_app.model.VedioModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import np.com.bimalkafle.miniclip.adapter.VideoListAdapter

class single_video_player : AppCompatActivity() {

    lateinit var binding : ActivitySingleVideoPlayerBinding

    lateinit var videoId : String
    lateinit var adapter : VideoListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        videoId = intent.getStringExtra("videoId")!!
        setupViewPager()

    }

    fun setupViewPager() {

        val options = FirestoreRecyclerOptions.Builder<VedioModel>()
            .setQuery(
                Firebase.firestore.collection("videos")
                    .whereEqualTo("videoId",videoId),
                VedioModel::class.java
            ).build()
        adapter = VideoListAdapter(options)
        binding.viewPager.adapter = adapter


    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.stopListening()
    }
}