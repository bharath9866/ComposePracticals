package com.example.adaptivestreamingplayer.facebookReactions.sample

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.facebookReactions.library.ReactionPopup
import com.example.adaptivestreamingplayer.facebookReactions.library.ReactionsConfigBuilder

class ReactionSampleActivity : AppCompatActivity() {
    private val strings = arrayOf("like", "love", "laugh", "wow", "sad", "angry")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sampleTopLeft()
        sampleCenterLeft()
        sampleBottomLeft()
        this.setupTopRight()
        this.setupRight()
    }

    private fun sampleCenterLeft() {
        val popup = ReactionPopup(
            this,
            ReactionsConfigBuilder(this)
                .withReactions(
                    intArrayOf(
                        R.drawable.ic_fb_like,
                        R.drawable.ic_fb_love,
                        R.drawable.ic_fb_laugh,
                        R.drawable.ic_fb_wow,
                        R.drawable.ic_fb_sad,
                        R.drawable.ic_fb_angry,
                    )
                )
                .withReactionTexts { position: Int? -> strings[position!!] }
                .build())

        findViewById<View>(R.id.facebook_btn).setOnTouchListener(popup)
    }

    private fun sampleTopLeft() {
        val popup = ReactionPopup(
            context = this,
            reactionsConfig = ReactionsConfigBuilder(this)
                .withReactions(
                    intArrayOf(
                        R.drawable.ic_fb_like,
                        R.drawable.ic_fb_love,
                        R.drawable.ic_fb_laugh,
                    )
                )
                .withPopupAlpha(20)
                .withReactionTexts { position: Int? -> strings[position!!] }
                .withTextBackground(ColorDrawable(Color.TRANSPARENT))
                .withTextColor(Color.BLACK)
                .withPopupCornerRadius(6)
                .withTextHorizontalPadding(0)
                .withTextVerticalPadding(0)
                .withTextSize(resources.getDimension(R.dimen.reactions_text_size))
                .build(),
            reactionSelectedListener = { true }
        )

        findViewById<View>(R.id.top_btn).setOnTouchListener(popup)
    }

    @SuppressLint("LogNotTimber")
    private fun sampleBottomLeft() {
        val margin = resources.getDimensionPixelSize(R.dimen.crypto_item_margin)

        val popup = ReactionPopup(
            context = this,
            reactionsConfig = ReactionsConfigBuilder(this)
                .withReactions(
                    intArrayOf(
                        R.drawable.ic_crypto_btc,
                        R.drawable.ic_crypto_eth,
                        R.drawable.ic_crypto_ltc,
                        R.drawable.ic_crypto_dash,
                        R.drawable.ic_crypto_xrp,
                        R.drawable.ic_crypto_xmr,
                        R.drawable.ic_crypto_doge,
                        R.drawable.ic_crypto_steem,
                        R.drawable.ic_crypto_kmd,
                        R.drawable.ic_crypto_zec
                    )
                )
                .withReactionTexts(R.array.crypto_symbols)
                .withPopupColor(Color.LTGRAY)
                .withReactionSize(resources.getDimensionPixelSize(R.dimen.crypto_item_size))
                .withHorizontalMargin(margin)
                .withVerticalMargin(margin / 2)
                .withTextBackground(ColorDrawable(Color.TRANSPARENT))
                .withTextColor(Color.BLACK)
                .withTextSize(resources.getDimension(R.dimen.reactions_text_size) * 1.5f)
                .build()
        )

        popup.reactionSelectedListener = { position: Int ->
            Log.d("Reactions","Selection position=$position")
            position != 3
        }

        findViewById<View>(R.id.crypto_bottom_left).setOnTouchListener(popup)
    }
}