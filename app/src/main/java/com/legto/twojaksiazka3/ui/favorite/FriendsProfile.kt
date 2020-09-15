package project.legto.twojaksiazka3.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.legto.twojaksiazka3.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking
import project.legto.twojaksiazka3.ui.profil.ProfilFragmentViewModel
import project.legto.twojaksiazka3.ui.profil.UserReadBookMaper
import project.legto.twojaksiazka3.ui.profil.userStatistics


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FriendsProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class FriendsProfile() : Fragment() {


    private lateinit var userPhotoView:de.hdodenhof.circleimageview.CircleImageView
    private lateinit var notificationsViewModel: ProfilFragmentViewModel
    private lateinit var mapperDataUserStatistics: userStatistics
    private lateinit var readBookView: TextView
    private lateinit var timeReadView: TextView
    private lateinit var avgBookMarkView: TextView
    private lateinit var userLoginView: TextView
    private lateinit var readBookViewButton: Button
    private val WIDTH_USER_IMAGE=230
    private val HEIGHT_USER_IMAGE=350

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return Math.round(this * multiplier) / multiplier
    }

    fun calculateHowTimeBookReadHuman(readPage:Int):String{
        var howMinutsRead=readPage*1.38
        var howDaysRead=((howMinutsRead/60)/24).toInt()
        var howHoursRead=((howMinutsRead-(howDaysRead*24*60))/60).toInt()
        var howMinutesRead=(howMinutsRead-((howDaysRead*60*24)+(howHoursRead*60))).toInt()
        return howDaysRead.toString()+"d "+howHoursRead+"h "+howMinutesRead+"m"

    }


    fun fillViewDownlaodedData(){
        readBookView.setText(""+mapperDataUserStatistics.readBook)
        timeReadView.setText(calculateHowTimeBookReadHuman(mapperDataUserStatistics.readPage))
        avgBookMarkView.setText(""+(mapperDataUserStatistics.avgMark).round(2))
        userLoginView.setText(" "+mapperDataUserStatistics.login+" ")
    }
    fun downloadUserImagePhoto(){

                                Picasso.with(context)
                                    .load("https://graph.facebook.com/" + idFacebook + "/picture?type=large" )
                                    .placeholder(R.drawable.database_icon)
                                    .resize(WIDTH_USER_IMAGE, HEIGHT_USER_IMAGE)
                                    .centerCrop()
                                    .into(userPhotoView)

       }

    fun initializeReferenceView(){
        userPhotoView=view!!.findViewById(R.id.profilFriend_userPhoto)
        readBookView=view!!.findViewById(R.id.profilFriend_userReadBook)
        timeReadView=view!!.findViewById(R.id.profilFriend_timeRead)
        avgBookMarkView=view!!.findViewById(R.id.profilFriend_userAvg)
        userLoginView=view!!.findViewById(R.id.profilFriend_userLogin)
        readBookViewButton=view!!.findViewById(R.id.profilFriend_readBookButton)
    }


    fun addCallbackToButtonShowReadsBook(){
        readBookViewButton.setOnClickListener {
            AdapterFriendReadsBook.facebookId= idFacebook
            Navigation.findNavController(view!!).navigate(R.id.friendsProfileReadBooks)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeReferenceView()
        downloadUserImagePhoto()
        downloadFriendsStattistics()
        fillViewDownlaodedData()
        addCallbackToButtonShowReadsBook()

    }



    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    fun downloadFriendsStattistics(){

        runBlocking {
            val (request, response, result) = Fuel.get(
                context!!.getString(R.string.GET_USER_FRIEND_DATA_SETTINGS_QUERY),
                listOf(
                    "idFb" to idFacebook
                )
            ).awaitStringResponse()

            mapperDataUserStatistics = UserReadBookMaper().deserialize(response.data)


        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends_profile, container, false)
    }

    companion object {
        public fun setFacebookId(idFb:String){
            idFacebook=idFb
        }

        var idFacebook=""
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FriendsProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FriendsProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
