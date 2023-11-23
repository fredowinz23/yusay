package com.capstone.yusayhub.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.yusayhub.MainActivity
import com.capstone.yusayhub.api.UserSession
import com.capstone.yusayhub.api.ApiInterface
import com.capstone.yusayhub.api.RetrofitClient
import com.capstone.yusayhub.databinding.ActivityLoginBinding
import com.capstone.yusayhub.debug.HostUrlActivity
import com.capstone.yusayhub.request.AuthRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.btnLogin.setOnClickListener {
            if (binding.etUsername.text.isEmpty() || binding.etPassword.text.isEmpty()){
                Toast.makeText(
                    this,
                    "Username and password must not be empty",
                    Toast.LENGTH_LONG
                ).show()
            }
            else{
                postLoginData(binding.etUsername.text.toString(),
                    binding.etPassword.text.toString())
            }
        }

        //Debug
        val userSession = UserSession(this@LoginActivity)
        binding.tvUserTestingDebug.text = userSession.BASE_URL

        binding.tvUserTestingDebug.setOnClickListener {
            startActivity(Intent(this@LoginActivity, HostUrlActivity::class.java))
        }
    }

    private fun postLoginData(username: String, password: String) {
        val retrofit = RetrofitClient.getInstance(this)
        val retrofitAPI = retrofit.create(ApiInterface::class.java)

        binding.btnLogin.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        val loginModal = AuthRequest(username, password)
        val call = retrofitAPI.loginUser(loginModal)

        call.enqueue(object : Callback<AuthRequest?> {
            override fun onResponse(call: Call<AuthRequest?>, response: Response<AuthRequest?>) {

                // we are getting response from our body
                // and passing it to our modal class.
                val responseFromAPI: AuthRequest? = response.body()

                if (responseFromAPI?.profile?.status == "Inactive"){
                    val userSession = UserSession(this@LoginActivity)
                    val intent = Intent(this@LoginActivity, ChangePasswordActivity::class.java)
                    intent.putExtra("username", responseFromAPI.profile!!.username)
                    startActivity(intent)
                    finish()
                }
                else if (!responseFromAPI?.profile?.username.isNullOrEmpty()){
                    val userSession = UserSession(this@LoginActivity)
                    userSession.edit?.putString("username", responseFromAPI?.profile?.username)
                    userSession.edit?.apply()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
                else{
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.visibility = View.VISIBLE
                    Toast.makeText(
                        this@LoginActivity,
                        "Username and Password not matched",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<AuthRequest?>, t: Throwable) {
                // setting text to our text view when
                // we get error response from API.

                Toast.makeText(
                    this@LoginActivity,
                    t.message.toString(),
                    Toast.LENGTH_LONG
                ).show()

                binding.progressBar.visibility = View.GONE
                binding.btnLogin.visibility = View.VISIBLE
                Log.e("Login Error", t.message.toString())
            }

        })
    }
}