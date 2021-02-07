package project.legto.twojaksiazka3.ui.bookShow

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.legto.twojaksiazka3.R
import kotlinx.coroutines.runBlocking
import project.legto.twojaksiazka3.utility.Book_Utility
import project.legto.twojaksiazka3.utility.UserData



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [VotedBook.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [VotedBook.newInstance] factory method to
 * create an instance of this fragment.
 */

class VotedBook(val idUser:Int,val idBook:Int) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var mark=0.0f
    private lateinit var opinion:String

    private lateinit var  markBook_User:TextView
    private lateinit var   opinionBookUser:EditText
    private lateinit var  changeMarkButton_User:Button
    private lateinit var userBookMarkData:Mark
    private lateinit var  removeMarkButton:Button
    private val USER_NOT_GIVE_MARK_BOOKS=0.0


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
        return inflater.inflate(R.layout.fragment_voted_book, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }


    private fun initialiseViewReference(){
         markBook_User = view!!.findViewById(R.id.VotedBook_markBook)
         opinionBookUser = view!!.findViewById(R.id.VotedBook_opinionAboutBook)
         changeMarkButton_User = view!!.findViewById(R.id.zmienOcene)
         removeMarkButton=view!!.findViewById(R.id.VotedBook_removeMark)

    }



    private fun downloadUserBookMark(){
        runBlocking {
            val (request, response, result) =  Fuel.get(
                resources.getString(R.string.USER_BOOK_MARK_ADRESS),
                listOf("idUser" to UserData.idUser, "idBook" to idBook)
            ).awaitStringResponse()


           userBookMarkData =
                BookMarkAndOpinion().deserialize(
                    response.data
                )


             }
        }


    private fun fillLayoutMarkUserData(){

        markBook_User.text = userBookMarkData.mark.toString()
        opinionBookUser.setText(userBookMarkData.opinion.trim())
        opinionBookUser.setEnabled(false)
        mark = userBookMarkData.mark
        opinion = userBookMarkData.opinion.trim() + " "

    }

    private fun choiceFragmentToShowOnBaseUserMarkBookOrNot(){
        if(userBookMarkData.mark != USER_NOT_GIVE_MARK_BOOKS.toFloat()){
            fillLayoutMarkUserData()

        }else{
            Book_Utility.changeFragmentTo(activity!!.supportFragmentManager,R.id.Book_marksPanelFragment, YourMark(idBook,"",""))

        }
    }

    private fun addCallbackToChangeMarkButton(){
        changeMarkButton_User.setOnClickListener {
            Book_Utility.changeFragmentTo(activity!!.getSupportFragmentManager(),R.id.Book_marksPanelFragment, YourMark(idBook, mark.toString(), opinion))
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }

    }



    private fun addCallbackToRemoveMarkButton(){
        removeMarkButton.setOnClickListener {
            Fuel.post(
                context!!.getString(R.string.REMOVE_MARK_BOOK_ADRESS),
                listOf("IdBook" to idBook, "userId" to UserData.idUser)
            ).response { _, _, result ->
                Book_Utility.changeFragmentTo(activity!!.supportFragmentManager,R.id.Book_marksPanelFragment, YourMark(idBook, "", ""))
            }
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialiseViewReference()
        downloadUserBookMark()
        addCallbackToChangeMarkButton()
        choiceFragmentToShowOnBaseUserMarkBookOrNot()
        addCallbackToRemoveMarkButton()




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
         * @return A new instance of fragment VotedBook.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VotedBook(2,0).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
