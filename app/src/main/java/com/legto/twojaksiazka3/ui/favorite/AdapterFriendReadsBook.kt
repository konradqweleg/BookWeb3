package project.legto.twojaksiazka3.ui.favorite

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
import project.legto.twojaksiazka3.utility.TextUtility


class AdapterFriendReadsBook(private val context: Activity)
    : ArrayAdapter<String>(context,
    R.layout.one_list_with_category) {

    companion object{
        var facebookId:String=""
    }


    var listElement=0
    val WIDTH_IMAGE=230
    val HEIGHT_IAMGE=350

    val INITIAL_LIST_SIZE=0

    private var bookReadMap=mutableMapOf<Int, friendRead>()
    private var imageMap=mutableMapOf<Int, ImageView>()

    private val markWhereInTextResponseUserReadBook="bookRead\":"

    @ExperimentalStdlibApi
    override fun getCount(): Int {






        runBlocking {
            val (request, response, result) = Fuel.get(
                context.getString(R.string.GET_SIZE_BOOK_READ_BY_FRIEND),
                listOf(
                    "idFb" to facebookId
                )
            ).awaitStringResponse()

            val sizeList =
                MapperSizeList().deserialize(
                    response.data
                )



            listElement = sizeList.size


        }

        //

      /*  if(listElement==0) {
            Fuel.get(
                context.getString(R.string.GET_SIZE_BOOK_READ_BY_FRIEND),
                listOf(
                    "idFb" to facebookId
                )
            ).response { _, _, result ->
                result.fold(
                    success = {
                        val sizeList =
                            MapperSizeList().deserialize(
                                it
                            )



                        listElement = sizeList.size


                    },
                    failure = { error ->

                    }
                )
            }
        }
/*
        if(listElement==0) {
            try {

                Fuel.get(context.getString(R.string.FRIEND_READ_BOOKS_ADRESS)+UserData.idUser)
                    .response { _, _, result -> result.fold(
                        success = {

                            var dataFromServerHowManyBookOnList =it.decodeToString().substring(
                                0,
                                it.decodeToString().length - 1
                            )

                            listElement = dataFromServerHowManyBookOnList.substringAfter(markWhereInTextResponseUserReadBook).toInt()


                        },
                        failure = { error ->

                        }
                    ) }

*/

            } catch (exception: Exception) {
                println("A network request exception was thrown: ${exception.message}")
            }*/
        Log.e("ilosc",listElement.toString())
            return super.getCount() + listElement
   //     }else{
      //      return listElement
     //   }

    }



    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.one_list_with_category, null, true)
        val titleText = rowView.findViewById(R.id.OneCategoryBook_title) as TextView
        val authorBook = rowView.findViewById(R.id.OneCategoryBook_author) as TextView
        val imageBook: ImageView =rowView.findViewById(R.id.OneCategoryBook_image) as ImageView
        val markBookView = rowView!!.findViewById<TextView>(R.id.OneCategoryBook_markBook)



        var bookData: friendRead

        if(bookReadMap.containsKey(position)){
            bookData= bookReadMap[position]!!
            imageBook.setImageDrawable(imageMap[position]!!.drawable)
            titleText.text = bookData.nameBook
            authorBook.text = bookData.writersName+" "+bookData.writersSurname
            markBookView.text=""+bookData.markBook


        } else {

            Fuel.get(
                context.getString(R.string.FRIEND_READ_BOOKS_ADRESS),
                listOf(
                    "position" to position, "idFb" to facebookId
                )
            ).response { _, _, result ->
                result.fold(
                    success = {
                        val bookData =
                            ReadBookFriendMapper().deserialize(
                                it
                            )

                        var imageToSaveOnTemp= ImageView(context)
                        Picasso.with(context)
                            .load(getContext().resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + bookData.idBook)
                            .placeholder(R.drawable.database_icon)
                            .resize(WIDTH_IMAGE, HEIGHT_IAMGE)
                            .centerCrop()
                            .into(imageToSaveOnTemp)
                        imageMap[position] = imageToSaveOnTemp




                        TextUtility.calculateNameBookSize(titleText, bookData.nameBook, 35f)
                        titleText.text = bookData.nameBook
                        authorBook.text = bookData.writersName+" "+bookData.writersSurname
                        markBookView.text=""+bookData.markBook

                        bookReadMap[position]=bookData


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
        }

        return rowView







    }}