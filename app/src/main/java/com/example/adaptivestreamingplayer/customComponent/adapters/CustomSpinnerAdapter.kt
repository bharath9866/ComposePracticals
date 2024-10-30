package com.example.adaptivestreamingplayer.customComponent.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.adaptivestreamingplayer.R


class CustomSpinnerAdapter(
    context: Context,
    private val values: Array<String>,
    private val images: IntArray
) :
    ArrayAdapter<String?>(context, R.layout.custom_spinner_item, values) {
    private val context: Context = context

    override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
        return createCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCustomView(position, convertView, parent)
    }

    private fun createCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row: View = inflater.inflate(R.layout.custom_spinner_item, parent, false)

        val textView = row.findViewById<TextView>(R.id.text_view)
        val imageView = row.findViewById<ImageView>(R.id.image_view)

        textView.text = values[position]
        imageView.setImageResource(images[position])

        return row
    }
}