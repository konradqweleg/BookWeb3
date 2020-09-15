package project.legto.twojaksiazka3.ui.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.legto.twojaksiazka3.R
import project.legto.twojaksiazka3.FromWhereBookShow


class HomeFragment : Fragment() {




    private lateinit var horizontalPopularBookList:org.lucasr.twowayview.TwoWayView
    private lateinit var hotizontalListFriendActivity:org.lucasr.twowayview.TwoWayView
    private lateinit var newBookList:org.lucasr.twowayview.TwoWayView
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var searchBook:EditText



    private fun initializeViewRefrence(){
        horizontalPopularBookList=view!!.findViewById(R.id.Home_ListPopularBook)
        newBookList=view!!.findViewById(R.id.Home_newBookList)








        searchBook=view!!.findViewById(R.id.Home_searchBook)
        hotizontalListFriendActivity=view!!.findViewById(R.id.Home_ListFriendActivity)

    }



    fun addCallbackToClickSearchButton(){
        searchBook.setOnClickListener {
            findNavController(view!!).navigate(R.id.FindPanelBookFragment)
        }

    }

    fun addAdapterNewBookList()
    {
        val adapterPopularBook = ListAdapter(
            activity as Activity
        )


        newBookList.adapter=adapterPopularBook
    }
    fun addAdapterToListFriendActivity()
    {
        val adapterActivity = FriendLastActivityAdapter(
            activity as Activity
        )
        hotizontalListFriendActivity.adapter=adapterActivity
    }
    fun addAdapterToListHorizontalPopualrBook(){

        val adapterPopularBook = ListAdapter(
            activity as Activity
        )
        horizontalPopularBookList.adapter=adapterPopularBook


    }

    fun addOnCallbackClikPopularBookList(){
        horizontalPopularBookList.setOnItemClickListener { parent, view, position, id ->
            FromWhereBookShow.setFromWherIMustShowBook(FromWhereBookShow.From.FAVORITE_BOOK,position)
            findNavController(view).navigate(R.id.bookFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewRefrence()
        addAdapterToListHorizontalPopualrBook()
        addOnCallbackClikPopularBookList()
        addCallbackToClickSearchButton()
        addAdapterToListFriendActivity()
        addAdapterNewBookList()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)



        return root
    }
}