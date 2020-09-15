package project.legto.twojaksiazka3.ui.bookShow

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.kittinunf.fuel.Fuel
import com.legto.twojaksiazka3.R
import project.legto.twojaksiazka3.utility.Book_Utility
import project.legto.twojaksiazka3.utility.UserData


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [YourMark.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [YourMark.newInstance] factory method to
 * create an instance of this fragment.
 */
class YourMark(val idBookParam:Int, val userOpinionParam:String, val userMarkParam:String) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var giveMarkButton:Button
    private lateinit var  userOpinion:EditText
    private lateinit var   userRating:RatingBar
    private lateinit var markDescription:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }




    private fun initialiseViewReference(){
         giveMarkButton= view!!.findViewById(R.id.YourMark_giveMarkToBookButton)
         userOpinion=view!!.findViewById(R.id.VotedBook_opinionAboutBook)
         userRating=view!!.findViewById(R.id.YourMark_ratingBook)
         markDescription=view!!.findViewById(R.id.YourMark_descriptionYourMark)


    }




    @SuppressLint("ClickableViewAccessibility")
    private fun addCallbackModificationRating(){
        userRating.setOnTouchListener { v, event ->
            val markWhichGiveUser=userRating.rating


            when(markWhichGiveUser.toInt()){
                1->{
                    markDescription.text= resources.getString(R.string.UserMark_1)

                }
                2->{
                    markDescription.text= resources.getString(R.string.UserMark_2)
                }
                3->{
                    markDescription.text= resources.getString(R.string.UserMark_3)
                }
                4->{
                    markDescription.text= resources.getString(R.string.UserMark_4)
                }
                5->{
                    markDescription.text= resources.getString(R.string.UserMark_5)
                }
                6->{
                    markDescription.text= resources.getString(R.string.UserMark_6)
                }
                7->{
                    markDescription.text= resources.getString(R.string.UserMark_7)
                }
                8->{
                    markDescription.text= resources.getString(R.string.UserMark_8)
                }
                9->{
                    markDescription.text= resources.getString(R.string.UserMark_9)
                }
                10->{
                    markDescription.text= resources.getString(R.string.UserMark_10)
                }
            }


            v?.onTouchEvent(event) ?: true
        }



        userRating.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener()
        { ratingBar: RatingBar, setMark: Float, b: Boolean ->
            if(setMark<1.0f)
                ratingBar.rating = 1.0f

            when(setMark.toInt()){
                1->{
                    markDescription.text= resources.getString(R.string.UserMark_1)

                }
                2->{
                    markDescription.text= resources.getString(R.string.UserMark_2)
                }
                3->{
                    markDescription.text= resources.getString(R.string.UserMark_3)
                }
                4->{
                    markDescription.text= resources.getString(R.string.UserMark_4)
                }
                5->{
                    markDescription.text= resources.getString(R.string.UserMark_5)
                }
                6->{
                    markDescription.text= resources.getString(R.string.UserMark_6)
                }
                7->{
                    markDescription.text= resources.getString(R.string.UserMark_7)
                }
                8->{
                    markDescription.text= resources.getString(R.string.UserMark_8)
                }
                9->{
                    markDescription.text= resources.getString(R.string.UserMark_9)
                }
                10->{
                    markDescription.text= resources.getString(R.string.UserMark_10)
                }
            }

        }

    }


    private fun checkIfUserAlreadyGiveMark(){


        if(userOpinionParam!=""){
            userOpinion.setText(userMarkParam)
            userRating.rating=(userOpinionParam).toFloat()



        }

    }


    private fun addCallbackToButtonGiveMark(){


        giveMarkButton.setOnClickListener {

            Fuel.post(resources.getString(R.string.MARK_BOOK_ADRESS), listOf("mark" to userRating.rating,"opinion" to userOpinion.text,"idUser" to UserData.idUser,"idBook" to idBookParam)).response { _, response, _ ->

                Book_Utility.changeFragmentTo(activity!!.supportFragmentManager,R.id.Book_marksPanelFragment,VotedBook(UserData.idUser,idBookParam))
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialiseViewReference()
        addCallbackModificationRating()
        checkIfUserAlreadyGiveMark()
        addCallbackToButtonGiveMark()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_your_mark, container, false)
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
         * @return A new instance of fragment YourMark.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            YourMark(0,"","").apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
