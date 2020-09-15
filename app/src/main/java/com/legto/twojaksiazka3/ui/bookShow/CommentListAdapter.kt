package project.legto.twojaksiazka3.ui.bookShow

import android.app.Activity
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.legto.twojaksiazka3.R
import kotlinx.coroutines.runBlocking


class CommentListAdapter(private val context: Activity,private val bookId:Int)
    : ArrayAdapter<String>(context,
    R.layout.list_comment_one) {

    private var howListElem=0


    fun readDataStringServerResponseAndChangeToElemComment(data:String){
        val substringData = data.substring(0, data.length - 1)
        howListElem = substringData.substringAfter("nts\":").toInt()


    }



    override fun getCount(): Int {
        if(howListElem!=0){
            return howListElem
        }else {

            runBlocking {
                try {
                    var dataFromServerHowManyElemRow =
                        Fuel.get(getContext().resources.getString(R.string.HOW_MANY_COMMENT_BOOK_ADRESS)+bookId)
                            .awaitString()

                   readDataStringServerResponseAndChangeToElemComment(dataFromServerHowManyElemRow)


                } catch (exception: Exception) {
                    println("A network request exception was thrown: ${exception.message}")
                }
            }


            return super.getCount() + howListElem
        }



    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {


        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_comment_one, null, true)
        val userName = rowView.findViewById<TextView>(R.id.ListCommentOne_userName)
        var userMark = rowView.findViewById<TextView>(R.id.marks)
        var userOpinion = rowView.findViewById<TextView>(R.id.ListCommentOne_userOpinion)

        if(view!=null){
            return view
        }else {


            runBlocking {

                    val (request, response, result) = Fuel.get(
                        getContext().resources.getString(R.string.GET_COMMENTS_BOOKS_ADRESS),
                        listOf(
                            "idBook" to bookId, "position" to position
                        )
                    ).awaitStringResponse()

                    val commentBook =
                        CommentData().deserialize(
                            response.data
                        )


                    userName.text = commentBook.userName
                    userMark.text = commentBook.userMark
                    userOpinion.text = commentBook.userOpinion.trim()


                }


            }

            return rowView
        }
    }



