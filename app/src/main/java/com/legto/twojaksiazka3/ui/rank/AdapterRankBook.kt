package project.legto.twojaksiazka3.ui.rank

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.legto.twojaksiazka3.R
import com.squareup.picasso.Picasso
import project.legto.twojaksiazka3.ui.home.Book
import project.legto.twojaksiazka3.ui.home.BookData
import project.legto.twojaksiazka3.utility.TextUtility

import java.lang.Exception

class AdapterRankBook(private val context: Activity)
    : ArrayAdapter<String>(context,
    R.layout.one_list_with_vategory_rank) {





    var markStart="0"
    var markEnd="10"
    var yearsStartPublish="1400"
    var yearsEndPublish="2020"
    var authorsName=""
    var authorsSurname=""

    var listElement=0
    val WIDTH_IMAGE=230
    val HEIGHT_IAMGE=350


    private var bookMap=mutableMapOf<Int, Book>()
    private var imageMap=mutableMapOf<Int, ImageView>()





    private fun saveDataBookToMap(book: Book, position: Int){
        bookMap[position] = book
    }

    @ExperimentalStdlibApi
    override fun getCount(): Int {
        return 500
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
        val rowView = inflater.inflate(R.layout.one_list_with_vategory_rank, null, true)


        val titleText = rowView.findViewById(R.id.OneCategoryBook_title) as TextView
        val authorBook = rowView.findViewById(R.id.OneCategoryBook_author) as TextView
        val imageBook: ImageView =rowView.findViewById(R.id.OneCategoryBook_image) as ImageView
        val markBookView = rowView!!.findViewById<TextView>(R.id.OneCategoryBook_markBook)
        val positionBookInRank=rowView!!.findViewById<TextView>(R.id.OneCategoryBook_position)


        positionBookInRank.text=""+(position+1)
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
                context.getString(R.string.GET_TOP_BOOK_ADRESS),
                listOf(
                    "position" to position
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