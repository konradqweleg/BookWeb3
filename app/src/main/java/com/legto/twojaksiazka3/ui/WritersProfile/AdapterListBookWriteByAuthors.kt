package project.legto.twojaksiazka3.ui.WritersProfile

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.legto.twojaksiazka3.R
import com.squareup.picasso.Picasso



class AdapterListBookWriteByAuthors(private val context: Activity,val writersId:Int,val howManyBookWriteAuthors:Int)
    : ArrayAdapter<String>(context,
    R.layout.book_write_by_writers) {




    var mapBookWriteByWriters= kotlin.collections.mutableMapOf<Int, WritersWriteData>()
    var mapBookImage=kotlin.collections.mutableMapOf<Int, ImageView>()

    val WIDTH_IMAGE=230
    val HEIGHT_IAMGE=350






    @ExperimentalStdlibApi
    override fun getCount(): Int {
        return howManyBookWriteAuthors
    }



    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.book_write_by_writers, null, true)
        val bookName=rowView.findViewById<TextView>(R.id.BookWriteByAuthors_nameBook)
        val bookMark=rowView.findViewById<TextView>(R.id.BookWriteByAuthors_markBook)
        val imageBook=rowView.findViewById<ImageView>(R.id.BookWriteByAuthors_imageBook)

        if(mapBookWriteByWriters.containsKey(position)){
            val writersData=mapBookWriteByWriters[position]
            bookName.text=writersData!!.nameBook
            bookMark.text=""+writersData!!.markBook
            imageBook.setImageDrawable(mapBookImage[position]!!.drawable)
            WritersProfile.idBook=writersData.idBook

        }else {

            Fuel.get(
                context.getString(R.string.GET_WRITE_BOOK_BY_AUTHORS),
                listOf(
                    "position" to position,
                    "idWriters" to writersId
                )
            ).response { _, _, result ->
                result.fold(
                    success = {
                        val writersDataWrite =
                            WriteBookByAuthorsMapper().deserialize(
                                it
                            )


                        WritersProfile.idBook=writersDataWrite.idBook

                        bookName.text = writersDataWrite.nameBook
                        bookMark.text = "" + writersDataWrite.markBook
                        mapBookWriteByWriters[position]=writersDataWrite

                        Picasso.with(context)
                            .load(getContext().resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + writersDataWrite.idBook)
                            .placeholder(R.drawable.database_icon)
                            .resize(230, 350)
                            .centerCrop()
                            .into(imageBook)
                        mapBookImage[position]=imageBook

                    },
                    failure = { error ->

                    }
                )
            }


        }

        return rowView

    }

}