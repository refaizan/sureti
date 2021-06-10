package com.sureti.models

import com.google.gson.annotations.SerializedName

data class UserInfoModel(
    @SerializedName("email") var email: String,
    @SerializedName("id") var id: Int,
    @SerializedName("firstName") var firstName: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("userCellNo") var userCellNo: String,
    @SerializedName("mailingAddress") var mailingAddress: String,
    @SerializedName("token") var token: String,
    @SerializedName("emailVerified") var emailVerified: Boolean,
    @SerializedName("profilePicture") var profilePicture: String,
    @SerializedName("role") var role: String,
    @SerializedName("apartmentno") var apartmentno: String,
    @SerializedName("customerStatus") var customerStatus: String,
    @SerializedName("noOfCheckCollabboratingIn") var noOfCheckCollabboratingIn: String,
    @SerializedName("cansubmitacheck") var cansubmitacheck: Boolean,
    @SerializedName("joiningDate") var joiningDate: String,
    @SerializedName("proprofilecompletionpercentage") var proprofilecompletionpercentage: String,
    @SerializedName("idstatus") var idstatus: String,
    @SerializedName("ssnstatus") var ssnstatus: String,
    @SerializedName("ssn") var ssn: String,
    @SerializedName("idbackURL") var idbackURL: String,
    @SerializedName("idfrontURL") var idfrontURL: String,
    @SerializedName("ssnexpiry") var ssnexpiry: String,
    @SerializedName("idexpiry") var idexpiry: String
)
