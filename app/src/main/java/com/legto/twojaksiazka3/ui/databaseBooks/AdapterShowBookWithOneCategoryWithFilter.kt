package project.legto.twojaksiazka3.ui.databaseBooks

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.legto.twojaksiazka3.R
import com.squareup.picasso.Picasso
import project.legto.twojaksiazka3.CategoryBooks
import project.legto.twojaksiazka3.ui.home.Book
import project.legto.twojaksiazka3.ui.home.BookData
import project.legto.twojaksiazka3.utility.TextUtility

import java.lang.Exception

class AdapterShowBookWithOneCategoryWithFilter(private val context: Activity)
    : ArrayAdapter<String>(context,
    R.layout.one_list_with_category) {



     fun setFilter(markStart:String,markEnd:String,yearsStartPublish:String,yearsEndPublish:String,authorsName:String,authorsSurname:String){
        this.markStart=markStart
         this.markEnd=markEnd
        this.yearsStartPublish=yearsStartPublish
         this.yearsEndPublish=yearsEndPublish
        this.authorsName=authorsName
         this.authorsSurname=authorsSurname
    }


    var markStart="0"
    var markEnd="10"
    var yearsStartPublish="1400"
    var yearsEndPublish="2020"
    var authorsName=""
    var authorsSurname=""

    var listElement=0
    val WIDTH_IMAGE=230
    val HEIGHT_IAMGE=350

    val INITIAL_LIST_SIZE=20

    private var bookMap=mutableMapOf<Int, Book>()
    private var imageMap=mutableMapOf<Int, ImageView>()


    @ExperimentalStdlibApi
    private fun ReadListSize(byteArray: ByteArray){
        var dataFromServerHowManyBookOnList = byteArray.decodeToString().substring(
            0,
            byteArray.decodeToString().length - 1
        )

        listElement = dataFromServerHowManyBookOnList.substringAfter("howMany\":").toInt()

    }


    private fun saveDataBookToMap(book: Book, position: Int){
        bookMap[position] = book
    }

    @ExperimentalStdlibApi
    override fun getCount(): Int {
        if(listElement==0) {
            try {

                Fuel.get(context.getString(R.string.HOW_MANY_BOOK_WITH_FILTER_SHOW_ADRESS) + CategoryBooks.CategoryChoice.nameBookCategory+"&markStart="+markStart+"&markEnd="+markEnd+"&yearsStartPublish="+yearsStartPublish+"&yearsEndPublish="+yearsEndPublish+"&authorsName="+authorsName+"&authorsSurname="+authorsSurname)
                    .response { _, _, result -> result.fold(
                        success = {

                            ReadListSize(it)


                        },
                        failure = { error ->

                        }
                    ) }



            } catch (exception: Exception) {
                println("A network request exception was thrown: ${exception.message}")
            }

            return super.getCount() + INITIAL_LIST_SIZE
        }else{
            return listElement
        }
    }

    private fun fillZeroMarkEnds(bookData: Book, markBookView: TextView){
        when (bookData.markBook.length) {
            1 -> {
                markBookView.text = bookData.markBook + ".00"

            }
            2 -> {
                markBookView.text = "10.00"

            }
            3 -> {


                markBookView.text = bookData.markBook + "0"
            }
            else -> {
                markBookView.text = bookData.markBook
            }
        }

    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.one_list_with_category, null, true)


        val titleText = rowView.findViewById(R.id.OneCategoryBook_title) as TextView
        val authorBook = rowView.findViewById(R.id.OneCategoryBook_author) as TextView
        val imageBook: ImageView =rowView.findViewById(R.id.OneCategoryBook_image) as ImageView
        val markBookView = rowView!!.findViewById<TextView>(R.id.OneCategoryBook_markBook)


        var bookData: Book

        if(bookMap.containsKey(position)){

            bookData= bookMap[position]!!

            imageBook.setImageDrawable(imageMap[position]!!.drawable)
            TextUtility.calculateNameBookSize(titleText, bookData.titleBook, 35f)
            titleText.text = bookData.titleBook
            authorBook.text = bookData.nameAuthor
            fillZeroMarkEnds(bookData,markBookView)

        } else {

            Fuel.get(
                context.getString(R.string.GET_BOOK_WITH_FILTER),
                listOf(
                    "position" to position, "category" to CategoryBooks.CategoryChoice.nameBookCategory,
                    "markStart" to markStart,"markEnd" to markEnd,"yearsStartPublish" to yearsStartPublish,
                    "yearsEndPublish" to yearsEndPublish,"authorsName" to authorsName,"authorsSurname" to authorsSurname
                )
            ).response { _, _, result ->
                result.fold(
                    success = {
                        val bookData =
                            BookData().deserialize(
                                it
                            )
                        saveDataBookToMap(bookData, position)
                        var imageToSaveOnTemp= ImageView(context)
                        Picasso.with(context)
                            .load(getContext().resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + bookData.idBook)
                            .placeholder(R.drawable.database_icon)
                            .resize(WIDTH_IMAGE, HEIGHT_IAMGE)
                            .centerCrop()
                            .into(imageToSaveOnTemp)
                        imageMap[position] = imageToSaveOnTemp




                        TextUtility.calculateNameBookSize(titleText, bookData.titleBook, 35f)
                        titleText.text = bookData.titleBook
                        authorBook.text = bookData.nameAuthor
                        fillZeroMarkEnds(bookData,markBookView)




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







    }



}