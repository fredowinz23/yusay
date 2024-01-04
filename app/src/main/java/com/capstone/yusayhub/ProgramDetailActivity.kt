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
import com.capstone.yusayhub.databinding.ActivityProgramDetailBinding
import com.capstone.yusayhub.models.Program
import com.capstone.yusayhub.request.JoinProgramRequest
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

class ProgramDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgramDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProgramDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val id = intent.extras!!.getInt("id")
        val title = intent.extras!!.getString("title")
        val joiners = intent.extras!!.getString("joiners")
        val description = intent.extras!!.getString("description")
        val address = intent.extras!!.getString("address")
        val dateTime = intent.extras!!.getString("dateTime")
        val image = intent.extras!!.getString("image")
        val programType = intent.extras!!.getString("programType")

        binding.tvTitle.text = title
        binding.tvJoiners.text = joiners
        binding.tvDescription.text = description
        binding.tvAddress.text = "@ $address"
        binding.tvDateTime.text = dateTime
        Picasso.with(this).load(image).fit().centerCrop()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .into(binding.ivImage)

        if (programType=="allPrograms"){
            binding.btnJoin.visibility = View.VISIBLE
        }
        if (programType=="joinedPrograms"){
            binding.btnUnjoin.visibility = View.VISIBLE
        }

        binding.btnJoin.setOnClickListener {
            joinProgram(id)
        }

        binding.btnUnjoin.setOnClickListener {
            unJoinProgram(id)
        }
    }

    private fun joinProgram(programId: Int) {
        val retrofit = RetrofitClient.getInstance(this)
        val retrofitAPI = retrofit.create(ApiInterface::class.java)

        val userSession = UserSession(this)
        val dataRequest = JoinProgramRequest(userSession.username!!, programId)
        val call = retrofitAPI.joinProgram(dataRequest)

        call.enqueue(object : retrofit2.Callback<JoinProgramRequest?> {
            override fun onResponse(call: Call<JoinProgramRequest?>, response: Response<JoinProgramRequest?>) {

                val responseFromAPI: JoinProgramRequest? = response.body()

                if (responseFromAPI?.response =="reachedMaxVolunteer"){
                    Toast.makeText(
                        this@ProgramDetailActivity,
                        "Max Volunteers Reached",
                        Toast.LENGTH_LONG
                    ).show()
                }
                if (responseFromAPI?.response =="alreadyJoined"){
                    Toast.makeText(
                        this@ProgramDetailActivity,
                        "You already joined this program",
                        Toast.LENGTH_LONG
                    ).show()
                }
                if (responseFromAPI?.response =="successfullyJoined"){
                    Toast.makeText(
                        this@ProgramDetailActivity,
                        "You have successfully joined this program",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }

            }

            override fun onFailure(call: Call<JoinProgramRequest?>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.e("Login Error", t.message.toString())

                Toast.makeText(
                    this@ProgramDetailActivity,
                    "Internet Connection Error",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun unJoinProgram(programId: Int) {
        val retrofit = RetrofitClient.getInstance(this)
        val retrofitAPI = retrofit.create(ApiInterface::class.java)

        val userSession = UserSession(this)
        val dataRequest = JoinProgramRequest(userSession.username!!, programId)
        val call = retrofitAPI.unjoinProgram(dataRequest)

        call.enqueue(object : retrofit2.Callback<JoinProgramRequest?> {
            override fun onResponse(call: Call<JoinProgramRequest?>, response: Response<JoinProgramRequest?>) {

                val responseFromAPI: JoinProgramRequest? = response.body()

                finish()
                Toast.makeText(
                    this@ProgramDetailActivity,
                    "You have left from this program",
                    Toast.LENGTH_LONG
                ).show()

            }

            override fun onFailure(call: Call<JoinProgramRequest?>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.e("Login Error", t.message.toString())

                Toast.makeText(
                    this@ProgramDetailActivity,
                    "Internet Connection Error",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}