package project.legto.twojaksiazka3.utility

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

class AnimUtility{


        private val Int.px: Int
            get() {
                return (this * Resources.getSystem().displayMetrics.density).toInt()
            }


        fun slideTopCollectionView(views: List<View>, dp: Int, durationAnim: Long) {
            for (view in views) {
                ObjectAnimator.ofFloat(view, "translationY", dp.px.toFloat()).apply {
                    duration = durationAnim
                    start()
                }
            }

        }


        fun slideDownCollectionView(views: List<View>, dp: Int, durationAnim: Long) {
            for (view in views) {
                ObjectAnimator.ofFloat(view, "translationY", dp.px.toFloat()).apply {
                    duration = durationAnim
                    start()
                }
            }

        }

        fun animInputError(
            v: View,
            animationDuration: Long,
            functionSetPadding: () -> Unit,
            functionRunAfterAnimation: (ifShow: Boolean) -> Unit,
            animationDelay: Long = 0

        ) {
            val anim: Animation = ScaleAnimation(
                0f, 1f,
                1f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            anim.fillAfter = false
            anim.duration = animationDuration
            anim.startOffset = animationDelay


            functionSetPadding()

            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(arg0: Animation?) {}
                override fun onAnimationRepeat(arg0: Animation?) {}
                override fun onAnimationEnd(arg0: Animation?) {

                    functionRunAfterAnimation(false)


                }
            })
            v.startAnimation(anim)
        }
    }
