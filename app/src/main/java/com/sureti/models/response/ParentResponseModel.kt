package com.sureti.models.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class ParentResponseModel(
    @SerializedName("message")
     var message: String,
    @SerializedName("Requested_Action")
     var requestedAction: Boolean
)


