package com.sureti.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sureti.databinding.ActivitySignUpBinding
import com.sureti.models.response.SignUpResponseModel
import com.sureti.network.APIClient
import com.sureti.utils.AppConstants
import com.sureti.utils.DialogUtils
import com.sureti.utils.StorageUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    companion object {
        const val TAG = "SIGN_UP_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpBtn.setOnClickListener {
            if (checkValidations()) {
                DialogUtils.showLoading(this, "Please Wait!", "Signing up...")
                val firstName = binding.signUpEdtFirstName.text.toString()
                val lastName = binding.signUpEdtLastName.text.toString()
                val email = binding.signUpEdtEmail.text.toString()
                val password = binding.signUpEdtPassword.text.toString()
                APIClient.signUp(email, password, firstName, lastName, object :
                    Callback<SignUpResponseModel> {
                    override fun onResponse(
                        call: Call<SignUpResponseModel>,
                        response: Response<SignUpResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body()?.message == "PH user added.") {
                                Log.d(
                                    TAG,
                                    "onResponse: SignUp Model: " + response.body()?.data.toString()
                                )
                                val token = response.body()?.data?.token
                                StorageUtility.saveDataInPreferences(
                                    this@SignUpActivity,
                                    AppConstants.SharedPreference.USER_ACCESS_TOKEN,
                                    token
                                )
                                StorageUtility.saveDataInPreferences(
                                    this@SignUpActivity,
                                    AppConstants.SharedPreference.IS_USER_LOGGED_IN,
                                    true
                                )
                                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(intent)
                                DialogUtils.dismiss()
                            } else {
                                Toast.makeText(
                                    this@SignUpActivity,
                                    response.body()?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                DialogUtils.dismiss()
                            }
                        } else {
                            Toast.makeText(
                                this@SignUpActivity,
                                "Something wrong please try again later",
                                Toast.LENGTH_SHORT
                            ).show()
                            DialogUtils.dismiss()
                        }
                    }

                    override fun onFailure(call: Call<SignUpResponseModel>, t: Throwable) {
                        Toast.makeText(this@SignUpActivity, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun checkValidations(): Boolean {
        when {
            binding.signUpEdtFirstName.text.toString().isEmpty() -> {
                Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show()
                return false
            }
            binding.signUpEdtLastName.text.toString().isEmpty() -> {
                Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT).show()
                return false
            }
            binding.signUpEdtEmail.text.toString().isEmpty() -> {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                return false
            }
            binding.signUpEdtPassword.text.toString().isEmpty() -> {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                return false
            }
            binding.signUpEdtConfirmPassword.text.toString().isEmpty() -> {
                Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_SHORT).show()
                return false
            }
            binding.signUpEdtConfirmPassword.text.toString() != binding.signUpEdtPassword.text.toString() -> {
                Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> return true
        }
    }
}