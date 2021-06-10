package com.sureti.models.response

import com.google.gson.annotations.SerializedName
import com.sureti.models.ActiveCheckModel

data class ActiveCheckResponseModel(
    @SerializedName("data") var data: List<ActiveCheckModel>,
    @SerializedName("message") var message: String,
    @SerializedName("Requested_Action") var RequestedAction: Boolean
)
