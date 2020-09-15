package project.legto.twojaksiazka3.ui.home

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.github.kittinunf.fuel.Fuel
import com.legto.twojaksiazka3.R
import org.json.JSONArray
import org.json.JSONObject
import project.legto.twojaksiazka3.ui.favorite.FriendFromFb
import project.legto.twojaksiazka3.ui.favorite.FriendsView

import java.lang.Exception

class FriendLastActivityAdapter(private val context: Activity)
    : ArrayAdapter<String>(context,
    R.layout.userfriendlastactivity) {
  //  private var bookMap=mutableMapOf<Int,Book>()
  //  private var imageBookMap= mutableMapOf<Int,ImageView>()
    companion object{
      public var FriendsDataMap=mutableMapOf<Int, FriendFromFb>()
  }
    var oneBookData:ActivityConfig = ActivityConfig()

    var ifDownloaded=false
    var sizeFriendList=3
  //  private val EMPTY_CATEGORY=" "
  private var FriendsViewMap= mutableMapOf<Int, FriendsView>()
    private  var userName=""

    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null
    }



    private fun downloadFriendListFromFb(){
     //   if(oneBookData.res.size<1){
        try {

            if (isLoggedIn() && (!ifDownloaded)) {
                val request = GraphRequest.newGraphPathRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/me/friends"
                ) {
                    // Insert your code here
                    val jsonArrayFriends: JSONArray =
                        it.getJSONObject().getJSONArray("data")

                //    val friendlistObject = jsonArrayFriends.getJSONObject(0)
                    //  Log.e("asd",friendlistObject.toString())
               //     val friendListID = friendlistObject.getString("id")
                    //  Log.e("asd",jsonArrayFriends.length().toString())
                    sizeFriendList = jsonArrayFriends.length()
                    Log.e("asx", userName)

                    for (x in 0 until jsonArrayFriends.length()) {
                        //    Log.e("usersHow",jsonArrayFriends.getJSONObject(x).getString("id"))
                        val idFbFriends = jsonArrayFriends.getJSONObject(x).getString("id")
                        //   Log.e("usersHow",jsonArrayFriends.getJSONObject(x).getString("name"))
                        //  Log.e("usersHow",x.toString())
                        val nameFbFriends = jsonArrayFriends.getJSONObject(x).getString("name")
                        FriendsDataMap[x] = FriendFromFb(idFbFriends, nameFbFriends, 0, 0)
                        userName += FriendsDataMap[x]!!.id + "','"
                        if (FriendsViewMap.containsKey(x)) {


                            //     runBlocking {
                            //  val (request, response, result) = Fuel.get(
                            //    context!!.getString(R.string.HOW_MANY_READ_BOOKS_FRIEND_QUERY),
                            //     listOf(
                            //        "fbId" to idFbFriends
                            //    )
                            //    ).awaitStringResponse()

                            //  var  friendReadData = MapperFirendsReadBook().deserialize(response.data)
                            //   FriendsViewMap[x]!!.readBookView.text=""+friendReadData.readBookFriend

                            //  }


                            ///


                            //////////


                        } else {

                        }
                        //
                        //userName[userName.length-1]=""

                        //    Log.e("usersHow",(x is Int).toString())
                    }
                    if (userName.length > 2) {
                        userName = userName.substring(0, userName.length - 2)
                        userName = "'" + userName
                    }


                }

                request.executeAsync()

            }










            ifDownloaded = true//}
        }
        catch(exp:Exception){

        }
    }


    override fun getCount(): Int {
        Log.e("asx",userName.toString()+"count")
        return super.getCount()+3
    }



  /*  private fun setCategoryBookInView(oneBook:Book,gentreBook_1:TextView,gentreBook_2:TextView,gentreBook_3:TextView){
        if (oneBook.gentre_1 != EMPTY_CATEGORY) {
            gentreBook_1.text = oneBook.gentre_1
        } else {
            gentreBook_1.visibility = View.GONE
        }
        if (oneBook.gentre_2 != EMPTY_CATEGORY) {
            gentreBook_2.text = oneBook.gentre_2
        } else {
            gentreBook_2.visibility = View.GONE
        }
        if (oneBook.gentre_3 != EMPTY_CATEGORY) {
            gentreBook_3.text = oneBook.gentre_3
        } else {
            gentreBook_3.visibility = View.GONE
        }
    }
*/
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.userfriendlastactivity, null, true)
      downloadFriendListFromFb()
      Log.e("asx",userName+"getview")


        val nameFriend = rowView.findViewById(R.id.FriendOne_nameFriend) as TextView
