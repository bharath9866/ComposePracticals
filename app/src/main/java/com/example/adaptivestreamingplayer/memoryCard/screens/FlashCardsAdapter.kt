package com.example.adaptivestreamingplayer.memoryCard.screens

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.memoryCard.model.Flashcards
import com.example.memorycards.mathView.MathView
import com.example.utils.isTabletOrMobile

class FlashCardsAdapter(
    private val context: Context,
    private val flashCarsBeanList: ArrayList<Flashcards>,
) : RecyclerView.Adapter<FlashCardsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardsHolder {
        val viewHolder: FlashCardsHolder
        if (isTabletOrMobile(context)) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_layout_flash_cards_sl_tab, parent, false)
            viewHolder = FlashCardsHolder(view)
            return viewHolder
        } else {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_layout_flash_cards_sl, parent, false)
            viewHolder = FlashCardsHolder(view)
            return viewHolder
        }
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: FlashCardsHolder, position: Int) {
        val pos: Int = position + 1

        Glide.with(context).load(flashCarsBeanList.get(position).back?.imageURL)
            .fitCenter()
            .into(holder.answer_image)
        Glide.with(context).load(flashCarsBeanList.get(position).front?.imageURL)
            .fitCenter()
            .into(holder.question_image)
        if(flashCarsBeanList.get(position).back?.imageURL!=null&&flashCarsBeanList.get(position).back?.imageURL!=""){
            holder.answer_image.visibility=View.VISIBLE
        }else{
            holder.answer_image.visibility=View.GONE
        }
        if(flashCarsBeanList.get(position).front?.imageURL!=null&&flashCarsBeanList.get(position).front?.imageURL!=""){
            holder.question_image.visibility=View.VISIBLE
        }else{
            holder.question_image.visibility=View.GONE
        }
        holder.question_count.text = pos.toString() + "/" + flashCarsBeanList.size.minus(1)
        holder.answer_count.text = pos.toString() + "/" + flashCarsBeanList.size.minus(1)
        holder.ans_web_view.setDisplayText(flashCarsBeanList.get(position).back?.text)
        holder.question_web_view.setDisplayText(flashCarsBeanList.get(position).front?.text)
        var scale = context.resources.displayMetrics.density
        lateinit var front_anim: AnimatorSet
        lateinit var back_animation: AnimatorSet
        holder.question_card.cameraDistance = 8000 * scale
        holder.answer_card.cameraDistance = 8000 * scale
        front_anim =
            AnimatorInflater.loadAnimator(context, R.animator.front_animator) as AnimatorSet
        back_animation =
            AnimatorInflater.loadAnimator(context, R.animator.back_animator) as AnimatorSet
        holder.question_card.setOnClickListener {
            front_anim.setTarget(holder.question_card);
            back_animation.setTarget(holder.answer_card);
            front_anim.start()
            back_animation.start()
            holder.question_card.isClickable = false
            holder.answer_card.isClickable = true
        }
        holder.answer_card.setOnClickListener {
            front_anim.setTarget(holder.answer_card);
            back_animation.setTarget(holder.question_card);
            front_anim.start()
            back_animation.start()
            holder.answer_card.isClickable = false
            holder.question_card.isClickable = true
        }

        if (flashCarsBeanList.get(position).isBookmarked == true) {
            holder.book_marked.visibility = View.VISIBLE
            holder.book_mark.visibility = View.GONE
        } else {
            holder.book_marked.visibility = View.GONE
            holder.book_mark.visibility = View.VISIBLE
        }
        holder.book_mark.setOnClickListener {
            holder.book_marked.visibility = View.VISIBLE
            holder.book_mark.visibility = View.GONE
            flashCarsBeanList.get(position).isBookmarked = true
        }

        holder.question_web_view.setOnTouchListener(
            @SuppressLint("ClickableViewAccessibility")
            object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    if (event!!.action === MotionEvent.ACTION_MOVE) {
                        return false
                    }

                    if (event!!.action === MotionEvent.ACTION_UP) {
                        if(holder.question_card.isClickable) {
                            front_anim.setTarget(holder.question_card);
                            back_animation.setTarget(holder.answer_card);
                            front_anim.start()
                            back_animation.start()
                            holder.question_card.isClickable = false
                            holder.answer_card.isClickable = true
                        }else if(holder.answer_card.isClickable) {
                            front_anim.setTarget(holder.answer_card);
                            back_animation.setTarget(holder.question_card);
                            front_anim.start()
                            back_animation.start()
                            holder.answer_card.isClickable = false
                            holder.question_card.isClickable = true
                        }
                    }
                    return false
                }
            }
        )

    }


    override fun getItemCount(): Int {
        return flashCarsBeanList.size
    }

}

class FlashCardsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var question_image: ImageView
    var answer_image: ImageView
    var question_count: TextView
    var question_web_view: MathView
    var ans_web_view: MathView
    var answer_card: CardView
    var tap_to_answer: TextView
    var question_card: CardView
    var answer_count: TextView
    var book_mark: LinearLayout
    var book_marked: LinearLayout

    init {
        answer_image = itemView.findViewById(R.id.answer_image)
        book_marked = itemView.findViewById(R.id.book_marked)
        book_mark = itemView.findViewById(R.id.book_mark)
        ans_web_view = itemView.findViewById(R.id.ans_web_view)
        answer_count = itemView.findViewById(R.id.answer_count)
        question_card = itemView.findViewById(R.id.question_card)
        answer_card = itemView.findViewById(R.id.answer_card)
        tap_to_answer = itemView.findViewById(R.id.tap_to_answer)
        question_web_view = itemView.findViewById(R.id.question_web_view)
        question_count = itemView.findViewById(R.id.question_count)
        question_image = itemView.findViewById(R.id.question_image);
    }
}