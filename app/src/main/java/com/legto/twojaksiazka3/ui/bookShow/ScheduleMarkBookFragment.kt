package project.legto.twojaksiazka3.ui.bookShow
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.legto.twojaksiazka3.R
import kotlinx.coroutines.runBlocking



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ScheduleMarkBookFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ScheduleMarkBookFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ScheduleMarkBookFragment(val idBook:String) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var markStatistics_Data:MarkStat

    private lateinit var  ScheduleMarkProgressBar_1:ProgressBar
    private lateinit var  ScheduleMarkProgressBar_2:ProgressBar
    private lateinit var  ScheduleMarkProgressBar_3:ProgressBar
    private lateinit var  ScheduleMarkProgressBar_4:ProgressBar
    private lateinit var  ScheduleMarkProgressBar_5:ProgressBar
    private lateinit var  ScheduleMarkProgressBar_6:ProgressBar
    private lateinit var  ScheduleMarkProgressBar_7:ProgressBar
    private lateinit var  ScheduleMarkProgressBar_8:ProgressBar
    private lateinit var  ScheduleMarkProgressBar_9:ProgressBar
    private lateinit var  ScheduleMarkProgressBar_10:ProgressBar



    private lateinit var ScheduleMarkQuantity_1:TextView
    private lateinit var ScheduleMarkQuantity_2:TextView
    private lateinit var ScheduleMarkQuantity_3:TextView
    private lateinit var ScheduleMarkQuantity_4:TextView
    private lateinit var ScheduleMarkQuantity_5:TextView
    private lateinit var ScheduleMarkQuantity_6:TextView
    private lateinit var ScheduleMarkQuantity_7:TextView
    private lateinit var ScheduleMarkQuantity_8:TextView
    private lateinit var ScheduleMarkQuantity_9:TextView
    private lateinit var ScheduleMarkQuantity_10:TextView

    private lateinit var showCommendButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun initialiseViewReference(){
        showCommendButton=view!!.findViewById(R.id.ScheduleMarkBook_showCommentListButton)

        ScheduleMarkProgressBar_1=view!!.findViewById(R.id.ScheduleMarkProgressBar_1)
        ScheduleMarkProgressBar_2=view!!.findViewById(R.id.ScheduleMarkProgressBar_2)
        ScheduleMarkProgressBar_3=view!!.findViewById(R.id.ScheduleMarkProgressBar_3)
        ScheduleMarkProgressBar_4=view!!.findViewById(R.id.ScheduleMarkProgressBar_4)
        ScheduleMarkProgressBar_5=view!!.findViewById(R.id.ScheduleMarkProgressBar_5)
        ScheduleMarkProgressBar_6=view!!.findViewById(R.id.ScheduleMarkProgressBar_6)
        ScheduleMarkProgressBar_7=view!!.findViewById(R.id.ScheduleMarkProgressBar_7)
        ScheduleMarkProgressBar_8=view!!.findViewById(R.id.ScheduleMarkProgressBar_8)
        ScheduleMarkProgressBar_9=view!!.findViewById(R.id.ScheduleMarkProgressBar_9)
        ScheduleMarkProgressBar_10=view!!.findViewById(R.id.ScheduleMarkProgressBar_10)

        ScheduleMarkQuantity_1=view!!.findViewById(R.id.ScheduleMarkQuant_1)
        ScheduleMarkQuantity_2=view!!.findViewById(R.id.ScheduleMarkQuant_2)
        ScheduleMarkQuantity_3=view!!.findViewById(R.id.ScheduleMarkQuant_3)
        ScheduleMarkQuantity_4=view!!.findViewById(R.id.ScheduleMarkQuant_4)
        ScheduleMarkQuantity_5=view!!.findViewById(R.id.ScheduleMarkQuant_5)
        ScheduleMarkQuantity_6=view!!.findViewById(R.id.ScheduleMarkQuant_6)
        ScheduleMarkQuantity_7=view!!.findViewById(R.id.ScheduleMarkQuant_7)
        ScheduleMarkQuantity_8=view!!.findViewById(R.id.ScheduleMarkQuant_8)
        ScheduleMarkQuantity_9=view!!.findViewById(R.id.ScheduleMarkQuant_9)
        ScheduleMarkQuantity_10=view!!.findViewById(R.id.ScheduleMarkQuant_10)

    }

