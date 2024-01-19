package com.example.short_app.model

import com.google.firebase.Timestamp

data class VedioModel (
    var videoId : String = "",
    var title : String = "",
    var url : String = "",
    var uploaderId : String = "",
    var createdTime : Timestamp = Timestamp.now()
)