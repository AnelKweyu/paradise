package co.ke.kweyu.paradise.models

import android.os.Parcel
import android.os.Parcelable
import co.ke.kweyu.paradise.enums.PlanEnum

data class Plan(
    var type: PlanEnum,
    val profit: Int
) {}