//        val imageFriend = rowView.findViewById(R.id.FriendOne_imageFriend) as ImageView
        val markBookByFriend:TextView= rowView.findViewById(R.id.FriendOne_markFriend) as TextView
       // val gentreBook_1:TextView= rowView.findViewById(R.id.List_gentreBook_1) as TextView
      //  val gentreBook_2:TextView= rowView.findViewById(R.id.List_gentreBook_2) as TextView
       // val gentreBook_3:TextView= rowView.findViewById(R.id.List_gentreBook_3) as TextView
      //  val imageBook:ImageView=rowView.findViewById(R.id.List_image) as ImageView

/*

        if(bookMap.containsKey(position)){
            val oneBook:Book?=bookMap[position]
            TextUtility.calculateNameBookSize(titleText, oneBook!!.titleBook, 35f)
            titleText.text = oneBook.titleBook
            authorBook.text = oneBook.nameAuthor
            markBook.text = oneBook.markBook
            imageBook.setImageDrawable(imageBookMap[position]!!.drawable)
            setCategoryBookInView(oneBook,gentreBook_1,gentreBook_2,gentreBook_3)


        }else {

*/


      Log.e("asd2",userName)

      if(userName.length>1){

     // if(oneBookData.res.size<1){
            Fuel.get(
                getContext().resources.getString(R.string.GET_LAST_ACTIVITY_FRIENDa_DRESS),
                listOf(
                    "idFbList" to userName.toString()
                )
            ).response { _, _, result ->
                result.fold(
                    success = {


                        Log.e("asd",oneBookData.toString())
                        Log.e("asdas", JSONObject(String(it)).toString())
                        Log.e("asd",FriendLastActivityListMapper().deserialize(
                            it
                        ).toString())
                       oneBookData =
                           FriendLastActivityListMapper().deserialize(
                               it
                            )
                        if(oneBookData.res.size>0) {
                            nameFriend.text = oneBookData.res[position].Login
                            //  val imageFriend
                            markBookByFriend.text = oneBookData.res[position].textActivity

                        }



                    //    TextUtility.calculateNameBookSize(titleText, oneBookData.titleBook, 35f)
                     //   titleText.text = oneBookData.titleBook
                    //    authorBook.text = oneBookData.nameAuthor
                    //    markBook.text = oneBookData.markBook
                    //    setCategoryBookInView(oneBookData,gentreBook_1,gentreBook_2,gentreBook_3)

                  //      Picasso.with(context)
                  //          .load(getContext().resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + oneBookData.idBook)
                 //           .placeholder(R.drawable.database_icon)
                //            .resize(230, 350)
                //            .centerCrop()
               //             .into(imageBook)


                       // var imageBookOne = ImageView(context)

                   //     Picasso.with(context)
                 //           .load(getContext().resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + oneBookData.idBook)
                 //           .placeholder(R.drawable.database_icon)
                 //           .resize(230, 350)
                 //           .centerCrop()
                 //           .into(imageBookOne)
                 //       imageBookMap[position] = imageBookOne


                   //     bookMap[position] = oneBookData
                    },
                    failure = { error ->

                    }
                )
            }}

      //}else{
        //  if(oneBookData.res.size>0){
       //   nameFriend.text =oneBookData.res[position].Login
          //  val imageFriend
      //    markBookByFriend.text=oneBookData.res[position].textActivity
  //    }

     // }
/*
        }*/




        return rowView




    }



}