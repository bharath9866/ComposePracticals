package com.example.adaptivestreamingplayer.onBoarding

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.adaptivestreamingplayer.databinding.ProgressButtonViewBinding

class ProgressButtonView : AppCompatActivity() {
    private lateinit var mBinding: ProgressButtonViewBinding
    @SuppressLint("LogNotTimber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            mBinding = ProgressButtonViewBinding.inflate(layoutInflater)
            setContentView(mBinding.root)
            "Get OTP on Whatsapp".also { mBinding.tvProgressTitle.text = it }

        } catch (e:Exception){
            Log.d("progressButtonException", "${e.message}")
        }

    }
}