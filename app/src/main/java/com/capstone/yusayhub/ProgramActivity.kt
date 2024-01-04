package com.capstone.yusayhub

import android.app.AlertDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.yusayhub.api.ApiInterface
import com.capstone.yusayhub.api.RetrofitClient
import com.capstone.yusayhub.api.UserSession
import com.capstone.yusayhub.databinding.ActivityProgramListBinding
import com.capstone.yusayhub.request.JoinProgramRequest
import com.capstone.yusayhub.request.ProgramListRequest
import retrofit2.Call
import retrofit2.Response

class ProgramActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityProgramListBinding
    var programType = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProgramListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        programType = intent.extras?.getString("programType", "")!!

        getPrograms()

        if (programType=="allPrograms"){
            binding.tvTitle.text = "Events"
        }
        if (programType=="joinedPrograms"){
            binding.tvTitle.text = "Joined Future Events"
        }
        if (programType=="historyPrograms"){
            binding.tvTitle.text = "History"
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        getPrograms()
    }

    private fun getPrograms() {
        val retrofit = RetrofitClient.getInstance(this)
        val retrofitAPI = retrofit.create(ApiInterface::class.java)

        val userSession = UserSession(this)
        var dataRequest = ProgramListRequest(userSession.username!!, "Approved")
        var call = retrofitAPI.getPrograms(dataRequest)
        if (programType=="allPrograms"){
            dataRequest = ProgramListRequest(userSession.username, "Approved")
            call = retrofitAPI.getPrograms(dataRequest)
        }
        if (programType=="joinedPrograms"){
            dataRequest = ProgramListRequest(userSession.username, "Approved")
            call = retrofitAPI.getJoinedPrograms(dataRequest)
        }
        if (programType=="historyPrograms"){
            dataRequest = ProgramListRequest(userSession.username, "Closed")
            call = retrofitAPI.getJoinedPrograms(dataRequest)
        }

        call.enqueue(object : retrofit2.Callback<ProgramListRequest?> {
            override fun onResponse(call: Call<ProgramListRequest?>, response: Response<ProgramListRequest?>) {

                binding.progressBar.visibility = View.GONE

                val responseFromAPI: ProgramListRequest? = response.body()

                val groupLinear = LinearLayoutManager(this@ProgramActivity)
                binding.rvList.layoutManager = groupLinear
                val data = responseFromAPI?.program_list!!

                val adapter = ProgramAdapter(this@ProgramActivity, data)
                adapter.programType = programType
                binding.rvList.adapter = adapter
            }

            override fun onFailure(call: Call<ProgramListRequest?>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.e("Login Error", t.message.toString())

                Toast.makeText(
                    this@ProgramActivity,
                    "Internet Connection Error",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
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
                        this@ProgramActivity,
                        "Max Volunteers Reached",
                        Toast.LENGTH_LONG
                    ).show()
                }
                if (responseFromAPI?.response =="alreadyJoined"){
                    Toast.makeText(
                        this@ProgramActivity,
                        "You already joined this program",
                        Toast.LENGTH_LONG
                    ).show()
                }
                if (responseFromAPI?.response =="successfullyJoined"){
                    Toast.makeText(
                        this@ProgramActivity,
                        "You have successfully joined this program",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

            override fun onFailure(call: Call<JoinProgramRequest?>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.e("Login Error", t.message.toString())

                Toast.makeText(
                    this@ProgramActivity,
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

                getPrograms()
                Toast.makeText(
                    this@ProgramActivity,
                    "You have left from this program",
                    Toast.LENGTH_LONG
                ).show()

            }

            override fun onFailure(call: Call<JoinProgramRequest?>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.e("Login Error", t.message.toString())

                Toast.makeText(
                    this@ProgramActivity,
                    "Internet Connection Error",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun checkDialog(id: Int, description: String) {
        if (programType=="allPrograms"){
            joinDialog(id, description)
        }
        if (programType=="joinedPrograms"){
            unjoinDialog(id, description)
        }
        if (programType=="historyPrograms"){
        }
    }

    fun joinDialog(id: Int, description: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Program: $description")
        builder.setMessage("Would you like to join this program?")
        builder.setPositiveButton("Join") { dialog, which ->
            joinProgram(id)
        }

        builder.setNegativeButton("No") { dialog, which ->
        }

        builder.show()
    }

    fun unjoinDialog(id: Int, description: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Program: $description")
        builder.setMessage("Would you like to leave this program?")
        builder.setPositiveButton("Leave") { dialog, which ->
            unJoinProgram(id)
        }

        builder.setNegativeButton("No") { dialog, which ->
        }

        builder.show()
    }
}