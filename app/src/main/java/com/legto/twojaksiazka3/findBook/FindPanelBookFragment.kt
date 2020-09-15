package project.legto.twojaksiazka3.findBook

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.github.kittinunf.fuel.Fuel
import com.hold1.keyboardheightprovider.KeyboardHeightProvider
import com.legto.twojaksiazka3.R
import project.legto.twojaksiazka3.FromWhereBookShow
import project.legto.twojaksiazka3.utility.WritersDataGlobal


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FindPanelBookFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FindPanelBookFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FindPanelBookFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit   var listSearchedBook:ListView
    private lateinit   var searchEditText:EditText

   val IMPOSIBLE_BOOK_NAME="hasghaidsufa9yf"
    val MIN_SEARCH_BOOK_NAME_LENGTH=1



    val PROBABLY_KEYBOARD_SHOW_PIXEL_KEYBOARD=200
    val LIST_MARGIN_BOTTOW_WHEN_KEYBOARD_SHOW=300.px
    val LIST_MARGIN_BOTTOW_WHEN_KEYBOARD_HIDE=57.px

    private var keyboardHeightProvider: KeyboardHeightProvider? = null
    private val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    private fun initializeKeyboardProvider(){
        keyboardHeightProvider = KeyboardHeightProvider(activity as Activity)
        keyboardHeightProvider?.addKeyboardListener(getKeyboardListener())
    }




    private fun initializeViewRefrence(){

         listSearchedBook=view!!.findViewById(R.id.FindBook_listProposition)
         searchEditText=view!!.findViewById(R.id.FindBook_searchEditText)

    }


    private fun managedLayoutWhenKeyboardShowHide(heightKeyboard:Int){


        if(heightKeyboard>PROBABLY_KEYBOARD_SHOW_PIXEL_KEYBOARD) {
           val param = listSearchedBook.layoutParams as ViewGroup.MarginLayoutParams

            param.bottomMargin=LIST_MARGIN_BOTTOW_WHEN_KEYBOARD_SHOW
            listSearchedBook.layoutParams = param



        }else{
            val param = listSearchedBook.layoutParams as ViewGroup.MarginLayoutParams
            param.bottomMargin=LIST_MARGIN_BOTTOW_WHEN_KEYBOARD_HIDE
            listSearchedBook.layoutParams = param


        }


    }

    private fun getKeyboardListener() = object : KeyboardHeightProvider.KeyboardListener {
        override fun onHeightChanged(height: Int) {
            managedLayoutWhenKeyboardShowHide(height)

        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onPause() {
        super.onPause()
        keyboardHeightProvider?.onPause()
    }

    override fun onResume() {
        super.onResume()
        keyboardHeightProvider?.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_panel_book, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }





    private fun addAdapterToListFindsBook(){
        val adapterPopularBook = AdapterFindBook(
            activity as Activity
        )
        listSearchedBook.adapter=adapterPopularBook
    }

    private fun addCallbackToClickSearchList(){
        listSearchedBook.setOnItemClickListener { parent, view, position, id ->

            if(position<AdapterFindBook.bookSize) {
                FromWhereBookShow.setFromWherIMustShowBook(
                    FromWhereBookShow.From.FIND_BOOK,
                    position
                )
                Navigation.findNavController(view).navigate(R.id.bookFragment)
            }else{
                Fuel.get(
                    getContext()!!.resources.getString(R.string.GET_WRITERS_WITH_FILTER_NAME_ADRESS),
                    listOf(
                        "position" to position-AdapterFindBook.bookSize,
                        "filter" to AdapterFindBook.FilterBook
                    )
                ).response { _, _, result ->
                    result.fold(
                        success = {
                            val writersData =
                                WritersMapperFind().deserialize(
                                    it
                                )

                            WritersDataGlobal.idWriters=writersData.idWriters
                            Navigation.findNavController(view).navigate(R.id.writersProfile)


                        },
                        failure = { error ->


                        }
                    )
                }

            }
        }


    }



    private fun setFocusOnSearchEditTextAndShowKeyboard(){

        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }


    private fun informAdapterShowSearchBookAboutChange(adapterPopularBook:AdapterFindBook){
        listSearchedBook.adapter = adapterPopularBook
        listSearchedBook.invalidate()
        listSearchedBook.deferNotifyDataSetChanged()
        (listSearchedBook.adapter as BaseAdapter).notifyDataSetChanged()

    }

    private fun addStaffSearch(){
        searchEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                var adapterPopularBook = AdapterFindBook(
                    activity as Activity
                )

                FromWhereBookShow.setFilterShowBook(s.toString())

                if(s.length>MIN_SEARCH_BOOK_NAME_LENGTH) {
                    adapterPopularBook.setNewFilter(s.toString())
                    informAdapterShowSearchBookAboutChange(adapterPopularBook)


                }else{
                    adapterPopularBook.setNewFilter(IMPOSIBLE_BOOK_NAME)
                    informAdapterShowSearchBookAboutChange(adapterPopularBook)

                }

            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewRefrence()
        addAdapterToListFindsBook()
        addCallbackToClickSearchList()
        initializeKeyboardProvider()
        setFocusOnSearchEditTextAndShowKeyboard()
        addStaffSearch()




    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment findPanelBookFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FindPanelBookFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}