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
import com.legto.twojaksiazka3.R
import project.legto.twojaksiazka3.FromWhereBookShow


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Rank_book.newInstance] factory method to
 * create an instance of this fragment.
 */
class Rank_book : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var listTopBook:ListView
    private lateinit var xReturn:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    private fun addAdapterToListToBook(){
        val adapterRankBook = AdapterRankBook(
            activity as Activity
        )

        listTopBook.adapter=adapterRankBook
    }


    private fun addClickRankBookList(){
        listTopBook.setOnItemClickListener { parent, view, position, id ->


            FromWhereBookShow.setFromWherIMustShowBook(FromWhereBookShow.From.RANK_BOOK,position)
            Navigation.findNavController(view!!).navigate(R.id.bookFragment)

        }
    }


    private fun addCallbackToReturn(){
        xReturn.setOnClickListener {
            Navigation.findNavController(view!!).navigate(R.id.navigation_notifications)
        }

    }
    private fun initializeReferenceView(){
        listTopBook=view!!.findViewById(R.id.RankBook_listBooksTop)
        xReturn=view!!.findViewById(R.id.RankBook_exit)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeReferenceView()
        addAdapterToListToBook()
        addClickRankBookList()
        addCallbackToReturn()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank_book, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Rank_book.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Rank_book().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
