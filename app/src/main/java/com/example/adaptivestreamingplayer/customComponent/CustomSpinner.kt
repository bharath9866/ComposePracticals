package com.example.adaptivestreamingplayer.customComponent

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.customComponent.models.CountryDetail
import com.google.android.material.imageview.ShapeableImageView

class CustomSpinner : FrameLayout {

    private var selectedCountry: CountryDetail = CountryDetail()
    var selectedCountryDetail:CountryDetail? = null
    private var dropdownLayout:Int = 0
    private var normalLayout:Int = 0
    private var popUpBackground:Int = 0
    private var background:Int = 0
    private var spinnerResourceId:Int = 0
    var onItemSelectedListener: OnItemSelectedListener? = null
    companion object {
        const val ON_BOARDING_SPINNER = 0
    }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    @SuppressLint("CustomViewStyleable", "Recycle")
    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        initTypedArray(context.obtainStyledAttributes(attr, R.styleable.CustomSpinner))
        initView(context)
    }

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(context, attr, defStyleAttr) {
        initTypedArray(context.obtainStyledAttributes(attr, R.styleable.CustomSpinner))
        initView(context)
    }

    private fun initTypedArray(typedArray: TypedArray?) {
        if (typedArray != null) {
            dropdownLayout = typedArray.getResourceId(R.styleable.CustomSpinner_dropDownLayout, 0)
            normalLayout = typedArray.getResourceId(R.styleable.CustomSpinner_normalLayout, 0)
            popUpBackground = typedArray.getResourceId(R.styleable.CustomSpinner_android_popupBackground, 0)
            background = typedArray.getResourceId(R.styleable.CustomSpinner_android_background, 0)
            typedArray.recycle()
        }
    }
    @SuppressLint("LogNotTimber")
    private fun initView(context: Context) {
        val rootView = (context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.custom_spinner, this, true)

        setUpOnBoardingSpinner(rootView, context)
    }

    private fun setUpOnBoardingSpinner(rootView: View, context: Context) {
        val spinner = rootView.findViewById<Spinner>(R.id.spinner)

        val adapter = SpinnerAdapter(
            context = context,
            dropdownLayout = dropdownLayout,
            normalLayout = normalLayout,
            countries = countryList
        )

        spinner.adapter = adapter
        spinner.setPopupBackgroundResource(popUpBackground)
        spinner.setBackgroundResource(background)

        if (spinner != null) {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (view != null) {
                        adapter.setSelectedItemPosition(position)
                        selectedCountry = countryList[position]
                        selectedCountryDetail = selectedCountry
                        onItemSelectedListener?.onItemSelected(
                            parent = parent,
                            view = view,
                            position = position,
                            id = id,
                            dataList = countryList
                        )
                    } else return
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    fun initOnItemSelectedListener(onItemSelectedListener: OnItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener
    }

    class SpinnerAdapter (
        context: Context,
        val dropdownLayout: Int,
        val normalLayout: Int,
        private val countries: List<CountryDetail>,
    ) : ArrayAdapter<CountryDetail>(context, 0, countries) {

        private val inflater: LayoutInflater = LayoutInflater.from(context)
        private var selectedItemPosition:Int = -1
        override fun getItemId(position: Int): Long {
            return countries.size.toLong()
        }

        @SuppressLint("ViewHolder", "SetTextI18n")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view: View? = convertView
            val country = getItem(position)

            view = inflater.inflate(normalLayout, parent, false)

            val countryCodeTv = view.findViewById<TextView>(R.id.countryCodeTv)
            val countryFlagSIV = view.findViewById<ShapeableImageView>(R.id.countryFlagSIV)

            countryCodeTv.text = "+${country?.countryCode?:""}"

            Glide.with(context)
                .load(country?.flag)
                .into(countryFlagSIV)

            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            return createView(position, convertView, parent)
        }

        @SuppressLint("SetTextI18n")
        private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view: View? = convertView
            val country = countries[position]

            view = inflater.inflate(dropdownLayout, parent, false)

            when (position) {
                0 -> view.setPadding(
                    context.resources.getDimension(R.dimen.dimen_15dp).toInt(),
                    context.resources.getDimension(R.dimen.dimen_12dp).toInt(),
                    context.resources.getDimension(R.dimen.dimen_15dp).toInt(),
                    context.resources.getDimension(R.dimen.dimen_8dp).toInt()
                )
                count - 1 -> view.setPadding(
                    context.resources.getDimension(R.dimen.dimen_15dp).toInt(),
                    context.resources.getDimension(R.dimen.dimen_8dp).toInt(),
                    context.resources.getDimension(R.dimen.dimen_15dp).toInt(),
                    context.resources.getDimension(R.dimen.dimen_12dp).toInt()
                )
                else -> view.setPadding(
                    context.resources.getDimension(R.dimen.dimen_15dp).toInt(),
                    context.resources.getDimension(R.dimen.dimen_8dp).toInt(),
                    context.resources.getDimension(R.dimen.dimen_15dp).toInt(),
                    context.resources.getDimension(R.dimen.dimen_8dp).toInt(),
                )
            }

            val countryCodeTv = view.findViewById<TextView>(R.id.countryCodeTv)
            val countryFlagSIV = view.findViewById<ShapeableImageView>(R.id.countryFlagSIV)

            countryCodeTv.text = "+${country.countryCode}"

            Glide.with(context).load(country.flag).into(countryFlagSIV)


            if(position == selectedItemPosition) {
                val selectedFontFamily = Typeface.createFromAsset(context.assets, "fonts/montserrat_bold.ttf");
                countryCodeTv.typeface = selectedFontFamily
                countryCodeTv.setTextColor(context.getColor(R.color.default_button_bg_color))
            } else {
                val unSelectedFontFamily = Typeface.createFromAsset(context.assets, "fonts/montserrat_semibold.ttf");
                countryCodeTv.typeface = unSelectedFontFamily
                countryCodeTv.setTextColor(context.getColor(R.color.score_test_taking_txt_color))
            }
            return view
        }

        fun setSelectedItemPosition(position: Int) {
            selectedItemPosition = position
            notifyDataSetChanged()
        }

    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    interface OnItemSelectedListener {
        fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long,
            dataList: ArrayList<CountryDetail>
        )
    }
}
val countryList = arrayListOf(
    CountryDetail(
        regionCode = "AE",
        label = "United Arab Emirates",
        countryCode = "971",
        phoneLength = arrayListOf(9),
        logo = "🇦🇪",
        flag = "https://cdn.jsdelivr.net/npm/country-flag-emoji-json@2.0.0/dist/images/AE.svg",
        unicode = "U+1F1E6 U+1F1EA"
    ),
    CountryDetail(
        regionCode = "IN",
        label = "India",
        countryCode = "91",
        phoneLength = arrayListOf(10),
        logo = "🇮🇳",
        flag = "https://cdn.jsdelivr.net/npm/country-flag-emoji-json@2.0.0/dist/images/IN.svg",
        unicode = "U+1F1EE U+1F1F3"
    )
)

val defaultCountryCode = arrayListOf(
    CountryDetail(
        regionCode = "IN",
        label = "India",
        countryCode = "91",
        phoneLength = arrayListOf(10),
        logo = "🇮🇳",
        flag = "https://cdn.jsdelivr.net/npm/country-flag-emoji-json@2.0.0/dist/images/IN.svg",
        unicode = "U+1F1EE U+1F1F3"
    )
)