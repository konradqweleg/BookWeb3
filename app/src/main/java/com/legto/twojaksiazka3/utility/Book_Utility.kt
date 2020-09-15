package project.legto.twojaksiazka3.utility

import android.app.Activity
import android.graphics.Color
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class Book_Utility {
    companion object{
        fun changeFragmentTo(manager:FragmentManager,fragmentFrame:Int,newFragment: Fragment){
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.replace(fragmentFrame, newFragment)
            transaction.commit()

        }


    }
}