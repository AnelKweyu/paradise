package co.ke.kweyu.paradise.models

import android.os.Parcel
import android.os.Parcelable
data class Plan(
    var type: String = "",
    val profit: String = "",
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(type)
        writeString(profit)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Plan> = object : Parcelable.Creator<Plan> {
            override fun createFromParcel(source: Parcel): Plan = Plan(source)
            override fun newArray(size: Int): Array<Plan?> = arrayOfNulls(size)
        }
    }
}