package com.example.puli

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BenfDetails(
    val rid: Int,
    val name: String,
    val amount: Long,
    val date: String,
    val iRate: String,
    val remarks: String
) : Parcelable
