package com.sureti.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sureti.R
import com.sureti.databinding.ActivitySplashBinding
import com.sureti.models.response.ParentResponseModel
import com.sureti.network.APIClient
import com.sureti.utils.AppConstants
import com.sureti.utils.DialogUtils
import com.sureti.utils.StorageUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    companion object {
        const val TAG = "SPLASH_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animMoveToTop = AnimationUtils.loadAnimation(this, R.anim.move_up);
        val animMoveToDown = AnimationUtils.loadAnimation(this, R.anim.move_down);

        binding.splashBarTop.startAnimation(animMoveToTop)
        binding.splashBarBottom.startAnimation(animMoveToDown)

        val isLogin = StorageUtility.getDataFromPreferences(
            this,
            AppConstants.SharedPreference.IS_USER_LOGGED_IN,
            false
        )

        Handler(Looper.myLooper()!!).postDelayed({
            if (isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else
                binding.splashCvCheckEmail.visibility = View.VISIBLE
        }, 2000)

        binding.splashBtnNext.setOnClickListener {
            val email: String = binding.splashEdtEmail.text.toString()
            if (email.isNotEmpty())
                checkEmail(email)
            else
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkEmail(email: String) {
        DialogUtils.showLoading(this, "Please wait!", "Checking email...")
        APIClient.isEmailExist(email, object : Callback<ParentResponseModel> {
            override fun onResponse(
                call: Call<ParentResponseModel>,
                response: Response<ParentResponseModel>
            ) {
                if (response.isSuccessful) {
                    val message = response.body()?.message
                    Log.d(TAG, "onResponse: $message")
                    if (message == "User Found.") {
                        //save email
                        StorageUtility.saveDataInPreferences(
                            this@SplashActivity,
                            AppConstants.SharedPreference.USER_EMAIL,
                            email
                        )
                        val intent = Intent(this@SplashActivity, SignInActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@SplashActivity,
                            "Email does not exist please sign up",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@SplashActivity, SignUpActivity::class.java)
                        startActivity(intent)
                    }
                    DialogUtils.dismiss()
                } else {
                    Toast.makeText(this@SplashActivity, response.message(), Toast.LENGTH_SHORT)
                        .show()
                    DialogUtils.dismiss()
                    Log.d(TAG, "onResponse: Fail  ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ParentResponseModel>, t: Throwable) {
                Toast.makeText(this@SplashActivity, t.message, Toast.LENGTH_SHORT).show()
                DialogUtils.dismiss()
            }
        })
    }
}