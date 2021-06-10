package com.sureti.models.response

import com.google.gson.annotations.SerializedName
import com.sureti.models.SignInModel

data class SignInResponseModel(
    @SerializedName("data") var data: SignInModel,
    @SerializedName("message") var message: String,
    @SerializedName("Requested_Action") var RequestedAction: Boolean
)
