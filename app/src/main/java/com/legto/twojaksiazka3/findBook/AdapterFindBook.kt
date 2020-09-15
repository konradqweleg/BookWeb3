package project.legto.twojaksiazka3.findBook

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.legto.twojaksiazka3.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking


import java.lang.Exception

class AdapterFindBook(private val context: Activity)
    : ArrayAdapter<String>(context,
    R.layout.one_find_book) {



    companion object{
        val INPOSIBLE_NAME_BOOK="tg678asd2"
        var FilterBook= INPOSIBLE_NAME_BOOK
        var bookSize=0
        var writersSize=0


    }

    private var bookMap=mutableMapOf<Int, FindBook>()
    private var writersMap= mutableMapOf<Int,WritersDataFind>()
    private var imageBookMap= mutableMapOf<Int,ImageView>()


        var bookData = 0
        var oldFilter = INPOSIBLE_NAME_BOOK







    fun setNewFilter(str:String){
       FilterBook=str
    }





    override fun getCount(): Int {


       if(oldFilter!=FilterBook) {
           runBlocking {
               val (request, response, result) = Fuel.get(
                   context.getString(R.string.FIND_BOOK_HOW_MANY),
                   listOf(
                       "positionBook" to 0,
                       "filterBook" to FilterBook
                   )
               ).awaitStringResponse()


               val (_,writersResponse,_)= Fuel.get(
                   context.getString(R.string.GET_WRITERS_WITH_FILTER_NAME_SIZE),
                   listOf(
                       "filter" to FilterBook

                   )
               ).awaitStringResponse()



               bookData =
                   ResponseHowMany().deserialize(
                       response.data
                   ).howMany

               bookSize=bookData

               bookData+=WritersFindSizeMapper().deserialize(writersResponse.data).size
               writersSize=WritersFindSizeMapper().deserialize(writersResponse.data).size




           }

           oldFilter=FilterBook

       }
        return bookData

    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.one_find_book, null, true)




        val titleText = rowView.findViewById(R.id.OneCategoryBook_title) as TextView
        val authorBook = rowView.findViewById(R.id.OneCategoryBook_author) as TextView
        val imageBook: ImageView =rowView.findViewById(R.id.OneCategoryBook_image) as ImageView




    if(bookMap.containsKey(position)){

        titleText.text = bookMap[position]!!.nameBook
        authorBook.text = bookMap[position]!!.author + " " + bookMap[position]!!.sur
        imageBook.setImageDrawable(imageBookMap[position]!!.drawable)

    }else if(writersMap.containsKey(position)){
        titleText.text = writersMap[position]!!.name+" "+writersMap[position]!!.surname
        authorBook.text=""
        imageBook.setImageDrawable(imageBookMap[position]!!.drawable)
    }
    else {


if(position<bookSize) {
    Fuel.get(
        getContext().resources.getString(R.string.FIND_BOOK_LIST_ADRESS),
        listOf(
            "positionBook" to position,
            "filterBook" to AdapterFindBook.FilterBook
        )
    ).response { _, _, result ->
        result.fold(
            success = {
                val bookData =
                    ResponseMapListFindBook().deserialize(
                        it
                    )

                bookMap[position] = bookData



                titleText.text = bookData.nameBook
                authorBook.text = bookData.author + " " + bookData.sur
                var img = ImageView(context)

                Picasso.with(context)
                    .load(getContext().resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + bookData.idBook)
                    .placeholder(R.drawable.database_icon)
                    .resize(230, 350)
                    .centerCrop()
                    .into(img)

                imageBookMap[position] = img
                Picasso.with(context)
                    .load(getContext().resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + bookData.idBook)
                    .placeholder(R.drawable.database_icon)
                    .resize(230, 350)
                    .centerCrop()
                    .into(imageBook)


            },
            failure = { error ->


            }
        )
    }
}else{
    Fuel.get(
        getContext().resources.getString(R.string.GET_WRITERS_WITH_FILTER_NAME_ADRESS),
        listOf(
            "position" to position-bookSize,
            "filter" to AdapterFindBook.FilterBook
        )
    ).response { _, _, result ->
        result.fold(
            success = {
                val writersData =
                    WritersMapperFind().deserialize(
                        it
                    )

                writersMap[position] = writersData



                titleText.text = writersData.name+" "+writersData.surname
                authorBook.text = ""
                var img = ImageView(context)

                Picasso.with(context)
                    .load(getContext().resources.getString(R.string.GET_WRITERS_IMAGE) + writersData.idWriters)
                    .placeholder(R.drawable.database_icon)
                    .resize(230, 350)
                    .centerCrop()
                    .into(img)

                imageBookMap[position] = img
                Picasso.with(context)
                    .load(getContext().resources.getString(R.string.GET_WRITERS_IMAGE) + writersData.idWriters)
                    .placeholder(R.drawable.database_icon)
                    .resize(230, 350)
                    .centerCrop()
                    .into(imageBook)


            },
            failure = { error ->


            }
        )
    }

}

}
















            return rowView
     //   }
    }



}