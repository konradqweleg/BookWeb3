package project.legto.twojaksiazka3.ui.rank

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.legto.twojaksiazka3.R


class AdapterListRankChoicer(private val context: Activity)
    : ArrayAdapter<String>(context,
    R.layout.rank_list) {


    val NAME_OPTION= listOf<String>("Książek","Pisarzy","Użytkowników")
    val IMAGE_OPTION= listOf<Int>(R.drawable.ic_study,R.drawable.ic_writer,R.drawable.ic_user_rank)



    override fun getCount(): Int {
        return super.getCount()+3
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.rank_list, null, true)

        val nameOptionList=rowView.findViewById<TextView>(R.id.Rank_mainTextList)
       nameOptionList.text = NAME_OPTION[position]

        val optionImage=rowView.findViewById<ImageView>(R.id.Rank_imageList)
        optionImage.setImageDrawable(context.resources.getDrawable(IMAGE_OPTION[position]))





        return rowView
    }

}