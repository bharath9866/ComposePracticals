package com.example.adaptivestreamingplayer.interview

import android.R.attr.data
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adaptivestreamingplayer.databinding.ActivityIltsReportBinding

data class Data(val name: String = "", val age: Int = 0)
class ThreadBasedQuestion : AppCompatActivity() {

    private lateinit var binding: ActivityIltsReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIltsReportBinding.inflate(layoutInflater)

        val thread = Thread {
            val userData = fetchUserData()
            runOnUiThread {
                updateData(Data())
            }
        }
        thread.start()
        setContentView(binding.root)
    }
}

fun fetchUserData():String {
    Thread.sleep(2000)
    return ""
}

fun updateData(data: Data) {

}