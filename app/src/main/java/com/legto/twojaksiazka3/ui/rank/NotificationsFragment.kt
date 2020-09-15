package project.legto.twojaksiazka3.ui.rank

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.legto.twojaksiazka3.R


class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var listRankOptionView:ListView

    private fun initializeReferenceView(){
        listRankOptionView=view!!.findViewById(R.id.Rank_list)

    }


    private fun addAdapterToListChoiceRank(){
        val adapterBookWithOneCategory = AdapterListRankChoicer(
            activity as Activity
        )

        listRankOptionView.adapter=adapterBookWithOneCategory

    }


    private fun addClickToListRank(){
        listRankOptionView.setOnItemClickListener { parent, view, position, id ->

        when(position){
            0->{
                   // FromWhereBookShow.setFromWherIMustShowBook(FromWhereBookShow.From.LIST_CATEGORY_BOOK,position)
                    Navigation.findNavController(view!!).navigate(R.id.rankBook)
            }
            1->{
                Navigation.findNavController(view!!).navigate(R.id.rankAuthors)
            }
        }



        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeReferenceView()
        addAdapterToListChoiceRank()
        addClickToListRank()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        return root
    }
}