package project.legto.twojaksiazka3.utility

import android.util.TypedValue
import android.widget.TextView

class TextUtility {
    companion object {
        fun calculateNameBookSize(view: TextView,str:String,size:Float){
            if(str.length>13){
               view.setTextSize(TypedValue.COMPLEX_UNIT_PX,size)
                view.text=str
            }else{
                view.text=str
            }
        }


        fun getDescriptionHowManyMark(howMark:String):String{
          return  "("+howMark+" ocen)"

        }
    }
}