package com.sureti.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sureti.databinding.ActivitySignInBinding
import com.sureti.models.response.SignInResponseModel
import com.sureti.network.APIClient
import com.sureti.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding

    companion object {
        const val TAG = "SIGN_IN_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = StorageUtility.getDataFromPreferences(
            this,
            AppConstants.SharedPreference.USER_EMAIL,
            ""
        )

        binding.signInBtnNext.setOnClickListener {
            val password = binding.signInEdtPassword.text.toString()
            if (password.isNotEmpty())
                signIn(email!!, password)
            else
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
        }


    }

    private fun signIn(email: String, password: String) {
        DialogUtils.showLoading(this, "Please Wait!", "Signing in...")
        APIClient.signIn(email, password, object : Callback<SignInResponseModel> {
            override fun onResponse(
                call: Call<SignInResponseModel>,
                response: Response<SignInResponseModel>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: " + response.body()?.data)
                    Log.d(TAG, "onResponse: Message: " + response.body()?.message)
                    if (response.body()?.message == "") {
                        val token = response.body()?.data?.token
                        StorageUtility.saveDataInPreferences(
                            this@SignInActivity,
                            AppConstants.SharedPreference.USER_ACCESS_TOKEN,
                            token
                        )
                        StorageUtility.saveDataInPreferences(
                            this@SignInActivity,
                            AppConstants.SharedPreference.IS_USER_LOGGED_IN,
                            true
                        )
                        val intent = Intent(this@SignInActivity, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        DialogUtils.dismiss()
                    } else if (response.body()?.message == "Incorrect email ID or password.") {
                        Toast.makeText(
                            this@SignInActivity,
                            "Invalid password",
                            Toast.LENGTH_SHORT
                        ).show()
                        DialogUtils.dismiss()
                    }
                }
            }

            override fun onFailure(call: Call<SignInResponseModel>, t: Throwable) {
                Toast.makeText(this@SignInActivity, t.message, Toast.LENGTH_SHORT).show()
                DialogUtils.dismiss()
            }

        })
    }
}