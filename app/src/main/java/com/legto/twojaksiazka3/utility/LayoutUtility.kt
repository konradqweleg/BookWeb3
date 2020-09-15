package project.legto.twojaksiazka3.utility

import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout

class LayoutUtility{
    fun increaseLayoutHeight(relativeLayout: RelativeLayout,height:Int){
        val heightLayout: Int = relativeLayout.height
        val modifyMarginLayout = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, heightLayout+height
        )
       relativeLayout.layoutParams = modifyMarginLayout

    }

    fun setDefaultLayoutHeight(relativeLayout: RelativeLayout){
        val modifyMarginLayout = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        relativeLayout.layoutParams = modifyMarginLayout

    }
}