package org.wit.blockhouse.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserModel(
    var id: Int = 0,
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var blockhouses: ArrayList<BlockhouseModel> = ArrayList()
) : Parcelable

