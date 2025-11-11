package com.example.puli

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BenfDetails(
    var rid: Int,
    var name: String,
    var amount: Long,
    var date: String,
    var iRate: String,
    var remarks: String = "" // Default to empty (ignored during parse)
) : Parcelable
