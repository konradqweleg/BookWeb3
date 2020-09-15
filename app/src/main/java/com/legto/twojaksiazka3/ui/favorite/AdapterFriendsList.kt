package project.legto.twojaksiazka3.ui.favorite

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.legto.twojaksiazka3.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking
import org.json.JSONArray



class AdapterFriendsList(private val context: Activity)
    : ArrayAdapter<String>(context,
    R.layout.friends_one_elem_list) {
    var ifDownloaded=false
    var sizeFriendList=3

    companion object{
        public var FriendsDataMap=mutableMapOf<Int, FriendFromFb>()
    }


    private var FriendsimageMap=mutableMapOf<Int, ImageView>()
    private var FriendsViewMap= mutableMapOf<Int,FriendsView>()

    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null
    }
    private fun downloadFriendListFromFb(){

        if(isLoggedIn() &&(!ifDownloaded)){
            val request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends"
            ) {
                // Insert your code here
                val jsonArrayFriends: JSONArray =
                    it.getJSONObject().getJSONArray("data")
                val friendlistObject = jsonArrayFriends.getJSONObject(0)
              //  Log.e("asd",friendlistObject.toString())
                val friendListID = friendlistObject.getString("id")
              //  Log.e("asd",jsonArrayFriends.length().toString())
                sizeFriendList=jsonArrayFriends.length()

                for(x in 0 until jsonArrayFriends.length() ){
                //    Log.e("usersHow",jsonArrayFriends.getJSONObject(x).getString("id"))
                    val idFbFriends=jsonArrayFriends.getJSONObject(x).getString("id")
                 //   Log.e("usersHow",jsonArrayFriends.getJSONObject(x).getString("name"))
                  //  Log.e("usersHow",x.toString())
                    val nameFbFriends=jsonArrayFriends.getJSONObject(x).getString("name")
                    FriendsDataMap[x]= FriendFromFb(idFbFriends,nameFbFriends,0,0)
                    if( FriendsViewMap.containsKey(x)){


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




                        Fuel.get(
                            getContext().resources.getString(R.string.HOW_MANY_READ_BOOKS_FRIEND_QUERY),
                            listOf(
                                "fbId" to idFbFriends
                            )
                        ).response { _, _, result ->
                            result.fold(
                                success = {
                                    val friendReadData =
                                       MapperFirendsReadBook().deserialize(
                                            it
                                        )
                                    FriendsViewMap[x]!!.readBookView.text=""+friendReadData.readBookFriend+" Przeczytane"


                                },
                                failure = { error ->

                                }
                            )
                        }



                        Fuel.get(
                            getContext().resources.getString(R.string.POSITION_RANK_FB_FRIENDS_QUERY),
                            listOf(
                                "fbId" to idFbFriends
                            )
                        ).response { _, _, result ->
                            result.fold(
                                success = {
                                    val friendRankData =
                                        PositionRankFriendsMapper().deserialize(
                                            it
                                        )
                                    if(friendRankData.positionRank!=0) {
                                        FriendsViewMap[x]!!.positionRankView.text =
                                            "" + (friendRankData.positionRank+1) + " pozycja"
                                    }else{
                                        FriendsViewMap[x]!!.positionRankView.text ="Nie przeczytano żadnej ksiażki"


                                    }

                                },
                                failure = { error ->

                                }
                            )
                        }























                        //////////


                         FriendsViewMap[x]!!.nameView.text=FriendsDataMap[x]!!.name
                        Picasso.with(context)
                            .load( "https://graph.facebook.com/" + idFbFriends + "/picture?type=large")
                            .placeholder(R.drawable.database_icon)
                            .resize(230, 230)
                            .centerCrop()
                            .into(FriendsViewMap[x]!!.imageView)
                    }
                    Log.e("usersHow",FriendsDataMap[x]!!.name.toString()+"Dodałem")
                //    Log.e("usersHow",(x is Int).toString())
                }


            }

            request.executeAsync()

        }










        ifDownloaded=true
    }


   /*

    private var bookReadMap=mutableMapOf<Int, userread>()
    private var imageMap=mutableMapOf<Int, ImageView>()

    private val markWhereInTextResponseUserReadBook="bookRead\":"
*/
    @ExperimentalStdlibApi
    override fun getCount(): Int {

return sizeFriendList
    /*    if(listElement==0) {
            try {

                Fuel.get(context.getString(R.string.HOW_MANY_READ_BOOKS_USER_ADRESS)+UserData.idUser)
                    .response { _, _, result -> result.fold(
                        success = {

                            var dataFromServerHowManyBookOnList =it.decodeToString().substring(
                                0,
                                it.decodeToString().length - 1
                            )

                            listElement = dataFromServerHowManyBookOnList.substringAfter(markWhereInTextResponseUserReadBook).toInt()


                        },
                        failure = { error ->

                        }
                    ) }



            } catch (exception: Exception) {
                println("A network request exception was thrown: ${exception.message}")
            }

            return super.getCount() + INITIAL_LIST_SIZE
        }else{
            return listElement
        }
*/
    }



    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.friends_one_elem_list, null, true)

        downloadFriendListFromFb()
        val friendName=rowView.findViewById<TextView>(R.id.FriendsList_name)
        val friendsBookReads=rowView.findViewById<TextView>(R.id.FriendsList_howBookReads)
        val friendsPositionRank=rowView.findViewById<TextView>(R.id.FriendsList_rankPosition)
        val friendsImage=rowView.findViewById<ImageView>(R.id.FriendsList_image)

        if( FriendsViewMap.containsKey(position)){

        }else{
            FriendsViewMap[position]=FriendsView(friendName,friendsImage,friendsBookReads,friendsPositionRank)

        }

        Log.e("usersHow",FriendsDataMap.toString())



    /*    val titleText = rowView.findViewById(R.id.OneCategoryBook_title) as TextView
        val authorBook = rowView.findViewById(R.id.OneCategoryBook_author) as TextView
        val imageBook: ImageView =rowView.findViewById(R.id.OneCategoryBook_image) as ImageView
        val markBookView = rowView!!.findViewById<TextView>(R.id.OneCategoryBook_markBook)



        var bookData: userread

        if(bookReadMap.containsKey(position)){
            bookData= bookReadMap[position]!!
            imageBook.setImageDrawable(imageMap[position]!!.drawable)
            titleText.text = bookData.nameBook
            authorBook.text = bookData.nameWriters+" "+bookData.surnameWriters
            markBookView.text=""+bookData.userMark


        } else {

            Fuel.get(
                context.getString(R.string.READ_BOOK_LIST_ABOUT_POSITON_ADRESS),
                listOf(
                    "position" to position, "idUser" to UserData.idUser
                )
            ).response { _, _, result ->
                result.fold(
                    success = {
                        val bookData =
                            ListBookReadMapper().deserialize(
                                it
                            )

                        var imageToSaveOnTemp= ImageView(context)
                        Picasso.with(context)
                            .load(getContext().resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + bookData.IdBook)
                            .placeholder(R.drawable.database_icon)
                            .resize(WIDTH_IMAGE, HEIGHT_IAMGE)
                            .centerCrop()
                            .into(imageToSaveOnTemp)
                        imageMap[position] = imageToSaveOnTemp




                        TextUtility.calculateNameBookSize(titleText, bookData.nameBook, 35f)
                        titleText.text = bookData.nameBook
                        authorBook.text = bookData.nameWriters+" "+bookData.surnameWriters
                        markBookView.text=""+bookData.userMark

                        bookReadMap[position]=bookData


                        Picasso.with(context)
                            .load(getContext().resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + bookData.IdBook)
                            .placeholder(R.drawable.database_icon)
                            .resize(230, 350)
                            .centerCrop()
                            .into(imageBook)

                    },
                    failure = { error ->

                    }
                )
            }
        }
        */


        return rowView







    }}

