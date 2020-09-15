package project.legto.twojaksiazka3.ui.profil

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.legto.twojaksiazka3.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking
import project.legto.twojaksiazka3.utility.SaveUserAuthorization
import project.legto.twojaksiazka3.utility.UserData

import java.lang.Math.round


class ProfilFragment : Fragment() {
    private lateinit var userPhotoView:de.hdodenhof.circleimageview.CircleImageView
    private lateinit var notificationsViewModel: ProfilFragmentViewModel
    private lateinit var mapperDataUserStatistics:userStatistics
    private lateinit var readBookView:TextView
    private lateinit var timeReadView:TextView
    private lateinit var avgBookMarkView:TextView
    private lateinit var userLoginView:TextView
    private lateinit var readBookViewButton:Button
    private val WIDTH_USER_IMAGE=230
    private val HEIGHT_USER_IMAGE=350

    companion object{
        var showSelectBookFromList=0
        var adresPhoto=""
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
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


    fun downloadUserStattistics(){
        Log.e("dane", SaveUserAuthorization.getUserLogin(activity as Activity))
        runBlocking {
            val (request, response, result) = Fuel.get(
                context!!.getString(R.string.USER_STATISTICS_ADRESS),
                listOf(
                    "idUser" to UserData.idUser
                )
            ).awaitStringResponse()

            mapperDataUserStatistics = UserReadBookMaper().deserialize(response.data)


        }
    }

    fun downloadUserImagePhoto(){
       // Log.e("xx", adresPhoto)
        if(adresPhoto!=""){
           // Log.e("xx", adresPhoto+" Robię")

            val params = Bundle()
            params.putString("fields", "id,email,gender,cover,picture.type(large)")
            GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                GraphRequest.Callback { response ->
                    if (response != null) {
                        try {
                            val data = response.jsonObject
                            if (data.has("picture")) {
                                val profilePicUrl =
                                    data.getJSONObject("picture").getJSONObject("data")
                                        .getString("url")
                            //    Log.e("xx","https://graph.facebook.com//1133554107019781//picture?type=large&access_token="+AccessToken.getCurrentAccessToken())
                                Picasso.with(context)
                                    .load( profilePicUrl)
                                    .placeholder(R.drawable.database_icon)
                                    .resize(WIDTH_USER_IMAGE, HEIGHT_USER_IMAGE)
                                    .centerCrop()
                                    .into(userPhotoView)

                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }).executeAsync()



        }else{
        Log.e("dane",SaveUserAuthorization.getUserLogin(activity as Activity))
        Picasso.with(context)
            .load(context!!.getString(R.string.USER_IMAGE_ADRESS) + UserData.idUser)
            .placeholder(R.drawable.database_icon)
            .resize(WIDTH_USER_IMAGE, HEIGHT_USER_IMAGE)
            .centerCrop()
            .into(userPhotoView)
    }}

    fun initializeReferenceView(){
        userPhotoView=view!!.findViewById(R.id.profil_userPhoto)
        readBookView=view!!.findViewById(R.id.profil_userReadBook)
        timeReadView=view!!.findViewById(R.id.profil_timeRead)
        avgBookMarkView=view!!.findViewById(R.id.profil_userAvg)
        userLoginView=view!!.findViewById(R.id.profil_userLogin)
        readBookViewButton=view!!.findViewById(R.id.profil_readBookButton)
    }


    fun addCallbackToButtonShowReadsBook(){
        readBookViewButton.setOnClickListener {
            Navigation.findNavController(view!!).navigate(R.id.readsBook)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeReferenceView()
        downloadUserImagePhoto()
        downloadUserStattistics()
        fillViewDownlaodedData()
        addCallbackToButtonShowReadsBook()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(ProfilFragmentViewModel::class.java)
        val root = inflater.inflate(R.layout.profilenewversion, container, false)


        return root
    }

}
