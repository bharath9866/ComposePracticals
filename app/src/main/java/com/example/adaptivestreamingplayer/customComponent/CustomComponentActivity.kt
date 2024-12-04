package com.example.adaptivestreamingplayer.customComponent

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.ComponentActivity
import com.example.adaptivestreamingplayer.customComponent.models.CountryDetail
import com.example.adaptivestreamingplayer.databinding.CustomComponentActivityBinding

class CustomComponentActivity: ComponentActivity(), CustomSpinner.OnItemSelectedListener {
    private lateinit var binding:CustomComponentActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomComponentActivityBinding.inflate(layoutInflater, null, false)
        binding.clPhoneNo.countryCodePicker.initOnItemSelectedListener(this)
        binding.customSpinner.initOnItemSelectedListener(this)

        if(binding.customSpinner.spinner!= null) {
            binding.customSpinner.spinner?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (view != null) {
                        binding.customSpinner.adapter?.setSelectedItemPosition(position)
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        setContentView(binding.root)
    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long,
        dataList: ArrayList<CountryDetail>
    ) {
        Log.d("onItemSelected", "position: $position, view:$view, parent:$parent, id:$id, dataList: $dataList")
    }
}