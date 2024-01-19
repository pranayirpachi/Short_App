package com.example.short_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.short_app.databinding.ActivityMainBinding
import com.example.short_app.model.VedioModel
import com.example.short_app.model.VedioUploadActivity
import com.example.short_app.util.UiUtil
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import np.com.bimalkafle.miniclip.adapter.VideoListAdapter


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter : VideoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavBar.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId){
                R.id.bottom_menu_home->{
                    UiUtil.showToast(this,"Home")
                }
                R.id.bottom_menu_add_video->{
                    startActivity(Intent(this,VedioUploadActivity::class.java))
                }
                R.id.bottom_menu_profile->{
                    val intent = Intent(this,ProfileActivity::class.java)
                    intent.putExtra("Profile_user_id",FirebaseAuth.getInstance().currentUser?.uid)
                    //Goto ProfileActivity
                    startActivity(intent)

                }
            }
            false
        }


        setupViewPager()





    }

    private fun setupViewPager() {

        val options = FirestoreRecyclerOptions.Builder<VedioModel>()
            .setQuery(
                Firebase.firestore.collection("videos"),
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