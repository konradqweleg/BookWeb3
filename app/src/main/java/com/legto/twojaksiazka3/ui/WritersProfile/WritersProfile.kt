package project.legto.twojaksiazka3.ui.WritersProfile

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.legto.twojaksiazka3.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking
import project.legto.twojaksiazka3.FromWhereBookShow
import project.legto.twojaksiazka3.utility.BaseFrragment
import project.legto.twojaksiazka3.utility.WritersDataGlobal


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WritersProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class WritersProfile : Fragment(), BaseFrragment {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var listWriteBookByWritersView:org.lucasr.twowayview.TwoWayView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private val WIDTH_USER_IMAGE=230
    private val HEIGHT_USER_IMAGE=350
    private lateinit var imageWriters:ImageView
    private lateinit var nameWriters:TextView
    private lateinit var howManyBooksWriteAuthors:TextView
    private lateinit var markAuthors:TextView
    private lateinit var infoAboutAuthors:TextView
    private lateinit var writersData:WritersData
    private lateinit var ifTopWriters:TextView
    private lateinit var ifTopBook:TextView
    private lateinit var returnBack:ImageView


    private fun downloadWritersData(){
        runBlocking {
            val (request, response, result) = Fuel.get(
                context!!.getString(R.string.GET_WRITERS_DATA),
                listOf(
                    "idWriters" to WritersDataGlobal.idWriters
                )
            ).awaitStringResponse()

            writersData = WritersMapper().deserialize(response.data)


        }

    }

    override fun onBackPressed(): Boolean {

        fragmentManager!!.popBackStackImmediate()
        Log.e("gha","Wywołuje cofam")
        return true
    }


    private fun addAdapterToListWriteBook(){
        val adapterRankBook = AdapterListBookWriteByAuthors(
            activity as Activity,writersData.writersId,writersData.howManyBookWriteAuthors
        )

        listWriteBookByWritersView.adapter=adapterRankBook
    }

    private fun addCallbackReturn(){
        returnBack.setOnClickListener {
            fragmentManager!!.popBackStackImmediate()
        }
    }

    private fun initialiseReferenceView(){
      listWriteBookByWritersView=view!!.findViewById(R.id.WritersProfile_bookListWriteByAutors)
        imageWriters=view!!.findViewById<ImageView>(R.id.Book_photoBook)
        nameWriters=view!!.findViewById(R.id.Book_bookTitle)
        howManyBooksWriteAuthors=view!!.findViewById(R.id.Book_yearPublish)
        markAuthors=view!!.findViewById(R.id.ProfileWriters_writersMark)
        infoAboutAuthors=view!!.findViewById(R.id.ProfileWriters_infoAboutAuthors)
        ifTopBook=view!!.findViewById(R.id.ProfileWriters_ifTopBook)
        ifTopWriters=view!!.findViewById(R.id.ProfileWriters_ifTopWriters)
       returnBack=view!!.findViewById(R.id.Book_returnToMenuArrow)

    }

    private fun downloadWritersPhoto(){

            Picasso.with(context)
                .load(context!!.getString(R.string.GET_WRITERS_IMAGE) + WritersDataGlobal.idWriters)
                .placeholder(R.drawable.database_icon)
                .resize(WIDTH_USER_IMAGE, HEIGHT_USER_IMAGE)
                .centerCrop()
                .into(imageWriters)

    }


    private fun addClickToBookOnListWriteByWriters(){
        listWriteBookByWritersView.setOnItemClickListener { parent, view, position, id ->

            runBlocking {
                val (request, response, result) =   Fuel.get(
                    context!!.getString(R.string.GET_WRITE_BOOK_BY_AUTHORS),
                    listOf(
                        "position" to position,
                        "idWriters" to writersData.writersId
                    )
                ).awaitStringResponse()
                Log.e("aax",""+position+" "+writersData.writersId)


                val writersDataWrite =
                    WriteBookByAuthorsMapper().deserialize(
                        response.data
                    )
                Log.e("aax",writersDataWrite.nameBook)
                FromWhereBookShow.idBook=writersDataWrite.idBook
                FromWhereBookShow.setFromWherIMustShowBook(FromWhereBookShow.From.ID_BOOK,0)
                Navigation.findNavController(view!!).navigate(R.id.bookFragment)

            }








        }
    }
    private fun fillViewDownlaodData(){
        nameWriters.setText(writersData.nameAuthors+" "+writersData.surnameAuthors)
        howManyBooksWriteAuthors.setText("Książki ("+writersData.howManyBookWriteAuthors+")")
        markAuthors.setText(""+writersData.markAuthors)


        if(!writersData.ifTopAuthors){
            ifTopWriters.setVisibility(View.INVISIBLE)
        }

        if(!writersData.ifTopBookAuthors){
            ifTopBook.setVisibility(View.INVISIBLE)
        }






    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseReferenceView()

        downloadWritersData()
        downloadWritersPhoto()
       addAdapterToListWriteBook()
        fillViewDownlaodData()
       addCallbackReturn()
      addClickToBookOnListWriteByWriters()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.authors_new, container, false)
    }

    companion object {

        var idBook=1
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WritersProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WritersProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
