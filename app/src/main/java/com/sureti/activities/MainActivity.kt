package com.sureti.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sureti.adapters.BranchCollaboratorAdapter
import com.sureti.adapters.CollaboratorAdapter
import com.sureti.databinding.ActivityMainBinding
import com.sureti.models.ActiveCheckModel
import com.sureti.models.response.ActiveCheckResponseModel
import com.sureti.network.APIClient
import com.sureti.utils.AppConstants
import com.sureti.utils.DialogUtils
import com.sureti.utils.StorageUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var listActiveCheckModel: List<ActiveCheckModel>

    companion object {
        const val TAG = "MAIN_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getActiveChecks()

    }

    private fun getActiveChecks() {
        DialogUtils.showLoading(this, "Please Wait!", "Loading data...")
        val token = StorageUtility.getDataFromPreferences(
            this,
            AppConstants.SharedPreference.USER_ACCESS_TOKEN,
            ""
        )
        APIClient.getActiveChecks(token!!, object : Callback<ActiveCheckResponseModel> {
            override fun onResponse(
                call: Call<ActiveCheckResponseModel>,
                response: Response<ActiveCheckResponseModel>
            ) {
                if (response.isSuccessful) {
                    listActiveCheckModel = response.body()!!.data
                    if (listActiveCheckModel.isNotEmpty())
                        setData()
                    else
                        Toast.makeText(
                            this@MainActivity,
                            "No data found, something wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                } else {
                    Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_SHORT).show()
                    DialogUtils.dismiss()
                }
            }

            override fun onFailure(call: Call<ActiveCheckResponseModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                DialogUtils.dismiss()
            }
        })
    }

    private fun setData() {
        val activeCheckModel = listActiveCheckModel[0]

        binding.mainTvUserName.text = activeCheckModel.userInfo.firstName
        binding.mainTvCheckNo.text = activeCheckModel.checkNo
        binding.mainTvAmount.text = activeCheckModel.checkAmount.toString()
        binding.mainTvCheckStatus.text = activeCheckModel.checkStatus
        binding.mainTvClaim.text = activeCheckModel.claimNo
        binding.mainTvLoan.text = activeCheckModel.loannumber
        binding.mainTvPreferredContractor.text = activeCheckModel.preferredcontractor
        binding.mainTvBranchName.text = activeCheckModel.branch.branchname
        binding.mainTvLossAddress.text = activeCheckModel.addressofloss
        binding.mainTvAptSte.text = activeCheckModel.branch.aptste

        val recyclerViewPolicy = binding.mainPolicyHolderRecyclerView
        val recyclerViewBranch = binding.mainBranchRecyclerView

        recyclerViewPolicy.setHasFixedSize(true)
        recyclerViewBranch.setHasFixedSize(true)

        recyclerViewPolicy.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewBranch.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)

        val adapterPolicy = CollaboratorAdapter(activeCheckModel.collabortator)
        val adapterBranch = BranchCollaboratorAdapter(activeCheckModel.branchcollabortator)

        recyclerViewPolicy.adapter = adapterPolicy
        recyclerViewBranch.adapter = adapterBranch
        DialogUtils.dismiss()

    }
}