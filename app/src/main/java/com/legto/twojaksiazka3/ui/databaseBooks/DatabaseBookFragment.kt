package project.legto.twojaksiazka3.ui.databaseBooks

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.legto.twojaksiazka3.R
import project.legto.twojaksiazka3.CategoryBooks


class DatabaseBookFragment : Fragment() {

    private lateinit var databaseBookViewModel: DatabaseBookViewModel
    private lateinit var listCategory:ListView

    private fun initializeViewRefrence(){
         listCategory= view!!.findViewById<ListView>(R.id.DatabaseBookListCategory_categoryBookList)
    }


    private fun setAdapterCategoryList(){
        val adapterPopularBook = CategoryBookAdapter(
            activity as Activity
        )
        listCategory.adapter=adapterPopularBook
    }



    private fun navigateToListBookWithOneCategory(){
        Navigation.findNavController(view!!).navigate(R.id.ShowBookWithOneCategory)
    }

    private fun saveChoiceBookCategory(positionOnList:Int){
        when(positionOnList){
            0->{
                CategoryBooks.CategoryChoice= CategoryBooks.Companion.CategoryBook.HORROR
            }
            1->{
                CategoryBooks.CategoryChoice=CategoryBooks.Companion.CategoryBook.THRILLER
            }
            2->{
                CategoryBooks.CategoryChoice=CategoryBooks.Companion.CategoryBook.FANTASTYKA
            }

        }

    }

    private fun addCallbackToClickCategoryOnListCategory(){
        listCategory.setOnItemClickListener { parent, view, position, id ->
            saveChoiceBookCategory(position)
            navigateToListBookWithOneCategory()


        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewRefrence()
        setAdapterCategoryList()
        addCallbackToClickCategoryOnListCategory()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseBookViewModel =
            ViewModelProviders.of(this).get(DatabaseBookViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_database_book_list_category, container, false)
//        val textView: TextView = root.findViewById(R.id.text_notifications)
        databaseBookViewModel.text.observe(this, Observer {
           // textView.text = it
        })




        return root
    }
}