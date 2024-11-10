package com.example.adaptivestreamingplayer.customComponent

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.customComponent.models.CountryDetail
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import java.lang.reflect.Field

class CustomSpinner : FrameLayout  {

    private var selectedCountry: CountryDetail = CountryDetail()
    var selectedCountryDetail:CountryDetail? = null
    private var dropdownLayout:Int = 0
    private var normalLayout:Int = 0
    private var layoutHeightSpinner:Int = 0
    private var layoutWidthSpinner:Int = 0
    //private var popUpBackground:Int = 0
    private var cardCornerRadius:Float = 0f
    private var spinnerBackground:Int = 0
    private var spinnerStrokeColor:Int = 0
    private var spinnerStrokeWidth:Int = 0
    private var spinnerElevation:Float = 0f
    private var spinnerMaxElevation:Float = 0f
    private var layoutHeightPopUp:Int = 0
    private var layoutWidthPopUp:Int = 0
    private var popUpShape:Int = 0
    private var popUpCornerRadius:Float = 0f
    private var popUpBackgroundColor:ColorStateList = ColorStateList.valueOf(Color.Transparent.toArgb())
    private var popUpStrokeColor:Int = 0
    private var popUpStrokeWidth:Int = 0
    private var popUpPaddingLeft:Int = 0
    private var popUpPaddingTop:Int = 0
    private var popUpPaddingRight:Int = 0
    private var popUpPaddingBottom:Int = 0
    var onItemSelectedListener: OnItemSelectedListener? = null
    var spinner: AppCompatSpinner? = null
    var adapter: SpinnerAdapter? = null

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
            layoutHeightSpinner = typedArray.getLayoutDimension(R.styleable.CustomSpinner_spinner_layout_height, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutWidthSpinner = typedArray.getLayoutDimension(R.styleable.CustomSpinner_spinner_layout_width, ViewGroup.LayoutParams.WRAP_CONTENT)
            cardCornerRadius = displayMetrics(typedArray.getDimension(R.styleable.CustomSpinner_cardCornerRadius, context.resources.getDimension(R.dimen.dimen_10dp)))
            spinnerBackground = typedArray.getColor(R.styleable.CustomSpinner_spinnerBackground, ContextCompat.getColor(context, R.color.white))
            spinnerStrokeColor = typedArray.getColor(R.styleable.CustomSpinner_spinnerStrokeColor, ContextCompat.getColor(context, R.color.transperent))
            spinnerStrokeWidth = displayMetrics(typedArray.getDimension(R.styleable.CustomSpinner_spinnerStrokeWidth, 0f)).toInt()
            spinnerElevation = displayMetrics(typedArray.getDimension(R.styleable.CustomSpinner_spinnerElevation, 0f))
            spinnerMaxElevation = displayMetrics(typedArray.getDimension(R.styleable.CustomSpinner_spinnerMaxElevation, 0f))
            layoutHeightPopUp = typedArray.getLayoutDimension(R.styleable.CustomSpinner_popup_layout_height, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutWidthPopUp = typedArray.getLayoutDimension(R.styleable.CustomSpinner_popup_layout_width, ViewGroup.LayoutParams.WRAP_CONTENT)


            popUpShape = typedArray.getInt(R.styleable.CustomSpinner_popUpShape, GradientDrawable.RECTANGLE)
            popUpCornerRadius = displayMetrics(typedArray.getDimension(R.styleable.CustomSpinner_popUpCornerRadius, context.resources.getDimension(R.dimen.dimen_10dp)))
            popUpBackgroundColor = typedArray.getColorStateList(R.styleable.CustomSpinner_popUpBackgroundColor)?:ColorStateList.valueOf(Color.White.toArgb())
            popUpStrokeColor = typedArray.getColor(R.styleable.CustomSpinner_popUpStokeColor, ContextCompat.getColor(context, R.color.transperent))
            popUpStrokeWidth = displayMetrics(typedArray.getDimension(R.styleable.CustomSpinner_popUpStokeWidth, 0f)).toInt()

            popUpPaddingTop = displayMetrics(typedArray.getDimension(R.styleable.CustomSpinner_popUpPaddingTop, 0f)).toInt()
            popUpPaddingLeft = displayMetrics(typedArray.getDimension(R.styleable.CustomSpinner_popUpPaddingLeft, 0f)).toInt()
            popUpPaddingRight = displayMetrics(typedArray.getDimension(R.styleable.CustomSpinner_popUpPaddingRight, 0f)).toInt()
            popUpPaddingBottom = displayMetrics(typedArray.getDimension(R.styleable.CustomSpinner_popUpPaddingBottom, 0f)).toInt()
            typedArray.recycle()
        }
    }

    private fun displayMetrics(typedArray: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        typedArray,
        context.resources.displayMetrics
    )

    @SuppressLint("LogNotTimber")
    private fun initView(context: Context) {
        val rootView = (context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.custom_spinner, this, true)
        setUpOnBoardingSpinner(rootView, context)
    }

    private fun setUpOnBoardingSpinner(rootView: View, context: Context) {
        val mcvSpinnerId = rootView.findViewById<MaterialCardView>(R.id.mcvSpinnerId)
        //mcvSpinnerId.setRadius(cardCornerRadius)
        mcvSpinnerId.radius = cardCornerRadius
        mcvSpinnerId.setCardBackgroundColor(spinnerBackground)
        mcvSpinnerId.strokeColor = spinnerStrokeColor
        mcvSpinnerId.strokeWidth = spinnerStrokeWidth
        mcvSpinnerId.cardElevation = spinnerElevation
        mcvSpinnerId.maxCardElevation = spinnerMaxElevation
        mcvSpinnerId.layoutParams = LayoutParams(layoutWidthSpinner, layoutHeightSpinner)

        spinner = rootView.findViewById<AppCompatSpinner>(R.id.spinner)

        adapter = SpinnerAdapter(
            context = context,
            dropdownLayout = dropdownLayout,
            normalLayout = normalLayout,
            countries = countryList
        )

        spinner?.adapter = adapter

        spinner?.setPopupBackgroundDrawable(
            GradientDrawable().apply {
                shape = popUpShape
                cornerRadius = popUpCornerRadius
                color = popUpBackgroundColor
                setStroke(popUpStrokeWidth, popUpStrokeColor)
            }
        )
        spinner?.setPadding(popUpPaddingLeft, popUpPaddingTop, popUpPaddingRight, popUpPaddingBottom)

        if (spinner != null) {
            spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    if (view != null) {
                        adapter?.setSelectedItemPosition(position)
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
                countryCodeTv.apply {
                    typeface = Typeface.createFromAsset(context.assets, "fonts/montserrat_bold.ttf")
                    setTextColor(context.getColor(R.color.default_button_bg_color))
                }
            } else {
                countryCodeTv.apply {
                    typeface = Typeface.createFromAsset(context.assets, "fonts/montserrat_semibold.ttf");
                    setTextColor(context.getColor(R.color.score_test_taking_txt_color))
                }
            }
            return view
        }

        fun setSelectedItemPosition(position: Int) {
            selectedItemPosition = position
            notifyDataSetChanged()
        }

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
        countryCode = "971-9866062197",
        phoneLength = arrayListOf(9),
        logo = "🇦🇪",
        flag = "https://cdn.jsdelivr.net/npm/country-flag-emoji-json@2.0.0/dist/images/AE.svg",
        unicode = "U+1F1E6 U+1F1EA"
    ),
    CountryDetail(
        regionCode = "IN",
        label = "India",
        countryCode = "91-9866062197",
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