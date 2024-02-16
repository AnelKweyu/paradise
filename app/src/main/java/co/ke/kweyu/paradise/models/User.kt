package co.ke.kweyu.paradise.models

import android.os.Parcel
import android.os.Parcelable

data class User(
    val email: String = "",
) : Parcelable {
    constructor(source: Parcel) : this(source.readString()!!)

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {writeString(email)}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}