package com.example.adaptivestreamingplayer.chatReaction.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.databinding.ChatReactionItemBinding
import com.example.adaptivestreamingplayer.facebookReactions.library.PopupGravity
import com.example.adaptivestreamingplayer.facebookReactions.library.ReactionPopup
import com.example.adaptivestreamingplayer.facebookReactions.library.ReactionsConfigBuilder

class ChatReactionAdapter(
    private val data: HashMap<Int, ArrayList<String>>
) : RecyclerView.Adapter<ChatReactionAdapter.MyViewHolder>() {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ChatReactionItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class MyViewHolder(private var binding: ChatReactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(item: ArrayList<String>?) {

            val reactionMap = mapOf(
                "Like" to R.drawable.ic_like_intro,
                "Love" to R.drawable.ic_heart_intro,
                "+1" to R.drawable.ic_plus_one_intro,
                "Celebrate" to R.drawable.ic_clap_intro,
                "Confused" to R.drawable.ic_thinking_intro
            )

            val popup = ReactionPopup(
                context = itemView.context,
                reactionsConfig = ReactionsConfigBuilder(itemView.context)
                    .withReactions(reactionMap.values.toIntArray())
                    .withReactionTexts { reactionPosition ->
                        reactionPosition.let {
                            reactionMap.keys.elementAt(
                                it
                            )
                        }
                    }
                    .withPopupGravity(PopupGravity.PARENT_RIGHT)
                    .withPopupCornerRadius(9)
                    .build()
            )

            binding.tvLeft.text = item?.get(0).toString()
            binding.tvLeft.setOnTouchListener(popup)

            binding.tvMiddle.text = item?.get(1).toString()
            binding.tvMiddle.setOnTouchListener(popup)

            binding.tvRight.text = item?.get(2).toString()
            binding.tvRight.setOnTouchListener(popup)

            popup.reactionSelectedListener = {
                Toast.makeText(
                    binding.root.context,
                    "Selected reaction: $it",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }
        }

    }
}
