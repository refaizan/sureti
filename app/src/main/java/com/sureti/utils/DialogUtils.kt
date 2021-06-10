package com.sureti.utils

import android.app.ProgressDialog
import android.content.Context
import com.sureti.R

@Suppress("DEPRECATION")
object DialogUtils {

    private lateinit var progressDialog: ProgressDialog

    fun showLoading(context: Context?, title: String?, message: String?) {
        progressDialog = ProgressDialog(context, R.style.ProgressDialog)
        progressDialog.setTitle(title)
        progressDialog.setMessage(message)
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    fun dismiss() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}