//Odpala nowy fragment usuń
    private fun changeFragmentTo(fragmentFrame:Int,newFragment:Fragment){
        val manager: FragmentManager = activity!!.supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(fragmentFrame, newFragment)
        transaction.commit()

    }
//jak wyzej zmien
    private fun addCallbackShowListCommentsButton(){
        showCommendButton.setOnClickListener {
            changeFragmentTo(R.id.Book_marksPanelFragment,CommentBookFragment(idBook))

        }

    }


    private fun downloadDataBookStatistics(){
        runBlocking {

                val (request, response, result) = Fuel.get(
                    getContext()!!.resources.getString(R.string.USER_MARK_STATISTICS_ADRESS),
                    listOf(
                        "idBook" to idBook
                    )
                ).awaitStringResponse()



                    markStatistics_Data =
                        MarkStatistics().deserialize(
                            response.data
                        )
        }



    }



    private fun setDescriptionHowManyMarkAboutTisNumber(){

        ScheduleMarkQuantity_1.text = markStatistics_Data.mark_1
        ScheduleMarkQuantity_2.text = markStatistics_Data.mark_2
        ScheduleMarkQuantity_3.text = markStatistics_Data.mark_3
        ScheduleMarkQuantity_4.text = markStatistics_Data.mark_4
        ScheduleMarkQuantity_5.text = markStatistics_Data.mark_5
        ScheduleMarkQuantity_6.text = markStatistics_Data.mark_6
        ScheduleMarkQuantity_7.text = markStatistics_Data.mark_7
        ScheduleMarkQuantity_8.text = markStatistics_Data.mark_8
        ScheduleMarkQuantity_9.text = markStatistics_Data.mark_9
        ScheduleMarkQuantity_10.text = markStatistics_Data.mark_10

    }

    private fun setStatisticOnRatingBar(){
        var countOfMark=0

        countOfMark+= markStatistics_Data.mark_1.toInt()
        countOfMark+= markStatistics_Data.mark_2.toInt()
        countOfMark+= markStatistics_Data.mark_3.toInt()
        countOfMark+= markStatistics_Data.mark_4.toInt()
        countOfMark+= markStatistics_Data.mark_5.toInt()
        countOfMark+= markStatistics_Data.mark_6.toInt()
        countOfMark+= markStatistics_Data.mark_7.toInt()
        countOfMark+= markStatistics_Data.mark_8.toInt()
        countOfMark+= markStatistics_Data.mark_9.toInt()
        countOfMark+= markStatistics_Data.mark_10.toInt()

        if(countOfMark==0) {
            countOfMark=1
        }

        ScheduleMarkProgressBar_1.progress =
            Math.round((markStatistics_Data.mark_1.toDouble()*100/countOfMark)).toInt()
        ScheduleMarkProgressBar_2.progress =
            Math.round((markStatistics_Data.mark_2.toDouble()*100/countOfMark)).toInt()
        ScheduleMarkProgressBar_3.progress =
            Math.round((markStatistics_Data.mark_3.toDouble()*100/countOfMark)).toInt()
        ScheduleMarkProgressBar_4.progress =
            Math.round((markStatistics_Data.mark_4.toDouble()*100/countOfMark)).toInt()
        ScheduleMarkProgressBar_5.progress =
            Math.round((markStatistics_Data.mark_5.toDouble()*100/countOfMark)).toInt()
        ScheduleMarkProgressBar_6.progress =
            Math.round((markStatistics_Data.mark_6.toDouble()*100/countOfMark)).toInt()
        ScheduleMarkProgressBar_7.progress =
            Math.round((markStatistics_Data.mark_7.toDouble()*100/countOfMark)).toInt()
        ScheduleMarkProgressBar_8.progress =
            Math.round((markStatistics_Data.mark_8.toDouble()*100/countOfMark)).toInt()
        ScheduleMarkProgressBar_9.progress =
            Math.round((markStatistics_Data.mark_9.toDouble()*100/countOfMark)).toInt()
        ScheduleMarkProgressBar_10.progress =
            Math.round((markStatistics_Data.mark_10.toDouble()*100/countOfMark)).toInt()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialiseViewReference()
        addCallbackShowListCommentsButton()
        downloadDataBookStatistics()
        setStatisticOnRatingBar()
        setDescriptionHowManyMarkAboutTisNumber()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_mark_book, container, false)
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
         * @return A new instance of fragment scheduleMarkBook.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScheduleMarkBookFragment("").apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
