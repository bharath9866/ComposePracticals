package com.example.adaptivestreamingplayer.customComponent.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.databinding.CustomSpinnerItemBinding


class CustomSpinnerAdapter(
    private val context: Context,
    private val values: List<String>
) : ArrayAdapter<String?>(context, R.layout.custom_spinner_item, values) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createCustomView(position, convertView, parent)
    }

    private fun createCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemBinding = CustomSpinnerItemBinding.inflate(inflater)

        itemBinding.textView.text = values[position]
        //itemBinding.imageView.setImageResource(images[position])

        return itemBinding.root
    }
}