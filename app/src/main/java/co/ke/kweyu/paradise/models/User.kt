package co.ke.kweyu.paradise.models

import android.os.Parcel
import android.os.Parcelable
import co.ke.kweyu.paradise.enums.Gender
import co.ke.kweyu.paradise.enums.PlanEnum

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val dob:String = "",
    var gender: Gender = Gender.NotSpecified,
    val image: String = "",
    val mobile: Long = 0,
    val address: String = "",
    val fcmToken: String = "",
    var selected: Boolean = false
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        Gender.valueOf(source.readString()!!),
        source.readString()!!,
        source.readLong(),
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(email)
        writeString(dob)
        writeString(gender.name)
        writeString(image)
        writeLong(mobile)
        writeString(address)
        writeString(fcmToken)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}