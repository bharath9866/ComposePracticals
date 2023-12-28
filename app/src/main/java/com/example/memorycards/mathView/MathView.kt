package com.example.memorycards.mathView

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.webkit.WebView
import androidx.core.content.ContextCompat
import com.example.adaptivestreamingplayer.R


/**
 * Created by lingaraj on 3/15/17.
 */
class MathView : WebView {
    private val TAG = "KhanAcademyKatexView"
    private var displayText: String? = null
    private var textColor = 0
    private var textSize = 0
    private var clickable = false
    private var enableZoomInControls = false

    constructor(context: Context) : super(context) {
        configurationSettingWebView(enableZoomInControls)
        setDefaultTextColor(context)
        setDefaultTextSize()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        configurationSettingWebView(enableZoomInControls)
        val mTypeArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MathView,
            0, 0
        )
        try {
            setBackgroundColor(
                mTypeArray.getInteger(
                    R.styleable.MathView_setViewBackgroundColor,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )
            setTextColor(
                mTypeArray.getColor(
                    R.styleable.MathView_setTextColor,
                    ContextCompat.getColor(context, R.color.black)
                )
            )
            pixelSizeConversion(
                mTypeArray.getDimension(
                    R.styleable.MathView_setTextSize,
                    DEFAULT_TEXT_SIZE
                )
            )
            setDisplayText(mTypeArray.getString(R.styleable.MathView_setText))
            setClickable(mTypeArray.getBoolean(R.styleable.MathView_setClickable, false))
        } catch (e: Exception) {
            Log.d(TAG, "Exception:$e")
        }
    }

    fun setViewBackgroundColor(color: Int) {
        setBackgroundColor(color)
        this.invalidate()
    }

    private fun pixelSizeConversion(dimension: Float) {
        if (dimension == DEFAULT_TEXT_SIZE) {
            setTextSize(DEFAULT_TEXT_SIZE.toInt())
        } else {
            val pixelDimenEquivalentSize = (dimension.toDouble() / 1.6).toInt()
            setTextSize(pixelDimenEquivalentSize)
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "NewApi")
    private fun configurationSettingWebView(enableZoomInControls: Boolean) {
        setLayerType(LAYER_TYPE_HARDWARE, null)
        val settings = this.settings
        settings.javaScriptEnabled = true
        settings.allowFileAccess = true
        settings.displayZoomControls = enableZoomInControls
        settings.builtInZoomControls = enableZoomInControls
        settings.setSupportZoom(enableZoomInControls)
        this.isVerticalScrollBarEnabled = enableZoomInControls
        this.isHorizontalScrollBarEnabled = enableZoomInControls
        Log.d(TAG, "Zoom in controls:$enableZoomInControls")
    }

    fun setDisplayText(formulaText: String?) {
        displayText = formulaText
        loadData()
    }

    private val offlineKatexConfig: String
        get() {
            val offlineConfig = """
                <!DOCTYPE html>
                    <html>
                        <head>
                            <meta charset="UTF-8">
                            <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
                            <link rel="stylesheet" type="text/css" href="file:///android_asset/katex/katex.min.css">
                            <link rel="stylesheet" type="text/css" href="file:///android_asset/themes/style.css" >
                            <script type="text/javascript" src="file:///android_asset/katex/katex.min.js" ></script>
                            <script type="text/javascript" src="file:///android_asset/katex/contrib/auto-render.min.js" ></script>
                            <script type="text/javascript" src="file:///android_asset/katex/contrib/auto-render.js" ></script>
                            <script type="text/javascript" src="file:///android_asset/jquery.min.js" ></script>
                            <script type="text/javascript" src="file:///android_asset/latex_parser.js" ></script>
                            <link rel="stylesheet" href="file:///android_asset/webviewstyle.css"/>
                            <style type='text/css'>
                                body {
                                    margin: 0px;
                                    padding: 0px;
                                    font-size:${textSize}px;
                                    color:${getHexColor(textColor)};
                                 }
                             </style>
                        </head>
                            <body>
                                {formula}
                            </body>
                    </html>"""
            val start =
                "<html>" +
                        "<head>" +
                            "<meta http-equiv='Content-Type' content='text/html' charset='UTF-8' />" +
                            "<style> " +
                                "body {" +
                                    "white-space: nowrap;" +
                                "}" +
                            "</style>" +
                        "</head>" +
                        "<body>"
            val end = "</body></html>"
            return offlineConfig.replace("{formula}", displayText!!)
        }

    private fun setTextSize(size: Int) {
        textSize = size
        loadData()
    }

    private fun setTextColor(color: Int) {
        textColor = color
        loadData()
    }

    private fun getHexColor(intColor: Int): String {
        //Android and javascript color format differ javascript support Hex color, so the android color which user sets is converted to hexcolor to replicate the same in javascript.
        val hexColor = String.format("#%06X", 0xFFFFFF and intColor)
        Log.d(TAG, "Hex Color:$hexColor")
        return hexColor
    }

    private fun setDefaultTextColor(context: Context) {
        //sets default text color to black
        textColor = ContextCompat.getColor(context, R.color.black)
    }

    private fun setDefaultTextSize() {
        //sets view default text size to 18
        textSize = DEFAULT_TEXT_SIZE.toInt()
    }

    private fun loadData() {
        if (displayText != null) {
            loadDataWithBaseURL("null", offlineKatexConfig, "text/html", "UTF-8", "about:blank")
        }
    }

    override fun setClickable(isClickable: Boolean) {
        this.isEnabled = true
        clickable = isClickable
        enableZoomInControls = !isClickable
        configurationSettingWebView(enableZoomInControls)
        this.invalidate()
    }

    @SuppressLint("NewApi")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (clickable && event.action == MotionEvent.ACTION_DOWN) {
            callOnClick()
            false
        } else {
            super.onTouchEvent(event)
        }
    }

    companion object {
        private const val DEFAULT_TEXT_SIZE = 18f
    }
}

