package com.example.short_app.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.short_app.databinding.ProfileVedioItemRowBinding
import com.example.short_app.model.VedioModel
import com.example.short_app.single_video_player
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ProfileVideoAdapter(options: FirestoreRecyclerOptions<VedioModel>)
    : FirestoreRecyclerAdapter<VedioModel,ProfileVideoAdapter.VideoViewHolder>(options)
{

    inner class VideoViewHolder(private val binding : ProfileVedioItemRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(video : VedioModel){
            Glide.with(binding.postThumbnailImageView)
                .load(video.url)
                .into(binding.postThumbnailImageView)
            binding.postThumbnailImageView.setOnClickListener{
                val intent = Intent(binding.postThumbnailImageView.context,single_video_player::class.java)
                intent.putExtra("videoId",video.videoId)
                binding.postThumbnailImageView.context.startActivity(intent)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ProfileVedioItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int, model: VedioModel) {
        TODO("Not yet implemented")
        holder.bind(model)
    }
}