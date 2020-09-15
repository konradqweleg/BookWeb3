package project.legto.twojaksiazka3.utility

import android.graphics.Paint
import android.widget.TextView

class ModifyTextViewUtility {
    fun underlineTextView(textView: TextView) {
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
}




