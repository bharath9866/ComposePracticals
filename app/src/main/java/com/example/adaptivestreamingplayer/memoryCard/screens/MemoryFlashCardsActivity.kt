package com.example.adaptivestreamingplayer.memoryCard.screens

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.databinding.ActivityMemoryFlashCardsBinding
import com.example.adaptivestreamingplayer.memoryCard.libraries.ZoomRecyclerLayout
import com.example.adaptivestreamingplayer.memoryCard.model.Back
import com.example.adaptivestreamingplayer.memoryCard.model.BookMarkedMemoryCardResponse
import com.example.adaptivestreamingplayer.memoryCard.model.Flashcards
import com.example.adaptivestreamingplayer.memoryCard.model.Front
import com.example.adaptivestreamingplayer.memoryCard.model.MemoryFlashcardResponse
import com.example.utils.lottieLoad
import com.example.utils.readJSONFromAssets
import com.google.gson.Gson

class MemoryFlashCardsActivity : ComponentActivity() {
    private var flashCardAdapter: FlashCardsAdapter? = null
    private var flash_cards_recyclerview: RecyclerView? = null
    private var close: ImageView? = null
    var lastCardPosition: Int = 0

    private lateinit var flashcardJson: String
    private lateinit var bookmarkJson: String

    private lateinit var binding: ActivityMemoryFlashCardsBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMemoryFlashCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        flash_cards_recyclerview = findViewById(R.id.flash_cards_recyclerview)

        close = findViewById(R.id.back)
        close?.setOnClickListener {
            onBackPressed()
        }
        flashcardJson = readJSONFromAssets(this, "memoryCard.json")
        val flashcardsData = Gson().fromJson(flashcardJson, MemoryFlashcardResponse::class.java)

        bookmarkJson = readJSONFromAssets(this, "getBookmarkedMemoryCardsByChapter.json")
        val bookmarksData = Gson().fromJson(bookmarkJson, BookMarkedMemoryCardResponse::class.java)


        binding.continueBtn.setOnClickListener {
            onBackPressed()
        }


        /* val disabler = RecyclerViewDisabler(true)
         flash_cards_recyclerview!!.addOnItemTouchListener(disabler)
         disabler.isEnable=true*/

        binding.swipeUpLayout.setOnTouchListener(OnTouchListener { v, event ->
            binding.swipeUpLayout.visibility = View.GONE
            // memoryFlashcardViewModel.sessionManager.setSwipeUpFlag(true)
            false
        })
        flashcardsData?.let {
            val flashCards = it.data?.flashcards

            var staObj = Flashcards(
                flashcardId = 1,
                title = "",
                typeId = 4,
                type = "Memory Flashcard",
                front = Front(
                    text = "Dummy Front",
                    imageURL = "",
                    isImageRequired = false,
                    orientation = "Landscape"
                ),
                back = Back(
                    text = "",
                    imageURL = "",
                    isImageRequired = false,
                    orientation = "Landscape"
                )
            )
            flashCards?.add(staObj)

            bookmarksData?.let {
                val bookMarkedMemorya = it.data

                for (i in bookMarkedMemorya ?: ArrayList()) {
                    for (j in flashCards!!.indices) {
                        if (i.isBookmarked == true && i.memoryCardId == flashCards[j].flashcardId)
                            flashCards[j].isBookmarked = true
                    }
                }

                if (flashCards != null && flashCards?.size!! > 0) {
                    val linearLayoutManager = ZoomRecyclerLayout(this)
                    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    //linearLayoutManager.reverseLayout = true
                    flash_cards_recyclerview?.layoutManager = linearLayoutManager
                    val snapHelper1: SnapHelper = PagerSnapHelper()
                    snapHelper1.attachToRecyclerView(flash_cards_recyclerview)
                    flashCardAdapter = FlashCardsAdapter(this, flashCards)
                    flash_cards_recyclerview!!.adapter = flashCardAdapter

                    flash_cards_recyclerview?.addOnScrollListener(object :
                        RecyclerView.OnScrollListener() {
                        @SuppressLint("ClickableViewAccessibility")
//                        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                            super.onScrollStateChanged(recyclerView, newState)
//
////                            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
////                                val layoutManager = flash_cards_recyclerview?.layoutManager
////                                val currentPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
////                                Log.d("getCurrentPositionw", "TAG${flash_cards_recyclerview?.getCurrentPosition()}-----"+"${flashCards?.size}")
////                            }
//                        }

                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            if (dy > 0) {
                                Log.d("Scrolling_up", "Scrolling_up")
                                val layoutManager = flash_cards_recyclerview?.layoutManager
                                val currentPosition =
                                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                                if (currentPosition == (flashCards?.size ?: 0) - 1) {
                                    binding.finishCard.visibility = View.VISIBLE
                                    lottieLoad(binding.gifImage, R.raw.memory_flash_gif)
                                }
                                // Scrolling up
                            } else if ((dy < 0)) {
                                Log.d("Scrolling_Down", "Scrolling_Down")

                            }
                        }
                    })


                    if (bookMarkedMemorya.size < flashCards.size)
                        flash_cards_recyclerview!!.scrollToPosition(bookMarkedMemorya.size - 1)
                    slideUp(flash_cards_recyclerview!!)
                }
            }
        }
    }

//    private fun RecyclerView.getCurrentPosition(): Int {
//        return (this?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
//    }

    private fun RecyclerView.getCurrentPosition(): Int {
        val layoutManager = this.layoutManager as? LinearLayoutManager
        return layoutManager?.findFirstVisibleItemPosition() ?: RecyclerView.NO_POSITION
    }

    private fun slideUp(view: View) {
        val height = view.height
        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, height.toFloat(), 0f)
            .apply {
                duration = 1000
                start()
            }
    }
}

class RecyclerViewDisabler(isEnable: Boolean) : RecyclerView.OnItemTouchListener {
    var isEnable = true

    init {
        this.isEnable = isEnable
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return !isEnable
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
}