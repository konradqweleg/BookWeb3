package project.legto.twojaksiazka3.ui.rank

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import androidx.navigation.Navigation
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.legto.twojaksiazka3.R
import kotlinx.coroutines.runBlocking
import project.legto.twojaksiazka3.utility.WritersDataGlobal


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RankAuthors.newInstance] factory method to
 * create an instance of this fragment.
 */
class RankAuthors : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var listTopWriters: ListView
    private lateinit var xReturn: ImageView


    private fun addAdapterToListToBook(){
        val adapterRankBook = AdapterTopWriters(
            activity as Activity
        )

        listTopWriters.adapter=adapterRankBook
    }





    private fun addClickRankBookList(){
        listTopWriters.setOnItemClickListener { parent, view, position, id ->

            Navigation.findNavController(view!!).navigate(R.id.writersProfile)





            runBlocking {
                val (request, response, result) = Fuel.get(
                    context!!.getString(R.string.GET_DATA_RANK_WRITERS_ADRESS),
                    listOf(
                        "position" to position
                    )
                ).awaitStringResponse()

              var  writersDataClicked = RankAuthorsMapper().deserialize(response.data)

                WritersDataGlobal.idWriters= writersDataClicked.idWriters

            }


        }
    }


    private fun addCallbackToReturn(){
        xReturn.setOnClickListener {
            Navigation.findNavController(view!!).navigate(R.id.navigation_notifications)
        }

    }
    private fun initializeReferenceView(){
        listTopWriters=view!!.findViewById(R.id.RankAuthors_listAuthorsTop)
        xReturn=view!!.findViewById(R.id.RankAuthors_exit)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeReferenceView()
        addAdapterToListToBook()
        addClickRankBookList()
        addCallbackToReturn()
    }












    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank_authors, container, false)
    }

    companion object {
        var idWritersShow=0
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RankAuthors.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RankAuthors().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
