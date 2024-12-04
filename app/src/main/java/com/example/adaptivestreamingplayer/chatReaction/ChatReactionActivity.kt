package com.example.adaptivestreamingplayer.chatReaction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adaptivestreamingplayer.chatReaction.adapter.ChatReactionAdapter
import com.example.adaptivestreamingplayer.databinding.ChatReactionLayoutBinding

class ChatReactionActivity : AppCompatActivity() {

    private lateinit var binding: ChatReactionLayoutBinding

    val data:HashMap<Int, ArrayList<String>> = hashMapOf(
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
        10 to arrayListOf("Left - 10", "Middle - 10", "Right - 10"),
        11 to arrayListOf("Left - 11", "Middle - 11", "Right - 11"),
        12 to arrayListOf("Left - 12", "Middle - 12", "Right - 12"),
        13 to arrayListOf("Left - 13", "Middle - 13", "Right - 13"),
        14 to arrayListOf("Left - 14", "Middle - 14", "Right - 14"),
        15 to arrayListOf("Left - 15", "Middle - 15", "Right - 15"),
        16 to arrayListOf("Left - 16", "Middle - 16", "Right - 16"),
        17 to arrayListOf("Left - 17", "Middle - 17", "Right - 17"),
        18 to arrayListOf("Left - 18", "Middle - 18", "Right - 18"),
        19 to arrayListOf("Left - 19", "Middle - 19", "Right - 19"),
        20 to arrayListOf("Left - 20", "Middle - 20", "Right - 20"),
        21 to arrayListOf("Left - 21", "Middle - 21", "Right - 21"),
        22 to arrayListOf("Left - 22", "Middle - 22", "Right - 22"),
        23 to arrayListOf("Left - 23", "Middle - 23", "Right - 23"),
        24 to arrayListOf("Left - 24", "Middle - 24", "Right - 24"),
        25 to arrayListOf("Left - 25", "Middle - 25", "Right - 25"),
        26 to arrayListOf("Left - 26", "Middle - 26", "Right - 26"),
        27 to arrayListOf("Left - 27", "Middle - 27", "Right - 27"),
        28 to arrayListOf("Left - 28", "Middle - 28", "Right - 28"),
        29 to arrayListOf("Left - 29", "Middle - 29", "Right - 29"),
        30 to arrayListOf("Left - 30", "Middle - 30", "Right - 30"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ChatReactionLayoutBinding.inflate(layoutInflater)

        binding.rvChatAdapter.adapter = ChatReactionAdapter(data)
        binding.rvChatAdapter.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        setContentView(binding.root)
    }
}
