package com.example.adaptivestreamingplayer.chatReaction

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.chatReaction.adapter.ChatReactionAdapter
import com.example.adaptivestreamingplayer.databinding.ChatReactionLayoutBinding
import com.example.adaptivestreamingplayer.utils.TextOrDrawable
import com.example.adaptivestreamingplayer.utils.createAnnotatedString

class ChatReactionActivity : AppCompatActivity() {

    private lateinit var binding: ChatReactionLayoutBinding

    private val dataOne:HashMap<Int, ArrayList<String>> = hashMapOf(
        0 to arrayListOf("Left - 0", "Middle - 0", "Right - 0"),
        1 to arrayListOf("Left - 1", "Middle - 1", "Right - 1"),
        2 to arrayListOf("Left - 2", "Middle - 2", "Right - 2"),
        3 to arrayListOf("Left - 3", "Middle - 3", "Right - 3"),
        4 to arrayListOf("Left - 4", "Middle - 4", "Right - 4"),
        5 to arrayListOf("Left - 5", "Middle - 5", "Right - 5"),
        6 to arrayListOf("Left - 6", "Middle - 6", "Right - 6"),
        7 to arrayListOf("Left - 7", "Middle - 7", "Right - 7"),
        8 to arrayListOf("Left - 8", "Middle - 8", "Right - 8"),
        9 to arrayListOf("Left - 9", "Middle - 9", "Right - 9"),
    )
    private val dataTwo:HashMap<Int, ArrayList<String>> = hashMapOf(
        0 to arrayListOf("Left - 10", "Middle - 10", "Right - 10"),
        1 to arrayListOf("Left - 11", "Middle - 11", "Right - 11"),
        2 to arrayListOf("Left - 12", "Middle - 12", "Right - 12"),
        3 to arrayListOf("Left - 13", "Middle - 13", "Right - 13"),
        4 to arrayListOf("Left - 14", "Middle - 14", "Right - 14"),
        5 to arrayListOf("Left - 15", "Middle - 15", "Right - 15"),
        6 to arrayListOf("Left - 16", "Middle - 16", "Right - 16"),
        7 to arrayListOf("Left - 17", "Middle - 17", "Right - 17"),
        8 to arrayListOf("Left - 18", "Middle - 18", "Right - 18"),
        9 to arrayListOf("Left - 19", "Middle - 19", "Right - 19"),
    )

    private val dataThree:HashMap<Int, ArrayList<String>> = hashMapOf(
        0 to arrayListOf("Left - 20", "Middle - 20", "Right - 20"),
        1 to arrayListOf("Left - 21", "Middle - 21", "Right - 21"),
        2 to arrayListOf("Left - 22", "Middle - 22", "Right - 22"),
        3 to arrayListOf("Left - 23", "Middle - 23", "Right - 23"),
        4 to arrayListOf("Left - 24", "Middle - 24", "Right - 24"),
        5 to arrayListOf("Left - 25", "Middle - 25", "Right - 25"),
        6 to arrayListOf("Left - 26", "Middle - 26", "Right - 26"),
        7 to arrayListOf("Left - 27", "Middle - 27", "Right - 27"),
        8 to arrayListOf("Left - 28", "Middle - 28", "Right - 28"),
        9 to arrayListOf("Left - 29", "Middle - 29", "Right - 29"),
    )

    private val dataFour:HashMap<Int, ArrayList<String>> = hashMapOf(
        0 to arrayListOf("Left - 30", "Middle - 30", "Right - 30"),
        1 to arrayListOf("Left - 31", "Middle - 31", "Right - 31"),
        2 to arrayListOf("Left - 32", "Middle - 32", "Right - 32"),
        3 to arrayListOf("Left - 33", "Middle - 33", "Right - 33"),
        4 to arrayListOf("Left - 34", "Middle - 34", "Right - 34"),
        5 to arrayListOf("Left - 35", "Middle - 35", "Right - 35"),
        6 to arrayListOf("Left - 36", "Middle - 36", "Right - 36"),
        7 to arrayListOf("Left - 37", "Middle - 37", "Right - 37"),
        8 to arrayListOf("Left - 38", "Middle - 38", "Right - 38"),
        9 to arrayListOf("Left - 39", "Middle - 39", "Right - 39"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ChatReactionLayoutBinding.inflate(layoutInflater)

        fun createTextWithImage(context: Context): SpannableStringBuilder {
            val drawable = ContextCompat.getDrawable(context, R.drawable.ic_reaction_symbol)?.apply {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight) // Set bounds directly
            }

            // Use a SpannableStringBuilder to directly insert the drawable as an ImageSpan
            return SpannableStringBuilder().apply {
                append("Why type when you can react ")

                drawable?.let {
                    val imageSpan = ImageSpan(it, ImageSpan.ALIGN_BOTTOM)
                    append(" ", imageSpan, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE) // Directly append drawable
                }

                append("?Quick, visible, powerful!")
            }
        }

        binding.tvChatTitle.text = createAnnotatedString(
            this,
            parts = listOf(
                TextOrDrawable.Text("Why type when you can react "),
                TextOrDrawable.DrawableRes(R.drawable.ic_reaction_symbol),
                TextOrDrawable.Text("?Quick, visible, powerful!"),
            )
        )

        binding.rvChatAdapterOne.adapter = ChatReactionAdapter(dataOne)
        binding.rvChatAdapterOne.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvChatAdapterTwo.adapter = ChatReactionAdapter(dataTwo)
        binding.rvChatAdapterTwo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvChatAdapterThree.adapter = ChatReactionAdapter(dataThree)
        binding.rvChatAdapterThree.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvChatAdapterFour.adapter = ChatReactionAdapter(dataFour)
        binding.rvChatAdapterFour.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        setContentView(binding.root)
    }
}
