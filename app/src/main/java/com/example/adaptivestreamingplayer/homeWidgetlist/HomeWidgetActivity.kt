package com.example.adaptivestreamingplayer.homeWidgetlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adaptivestreamingplayer.databinding.HomeWidgetLayoutBinding
import com.example.adaptivestreamingplayer.homeWidgetlist.adapter.HomeWidgetAdapter
import com.google.gson.annotations.SerializedName

class HomeWidgetActivity : AppCompatActivity() {

    private val binding: HomeWidgetLayoutBinding by lazy {
        HomeWidgetLayoutBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.rvHomeWidget.adapter =
            HomeWidgetAdapter(staticLearnHomeWidgetOrderLists.toList())
        setContentView(binding.root)
    }
}

/**
 * IMPORTANT
 * It is Default Widget Order Response which is maintained.
 */
val staticLearnHomeWidgetOrderLists = arrayListOf(
    OrderData(
        order = 1,
        layout = "USER_INTRO_LAYOUT",
    ),
    OrderData(
        order = 2,
        layout = "SEARCH_BAR_LAYOUT"
    ),
    OrderData(
        order = 3,
        layout = "WIDGET_BANNER_LAYOUT"
    ),
    OrderData(
        order = 4,
        layout = "FREE_MASTER_LIVE_CLASS_LAYOUT",
    ),
    OrderData(
        order = 5,
        layout = "SELFLEARN_SUBJECTS_LAYOUT"
    ),
    OrderData(
        order = 6,
        layout = "ENGAGING_LIVE_CLASS_LAYOUT"
    )
)

data class OrderData(
    @SerializedName("order") var order: Int? = null,
    @SerializedName("layout") var layout: String? = null,
)