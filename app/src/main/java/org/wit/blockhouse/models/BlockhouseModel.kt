package org.wit.blockhouse.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class BlockhouseModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var date: String = "",
    @Embedded var location: Location = Location(),
    //@Embedded var note: List<String> = ArrayList(),
    //@Embedded var image_list: List<String> = ArrayList(),
    //@Embedded var check_box: Boolean = false,
    var image: String = ""
) : Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable


