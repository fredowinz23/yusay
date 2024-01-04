package com.capstone.yusayhub

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.yusayhub.api.ApiInterface
import com.capstone.yusayhub.api.RetrofitClient
import com.capstone.yusayhub.api.UserSession
import com.capstone.yusayhub.databinding.ActivityProfileBinding
import com.capstone.yusayhub.models.User
import com.capstone.yusayhub.request.ProfileDetailRequest
import retrofit2.Call
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        getProfileDetail()

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnUpdate.setOnClickListener {
            if (binding.etFirstName.text.isEmpty() ||
                binding.etLastName.text.isEmpty() ||
                binding.etPhone.text.isEmpty() ||
                binding.etEmail.text.isEmpty()){
                Toast.makeText(
                    this,
                    "Fields must not be empty",
                    Toast.LENGTH_LONG
                ).show()
            }
            else{
                postUpdatedProfile(binding.etFirstName.text.toString(),
                    binding.etLastName.text.toString(),
                    binding.etPhone.text.toString(),
                    binding.etEmail.text.toString())
            }
        }
    }

    private fun postUpdatedProfile(firstName: String, lastName: String, phone: String, email: String) {
        val retrofit = RetrofitClient.getInstance(this)
        val retrofitAPI = retrofit.create(ApiInterface::class.java)

        val userSession = UserSession(this)
        val dataRequest = User(0, userSession.username!!, firstName, lastName, phone, email)
        val call = retrofitAPI.updateProfileDetail(dataRequest)

        call.enqueue(object : retrofit2.Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {

                val responseFromAPI: User? = response.body()
                finish()
                Toast.makeText(
                        this@ProfileActivity,
                "Profile Updated Successfully",
                Toast.LENGTH_LONG
                ).show()

            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.e("Login Error", t.message.toString())

                Toast.makeText(
                    this@ProfileActivity,
                    "Internet Connection Error",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun getProfileDetail() {
        val retrofit = RetrofitClient.getInstance(this)
        val retrofitAPI = retrofit.create(ApiInterface::class.java)

        val userSession = UserSession(this)
        val dataRequest = ProfileDetailRequest(userSession.username!!)
        val call = retrofitAPI.getProfileDetail(dataRequest)

        call.enqueue(object : retrofit2.Callback<ProfileDetailRequest?> {
            override fun onResponse(call: Call<ProfileDetailRequest?>, response: Response<ProfileDetailRequest?>) {

                val responseFromAPI: ProfileDetailRequest? = response.body()
                val profile = responseFromAPI?.profile
                binding.etFirstName.setText(profile!!.firstName)
                binding.etLastName.setText(profile.lastName)
                binding.etPhone.setText(profile.phone)
                binding.etEmail.setText(profile.email)

            }

            override fun onFailure(call: Call<ProfileDetailRequest?>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.e("Login Error", t.message.toString())

                Toast.makeText(
                    this@ProfileActivity,
                    "Internet Connection Error",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}