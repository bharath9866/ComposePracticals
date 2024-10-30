package com.example.adaptivestreamingplayer.slThree

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.adaptivestreamingplayer.databinding.CreatePlanLayoutBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CreatePlanActivity:AppCompatActivity() {

    private lateinit var binding:CreatePlanLayoutBinding

    private var lcCreatePlan:CreatePlan? by mutableStateOf(null)

    private val createPlanViewModel:CreatePlanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CreatePlanLayoutBinding.inflate(layoutInflater)

        createPlanViewModel.callCardData()

        lifecycleScope.launch {
            createPlanViewModel.cardData.collectLatest { lcCreatePlan = it }
        }

        binding.cvCreatePlan.setContent {
            lcCreatePlan?.let {
                CreatePlanCard(
                    cardData = it,
                    onClickIncrement = {
                        createPlanViewModel.incrementValue(lcCreatePlan?.incrementValue?:0)
                    }
                )
            } ?: CreatePlanCardShimmer()
        }
        setContentView(binding.root)
    }
}