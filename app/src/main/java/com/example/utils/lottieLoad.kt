package com.example.utils

import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.RenderMode

/**
 * Lottie Load for Views
 */
fun lottieLoad(lottieAnimationView: LottieAnimationView, anim: Int) {
    lottieAnimationView.setAnimation(anim)
    lottieAnimationView.renderMode = RenderMode.HARDWARE
    lottieAnimationView.playAnimation()
}
