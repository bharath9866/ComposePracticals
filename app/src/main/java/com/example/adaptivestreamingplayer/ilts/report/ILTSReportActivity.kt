package com.example.adaptivestreamingplayer.ilts.report

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.adaptivestreamingplayer.databinding.ActivityIltsReportBinding

class ILTSReportActivity : ComponentActivity() {

    private lateinit var mBinding: ActivityIltsReportBinding
    var score = 400f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityIltsReportBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.jeeIltsBackBtn.setOnClickListener {
            finish()
        }

        mBinding.jeeAdvViewSolution.setOnClickListener {
            if(score == 0f){
                mBinding.customView.progressValidation( 200f)
                // mBinding.arcImg.progressValidation( 200f)
                score = 200f
            } else if(score == 200f){
                mBinding.customView.progressValidation( 400f)
                // mBinding.arcImg.progressValidation( 400f)
                score = 400f
            } else if(score == 400f){
                mBinding.customView.progressValidation( 600f)
                // mBinding.arcImg.progressValidation( 600f)
                score = 600f
            } else if(score == 600f){
                mBinding.customView.progressValidation( 800f)
                // mBinding.arcImg.progressValidation( 800f)
                score = 800f
            } else {
                mBinding.customView.progressValidation( 0f)
                // mBinding.arcImg.progressValidation( 0f)
                score = 0f
            }
        }

    }
}