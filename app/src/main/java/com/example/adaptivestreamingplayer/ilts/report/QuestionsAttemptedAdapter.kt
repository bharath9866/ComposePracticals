package com.example.adaptivestreamingplayer.ilts.report

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.adaptivestreamingplayer.R

class QuestionsAttemptedAdapter(
) : RecyclerView.Adapter<QuestionsAttemptedAdapter.ViewHolder>() {

    private val iltsReportTypes: ArrayList<ILTSReportType> = arrayListOf()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivGridItem)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.grid_item_questions_attempted,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setBackgroundColor(Color.TRANSPARENT)
        when(iltsReportTypes[position]) {
            ILTSReportType.UNATTEMPTED -> holder.imageView.setColorFilter(Color.parseColor("#D9DBE9"))
            ILTSReportType.INCORRECT -> holder.imageView.setColorFilter(Color.parseColor("#EC9191"))
            ILTSReportType.CORRECT_ANSWER -> holder.imageView.setColorFilter(Color.parseColor("#79BC92"))
        }
    }
    override fun getItemCount() : Int{
        return iltsReportTypes.size
    }
    fun updateList(listOfReportsType: ILTSReportModel) {
        updateAndInsertList(listOfReportsType)
        notifyDataSetChanged()
    }
    private fun updateAndInsertList(iltsReportModel: ILTSReportModel) {
        val unAttemptedList = List(iltsReportModel.unAttempted ?: 0) {
            ILTSReportType.UNATTEMPTED
        }
        this.iltsReportTypes.addAll(unAttemptedList)

        val correctList = List(iltsReportModel.correctAnswer ?: 0) {
            ILTSReportType.CORRECT_ANSWER
        }
        this.iltsReportTypes.addAll(correctList)

        val inCorrectList = List(iltsReportModel.inCorrect ?: 0) {
            ILTSReportType.INCORRECT
        }
        this.iltsReportTypes.addAll(inCorrectList)
    }

}

data class ILTSReportModel(
    val totalQuestions: Int? = 0,
    val correctAnswer: Int? = 0,
    val inCorrect: Int? = 0,
    val unAttempted: Int? = 0
)

enum class ILTSReportType {
    CORRECT_ANSWER, INCORRECT, UNATTEMPTED
}