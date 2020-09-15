package project.legto.twojaksiazka3.ui.home

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.kittinunf.fuel.Fuel
import com.legto.twojaksiazka3.R
import com.squareup.picasso.Picasso



class ListAdapter(private val context: Activity)
    : ArrayAdapter<String>(context,
    R.layout.homebook) {
    private var bookMap=mutableMapOf<Int,Book>()
    private var imageBookMap= mutableMapOf<Int,ImageView>()


    private val EMPTY_CATEGORY=" "

    override fun getCount(): Int {
        return super.getCount()+4
    }



    private fun setCategoryBookInView(oneBook:Book,gentreBook_1:TextView,gentreBook_2:TextView,gentreBook_3:TextView){
        if (oneBook.gentre_1 != EMPTY_CATEGORY) {
            gentreBook_1.text = oneBook.gentre_1
        } else {
            gentreBook_1.visibility = View.GONE
        }
        if (oneBook.gentre_2 != EMPTY_CATEGORY) {
            gentreBook_2.text = oneBook.gentre_2
        } else {
            gentreBook_2.visibility = View.GONE
        }
        if (oneBook.gentre_3 != EMPTY_CATEGORY) {
            gentreBook_3.text = oneBook.gentre_3
        } else {
            gentreBook_3.visibility = View.GONE
        }
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.homebook, null, true)


        val titleText = rowView.findViewById(R.id.List_nameBook) as TextView
        val authorBook = rowView.findViewById(R.id.List_authorsBook) as TextView
        val markBook:TextView= rowView.findViewById(R.id.List_markBook) as TextView
    //    val gentreBook_1:TextView= rowView.findViewById(R.id.List_gentreBook_1) as TextView
      //  val gentreBook_2:TextView= rowView.findViewById(R.id.List_gentreBook_2) as TextView
       // val gentreBook_3:TextView= rowView.findViewById(R.id.List_gentreBook_3) as TextView
        val imageBook:ImageView=rowView.findViewById(R.id.List_image) as ImageView



    if(bookMap.containsKey(position)){
         val oneBook:Book?=bookMap[position]
      //   TextUtility.calculateNameBookSize(titleText, oneBook!!.titleBook, 35f)
         titleText.text = oneBook!!.titleBook
         authorBook.text = oneBook!!.nameAuthor
         markBook.text = oneBook!!.markBook
         imageBook.setImageDrawable(imageBookMap[position]!!.drawable)
       //  setCategoryBookInView(oneBook,gentreBook_1,gentreBook_2,gentreBook_3)


    }else {

         Fuel.get(
            getContext().resources.getString(R.string.GIVE_POPULAR_BOOK_ADRESS),
            listOf(
                "numberBook" to position
                 )
         ).response { _, _, result ->
            result.fold(
             success = {
                    val oneBookData =
                        BookData().deserialize(
                            it
                        )



                 //  TextUtility.calculateNameBookSize(titleText, oneBookData.titleBook, 35f)

                   titleText.text = oneBookData.titleBook
                   authorBook.text = oneBookData.nameAuthor
                   markBook.text = oneBookData.markBook
                //   setCategoryBookInView(oneBookData,gentreBook_1,gentreBook_2,gentreBook_3)

                   Picasso.with(context)
                    .load(getContext().resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + oneBookData.idBook)
                    .placeholder(R.drawable.database_icon)
                    .resize(295, 430)
                    .centerCrop()
                    .into(imageBook)


                var imageBookOne = ImageView(context)

                Picasso.with(context)
                    .load(getContext().resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + oneBookData.idBook)
                    .placeholder(R.drawable.database_icon)
                    .resize(295, 430)
                    .centerCrop()
                    .into(imageBookOne)
                imageBookMap[position] = imageBookOne


                bookMap[position] = oneBookData
            },
            failure = { error ->

            }
        )
    }

}



            return rowView




    }



}