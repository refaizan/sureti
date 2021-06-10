package com.sureti.models

import com.google.gson.annotations.SerializedName

data class BranchCollaboratorModel(
    @SerializedName("prouserid") var prouserid: Int,
    @SerializedName("email") var email: String,
    @SerializedName("firstName") var firstName: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("profilePicture") var profilePicture: String,
    @SerializedName("iscompleted") var iscompleted: Boolean,
    @SerializedName("phone") var phone: String,
    @SerializedName("role") var role: String,
    @SerializedName("joiningdate") var joiningdate: String,
    @SerializedName("firebasepushnotificationtoken") var firebasepushnotificationtoken: String
)
