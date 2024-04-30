package com.example.myproject

import android.net.Uri

data class Contact(
    val profileImage: Uri?,
    val textName: String,
    val textPhoneNumber: String
)

