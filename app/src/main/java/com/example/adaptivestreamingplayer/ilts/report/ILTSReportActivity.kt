package com.example.adaptivestreamingplayer.ilts.report

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.adaptivestreamingplayer.databinding.ActivityIltsReportBinding

class ILTSReportActivity : ComponentActivity() {

    private lateinit var mBinding: ActivityIltsReportBinding
    var score = 400f

    var iltsReportModel = ILTSReportModel(700, 200, 200, 300)
    val listOfReportsType = arrayListOf<ILTSReportType>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityIltsReportBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.jeeIltsBackBtn.setOnClickListener {
            finish()
        }

        mBinding.scoreProgressView.setTotalScore(800f)
        mBinding.jeeAdvViewSolution.setOnClickListener {
            if(score == 0f){
                mBinding.scoreProgressView.setScoreInSweepAngle(200f)
                // mBinding.arcImg.progressValidation( 200f)
                score = 200f
            } else if(score == 200f){
                mBinding.scoreProgressView.setScoreInSweepAngle(400f)
                // mBinding.arcImg.progressValidation( 400f)
                score = 400f
            } else if(score == 400f){
                mBinding.scoreProgressView.setScoreInSweepAngle(600f)
                // mBinding.arcImg.progressValidation( 600f)
                score = 600f
            } else if(score == 600f){
                mBinding.scoreProgressView.setScoreInSweepAngle(800f)
                // mBinding.arcImg.progressValidation( 800f)
                score = 800f
            } else {
                mBinding.scoreProgressView.progressValidation( 0f)
                // mBinding.arcImg.progressValidation( 0f)
                score = 0f
            }
        }

        val adapter = QuestionsAttemptedAdapter()
        mBinding.includeIltsReportQABody.apply {
            tvQATotalQuestions.text = iltsReportModel.totalQuestions.toString()
            tvQACorrectQuestions.text = iltsReportModel.correctAnswer.toString()
            tvQAIncorrectQuestions.text = iltsReportModel.inCorrect.toString()
            tvQAUnattemptedQuestions.text = iltsReportModel.unAttempted.toString()
            rvQuestionsAttempted.layoutManager = GridLayoutManager(this@ILTSReportActivity, 10)
            rvQuestionsAttempted.adapter = adapter
        }

        adapter.updateList(iltsReportModel)
    }


}