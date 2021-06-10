package com.sureti.models.response

import com.google.gson.annotations.SerializedName
import com.sureti.models.SignUpModel

data class SignUpResponseModel(
    @SerializedName("data") var data: SignUpModel,
    @SerializedName("message") var message: String,
    @SerializedName("Requested_Action") var RequestedAction: Boolean
)