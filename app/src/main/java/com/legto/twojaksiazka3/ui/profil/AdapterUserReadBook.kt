package project.legto.twojaksiazka3.ui.profil

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.legto.twojaksiazka3.R
import com.squareup.picasso.Picasso
import project.legto.twojaksiazka3.utility.TextUtility
import project.legto.twojaksiazka3.utility.UserData

import java.lang.Exception


class AdapterUserReadBook(private val context: Activity)
    : ArrayAdapter<String>(context,
    R.layout.one_list_with_category) {


    var listElement=0
    val WIDTH_IMAGE=230
    val HEIGHT_IAMGE=350

    val INITIAL_LIST_SIZE=20

    private var bookReadMap=mutableMapOf<Int, userread>()
    private var imageMap=mutableMapOf<Int, ImageView>()

    private val markWhereInTextResponseUserReadBook="bookRead\":"

    @ExperimentalStdlibApi
    override fun getCount(): Int {


        if(listElement==0) {
            try {

                Fuel.get(context.getString(R.string.HOW_MANY_READ_BOOKS_USER_ADRESS)+UserData.idUser)
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



            } catch (exception: Exception) {
                println("A network request exception was thrown: ${exception.message}")
            }

            return super.getCount() + INITIAL_LIST_SIZE
        }else{
            return listElement
        }

    }



    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.one_list_with_category, null, true)
        val titleText = rowView.findViewById(R.id.OneCategoryBook_title) as TextView
        val authorBook = rowView.findViewById(R.id.OneCategoryBook_author) as TextView
        val imageBook: ImageView =rowView.findViewById(R.id.OneCategoryBook_image) as ImageView
        val markBookView = rowView!!.findViewById<TextView>(R.id.OneCategoryBook_markBook)



        var bookData: userread

        if(bookReadMap.containsKey(position)){
            bookData= bookReadMap[position]!!
            imageBook.setImageDrawable(imageMap[position]!!.drawable)
            titleText.text = bookData.nameBook
            authorBook.text = bookData.nameWriters+" "+bookData.surnameWriters
            markBookView.text=""+bookData.userMark


        } else {

            Fuel.get(
                context.getString(R.string.READ_BOOK_LIST_ABOUT_POSITON_ADRESS),
                listOf(
                    "position" to position, "idUser" to UserData.idUser
                )
            ).response { _, _, result ->
                result.fold(
                    success = {
                        val bookData =
                            ListBookReadMapper().deserialize(
                                it
                            )

                        var imageToSaveOnTemp= ImageView(context)
                        Picasso.with(context)
                            .load(getContext().resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + bookData.IdBook)
                            .placeholder(R.drawable.database_icon)
                            .resize(WIDTH_IMAGE, HEIGHT_IAMGE)
                            .centerCrop()
                            .into(imageToSaveOnTemp)
                        imageMap[position] = imageToSaveOnTemp




                        TextUtility.calculateNameBookSize(titleText, bookData.nameBook, 35f)
                        titleText.text = bookData.nameBook
                        authorBook.text = bookData.nameWriters+" "+bookData.surnameWriters
                        markBookView.text=""+bookData.userMark

                        bookReadMap[position]=bookData


                        Picasso.with(context)
                            .load(getContext().resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + bookData.IdBook)
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



