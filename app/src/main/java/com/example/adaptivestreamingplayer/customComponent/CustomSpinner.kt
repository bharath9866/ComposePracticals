package com.example.adaptivestreamingplayer.customComponent

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner
import com.example.adaptivestreamingplayer.R


@SuppressLint("ViewConstructor")
class CustomSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    mode: Int
) : AppCompatSpinner(context, attrs, defStyleAttr, mode) {
    init {
        attrs?.let {
            initializeAttributes(it)
        }
    }

    @SuppressLint("CustomViewStyleable")
    private fun initializeAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpinnerStyle)
    }
}