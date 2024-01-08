package com.example.adaptivestreamingplayer.memoryCard.mathView

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.core.content.ContextCompat
import com.example.adaptivestreamingplayer.R


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
                    ContextCompat.getColor(context, R.color.transperent)
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
        settings.loadWithOverviewMode = true
        settings.allowFileAccess = true
        settings.displayZoomControls = false
        settings.builtInZoomControls = false
        settings.setSupportZoom(false)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        this.isVerticalScrollBarEnabled = false
        this.isHorizontalScrollBarEnabled = false
    }

    fun setDisplayText(formulaText: String?) {
        displayText = formulaText
        loadData()
    }

    private val offlineKatexConfig: String
        get() {
            val mathJaxConfig = """
                window.MathJax = {
                    tex: {
                      inlineMath: [['${'$'}', '${'$'}'], ['\\(', '\\)']],
                      displayMath: [['${'$'}${'$'}', '${'$'}${'$'}'], ['\\[', '\\]']],
                      processEscapes: true,
                      tags: 'ams'
                    },
                    options: {
                      ignoreHtmlClass: 'tex2jax_ignore',
                      processHtmlClass: 'tex2jax_process'
                    },
                    loader: {
                      load: ['[tex]/ams']
                    }
                };
                """.trimIndent()

            val cHtml = if (displayText?.contains("<math", true) == true)
                "<script id=\"MathJax-script\" src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js\"></script>"
            else
                ""

            val fontFamilyLoading = """
                @font-face {
                    font-family: 'Montserrat';
                    src: url('file:///android_res/font/montserrat_black.ttf') format('truetype');
                    font-weight: 900; /* Corrected to black */
                }
                @font-face {
                    font-family: 'Montserrat';
                    src: url('file:///android_res/font/montserrat_bold.ttf') format('truetype');
                    font-weight: 700; /* Corrected to bold */
                }
                @font-face {
                    font-family: 'Montserrat';
                    src: url('file:///android_res/font/montserrat_italic.ttf') format('truetype');
                    font-weight: italic;
                }
                @font-face {
                    font-family: 'Montserrat';
                    src: url('file:///android_res/font/montserrat_light.ttf') format('truetype');
                    font-weight: 300; /* Corrected to light */
                }
                @font-face {
                    font-family: 'Montserrat';
                    src: url('file:///android_res/font/montserrat_medium.ttf') format('truetype');
                    font-weight: 500; /* Corrected to medium */
                }
                @font-face {
                    font-family: 'Montserrat';
                    src: url('file:///android_res/font/montserrat_regular.ttf') format('truetype');
                    font-weight: 400; /* Corrected to normal */
                }
                @font-face {
                    font-family: 'Montserrat';
                    src: url('file:///android_res/font/montserrat_semi_bold.ttf') format('truetype');
                    font-weight: 600; /* Corrected to semibold */
                }
            """.trimIndent()
            val offlineConfig = """
                <!DOCTYPE html>
                    <html>
                        <head>
                            <meta charset="UTF-8">
                            <script type="text/x-mathjax-config">
                                $mathJaxConfig
                            </script>                          
                            $cHtml
                            <style type="text/css">
                                $fontFamilyLoading
                            </style>
                            <style type='text/css'>
                                body, div, p, span, math, mrow, mi, mo, mn, msup, msub, mfrac, mtext, .MJX-TEX {
                                    white-space: normal !important;
                                    text-align: center !important;
                                }
                                body, div, p, span {
                                    margin: 0px !important;
                                    padding: 0px !important;
                                }
                                                                
                                body, div, p, span, math, mrow, mi, mo, mn, msup, msub, mfrac, msqrt, menclose, mfenced, mtext, .MJX-TEX {
                                    font-size:${textSize}px !important;
                                    color:${getHexColor(textColor)} !important;
                                    font-family: 'Montserrat' !important;
                                    font-weight: 500 !important;
                                    color: #4E4B66 !important;
                                    background-color: #00000000 !important;
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
            return offlineConfig.replace("{formula}", displayText ?: "")
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

