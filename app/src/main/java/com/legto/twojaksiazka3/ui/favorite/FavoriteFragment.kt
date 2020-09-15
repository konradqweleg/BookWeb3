package project.legto.twojaksiazka3.ui.favorite

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.legto.twojaksiazka3.R
import org.json.JSONArray



class FavoriteFragment : Fragment() {
    private lateinit var friendsList:ListView
    fun disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return  // already logged out
        }
        GraphRequest(
            AccessToken.getCurrentAccessToken(),
            "/me/permissions/",
            null,
            HttpMethod.DELETE,
            GraphRequest.Callback { LoginManager.getInstance().logOut() }).executeAsync()
    }

    private lateinit var notificationsViewModel: FavoriteViewModel


    private fun addViewReference()
    {
        friendsList=view!!.findViewById(R.id.Friends_friendsList)

    }
    private fun addAdapterToListView(){
        val adapterfriends =AdapterFriendsList(
            activity as Activity
        )


        friendsList.adapter=adapterfriends
    }

    fun setOnClickOnListFriend(){
        friendsList.setOnItemClickListener { parent, view, position, id ->
            FriendsProfile.setFacebookId(AdapterFriendsList.FriendsDataMap[position]!!.id)
            Navigation.findNavController(view!!).navigate(R.id.friendsProfile)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addViewReference()
        addAdapterToListView()
        setOnClickOnListFriend()




       // disconnectFromFacebook()










/*
        GraphRequest(
            AccessToken.getCurrentAccessToken(),
            "/{user-id}/friends",
            null,
            HttpMethod.GET,
            GraphRequest.Callback {



                val jsonObject: JSONObject = it.jsonObject
                Log.d("data", jsonObject.toString(0))

                val array = jsonObject.getJSONArray("data")
                for (i in 0 until array.length()) {
                    val friend = array.getJSONObject(i)
                    Log.d("uid", friend.getString("uid"))
                    Log.d("name", friend.getString("name"))
                    Log.d("pic_square", friend.getString("pic_square"))
                }
            }
        ).executeAsync()*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        val root = inflater.inflate(R.layout.favorite_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)

        return root
    }

}
