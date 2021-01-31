package project.legto.twojaksiazka3.ui.bookShow

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.legto.twojaksiazka3.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking
import project.legto.twojaksiazka3.CategoryBooks
import project.legto.twojaksiazka3.FromWhereBookShow
import project.legto.twojaksiazka3.ui.databaseBooks.ShowBookWithOneCategory
import project.legto.twojaksiazka3.ui.home.Book
import project.legto.twojaksiazka3.ui.home.BookData
import project.legto.twojaksiazka3.utility.Book_Utility
import project.legto.twojaksiazka3.utility.TextUtility
import project.legto.twojaksiazka3.utility.UserData
import project.legto.twojaksiazka3.utility.WritersDataGlobal


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class BookFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var howModificationRating:Int=0



    private lateinit var panelFragment_userClick_scheduleBooks:TextView
    private lateinit var yourMarkText:TextView
    private lateinit var scheduleMark:TextView
    private lateinit var bookTitleView:TextView
    private lateinit var bookPhotoView:ImageView
    private lateinit var bookAuthorNameView:TextView
    private lateinit var bookMarkView:TextView
    private lateinit var bookPageView:TextView
    private lateinit var bookYearView:TextView
    private lateinit var bookGentreView_1:TextView
    private lateinit var bookGentreView_2:TextView
    private lateinit var bookGentreView_3:TextView
    private lateinit var bookHowMarkView:TextView
    private lateinit var bookBackArrowView:ImageView
    private lateinit var bookScrollView:ScrollView
    private lateinit  var panelFragment_userClick_youMark:TextView
    private lateinit var bookShowData: Book
    private lateinit  var bookDescriptionView:TextView
    private lateinit var descriptionActionMark:TextView
    private lateinit var userBookMarkData:Mark
    private lateinit var markUserView:me.zhanghai.android.materialratingbar.MaterialRatingBar







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
        return inflater.inflate(R.layout.newbook, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }


    private fun downlaodDataTopBook(){
        runBlocking {
            val (request, response, result) = Fuel.get(
                context!!.resources.getString(R.string.GET_DATA_RANK_BOOK_ADRESS),
                listOf(
                    "numberBook" to FromWhereBookShow.Position
                )
            ).awaitStringResponse()

            bookShowData = BookData().deserialize(response.data)


        }
    }

    private fun downloadDataFavoriteBookChoice(){
        runBlocking {
            val (request, response, result) = Fuel.get(
                context!!.resources.getString(R.string.GIVE_POPULAR_BOOK_ADRESS),
                listOf(
                    "numberBook" to FromWhereBookShow.Position
                )
            ).awaitStringResponse()

            bookShowData = BookData().deserialize(response.data)


        }
    }


    private fun downlaodDataBookAboutId(){
        runBlocking {
            val (request, response, result) = Fuel.get(
                context!!.getString(R.string.GET_BOOK_ABOUT_ID),
                listOf(
                    "idBook" to FromWhereBookShow.idBook

                )
            ).awaitStringResponse()

            Log.e("aax","Wyskakuje")

            bookShowData = BookData().deserialize(response.data)


        }
    }

    private fun downlaodDataFindBookChoice(){
        runBlocking {
            val (request, response, result) = Fuel.get(
                context!!.getString(R.string.SEARCH_BOOK_ADRESS),
                listOf(
                    "numberBook" to FromWhereBookShow.Position,
                    "filter" to FromWhereBookShow.Filter
                )
            ).awaitStringResponse()

            bookShowData = BookData().deserialize(response.data)


        }

    }

private fun addActionToClickWriters(){
    bookAuthorNameView.setOnClickListener {

        WritersDataGlobal.idWriters= bookShowData.idWriters
        Navigation.findNavController(view!!).navigate(R.id.writersProfile)
    }
}
    private fun downloadDataWithCategoryChoiceBook(){
        Log.e("Filtry","Ocena "+ ShowBookWithOneCategory.lowerValueMarkviewC+":"+ShowBookWithOneCategory.biggerValueMarkC
        +" Rok powstania "+ShowBookWithOneCategory.lowerValueYearsC+":"+ShowBookWithOneCategory.biggerValueYearsC+" Autor "+ShowBookWithOneCategory.nameAuthorsC)







        runBlocking {
            val (request, response, result) = Fuel.get(
                context!!.getString(R.string.GIVE_BOOK_ONE_CATEGORY_CHOICE_ADRESS),
                listOf(
                    "numberBook" to FromWhereBookShow.Position,
                    "filter" to CategoryBooks.CategoryChoice.nameBookCategory,
                    "lowerValueMark" to ShowBookWithOneCategory.lowerValueMarkviewC,
                    "biggerValueMark" to ShowBookWithOneCategory.biggerValueMarkC,
                    "lowerValueYears" to ShowBookWithOneCategory.lowerValueYearsC,
                    "biggerValueYears" to ShowBookWithOneCategory.biggerValueYearsC,
                    "nameAuthors" to ShowBookWithOneCategory.nameAuthorsC

                )
            ).awaitStringResponse()


            bookShowData = BookData().deserialize(response.data)


        }

    }


    private fun addCallbackToBacnkArrowToListBookWithOneCategory(){
        //nadpisuje dla tego wejscia z listy ksiazek nalezacych do kategorii
        bookBackArrowView.setOnClickListener {
            fragmentManager!!.popBackStackImmediate();//Wraca do poprzedneigo fragmentu :D
        }

    }

    fun onBackPressed() {
        fragmentManager!!.popBackStackImmediate()
    }

    private fun setArrowBackAction(){
        when (FromWhereBookShow.FromWhere) {
            FromWhereBookShow.From.FAVORITE_BOOK -> {
                addCallbackToBackArrowToMenu()
            }
            FromWhereBookShow.From.FIND_BOOK -> {

                addCallbackToBackArrowToMenu()

            }
            FromWhereBookShow.From.READ_BOOKS ->{
                bookBackArrowView.setOnClickListener {
                    fragmentManager!!.popBackStackImmediate();//Wraca do poprzedneigo fragmentu :D
                }

            }
            FromWhereBookShow.From.RANK_BOOK->{
                bookBackArrowView.setOnClickListener {
                    fragmentManager!!.popBackStackImmediate();//Wraca do poprzedneigo fragmentu :D
                }
            }
            FromWhereBookShow.From.ID_BOOK->{
                bookBackArrowView.setOnClickListener {
                    fragmentManager!!.popBackStackImmediate();//Wraca do poprzedneigo fragmentu :D
                }
            }
            else -> {
                addCallbackToBacnkArrowToListBookWithOneCategory()

            }
        }

    }


    private fun downloadBookFromServerDueUserReads(){
        runBlocking {
            val (request, response, result) = Fuel.get(
               context!!.getString(R.string.SHOW_BOOK_WITH_USER_READ_FROM_LISTREADS_ADRESS),
                listOf(
                    "numberBook" to FromWhereBookShow.Position,
                    "userId" to UserData.idUser

                )
            ).awaitStringResponse()


            bookShowData = BookData().deserialize(response.data)


        }
    }

    private fun downloadBookFromServerWhichUserShowActually_Firstly() {


        when (FromWhereBookShow.FromWhere) {
            FromWhereBookShow.From.FAVORITE_BOOK -> {

                downloadDataFavoriteBookChoice()


            }
            FromWhereBookShow.From.FIND_BOOK -> {

                downlaodDataFindBookChoice()

            }

            FromWhereBookShow.From.READ_BOOKS->{
                downloadBookFromServerDueUserReads()
            }

            FromWhereBookShow.From.RANK_BOOK->{
                downlaodDataTopBook()
            }
            FromWhereBookShow.From.ID_BOOK->{
                downlaodDataBookAboutId()
            }
            else -> {
                downloadDataWithCategoryChoiceBook()

            }
        }
    }

    private fun initializeViewRefrence(){
         panelFragment_userClick_scheduleBooks=view!!.findViewById(R.id.Book_usersMarks)
         yourMarkText=view!!.findViewById(R.id.Book_yourMark)
         scheduleMark=view!!.findViewById(R.id.Book_usersMarks)
         bookTitleView=view!!.findViewById(R.id.Book_bookTitle)
         bookPhotoView=view!!.findViewById(R.id.Book_photoBook)
         bookAuthorNameView=view!!.findViewById(R.id.Book_authorsBook)
         scheduleMark=view!!.findViewById(R.id.Book_usersMarks)
         bookMarkView=view!!.findViewById(R.id.ProfileWriters_writersMark)
         bookPageView=view!!.findViewById(R.id.Book_pageInBook)
         bookYearView=view!!.findViewById(R.id.Book_yearPublish)
         bookGentreView_1=view!!.findViewById(R.id.ProfileWriters_ifTopBook)
         bookGentreView_2=view!!.findViewById(R.id.ProfileWriters_ifTopWriters)
         bookGentreView_3=view!!.findViewById(R.id.Book_gentreBook_3)

         bookHowMarkView=view!!.findViewById(R.id.Book_howManyMarks)
         bookBackArrowView=view!!.findViewById(R.id.Book_returnToMenuArrow)
         bookScrollView=view!!.findViewById(R.id.Book_scroll)
    //     panelFragment_userClick_youMark= view!!.findViewById(R.id.Book_yourMark)
         bookDescriptionView=view!!.findViewById(R.id.ProfileWriters_infoAboutAuthors)
        markUserView=view!!.findViewById(R.id.YourMark_ratingBookw)
        descriptionActionMark=view!!.findViewById(R.id.BookMark_descriptionActionMark)

    }




    private fun setNewFragmentAboutMark(typeFragment:BookFragmentTypeEnum)
    {
        when(typeFragment){
            BookFragmentTypeEnum.SCHEDULE_FRAGMENT->{
                yourMarkText.setBackgroundResource(0)
                yourMarkText.setTextColor(Color.parseColor("#808080"))
                scheduleMark.setBackgroundResource(R.drawable.line_bottom)
                scheduleMark.setTextColor(Color.parseColor("#000000"))

            }

            BookFragmentTypeEnum.SHOW_YOUR_MARK_FRAGMENT->{
                yourMarkText.setBackgroundResource(R.drawable.line_bottom)
                yourMarkText.setTextColor(Color.parseColor("#000000"))
                scheduleMark.setBackgroundResource(0)
                scheduleMark.setTextColor(Color.parseColor("#808080"))
            }
        }



    }

    private fun setActionWhenUserClickShowScheduleBooks(){

           panelFragment_userClick_scheduleBooks.setOnClickListener {

               Book_Utility.changeFragmentTo(activity!!.supportFragmentManager,R.id.Book_marksPanelFragment,ScheduleMarkBookFragment(bookShowData.idBook.toString()))
               setNewFragmentAboutMark(BookFragmentTypeEnum.SCHEDULE_FRAGMENT)

        }

    }


    private fun getAllPermition(){
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

    }


    private fun downloadUserBookMark(userId:Int,idBook:Int){
        Log.e("ocena","Pobieram ocene")
        runBlocking {
            val (request, response, result) =  Fuel.get(
                resources.getString(R.string.USER_BOOK_MARK_ADRESS),
                listOf("idUser" to userId, "idBook" to idBook)
            ).awaitStringResponse()


            userBookMarkData =
                BookMarkAndOpinion().deserialize(
                    response.data
                )
            Log.e("ocena"," Ustawiam twoją ocenke w widoku "+userBookMarkData.mark)
            markUserView.rating=userBookMarkData.mark.toFloat()
            descriptionActionMark.setText("Zmień")


        }
    }



    private fun choiceActionDependOnUserGiveMarkOrNot(){
        Log.e("ocena","Dane otrzymane "+UserData.idUser.toString()+" "+ bookShowData.idBook.toString())
        Fuel.get(context!!.resources.getString(R.string.IF_USER_VOTED_BOOK_ADRESS), listOf("userId" to UserData.idUser,"idBook" to bookShowData.idBook )).response { _, response, _ ->

            val responseIfUserVotedBook =
                IsUserVotedBook().deserialize(
                    response.data
                )


            Log.e("ocena","czy dałeś ocene ? "+responseIfUserVotedBook.ifUserGiveMark.toString())
            if (responseIfUserVotedBook.ifUserGiveMark) {
                downloadUserBookMark(UserData.idUser,bookShowData.idBook)
                Log.e("ocena"," Dałeś ocene")

              //  Book_Utility.changeFragmentTo(activity!!.supportFragmentManager,R.id.Book_marksPanelFragment,VotedBook(UserData.idUser,bookShowData.idBook))



            }else{


              //  Book_Utility.changeFragmentTo(activity!!.supportFragmentManager,R.id.Book_marksPanelFragment,YourMark(idBookParam = bookShowData.idBook,userOpinionParam = "",userMarkParam = ""))

            }

        }




    }

    private fun setActionShowMarkAndGive(){
       // panelFragment_userClick_youMark.setOnClickListener {

          // setNewFragmentAboutMark(BookFragmentTypeEnum.SHOW_YOUR_MARK_FRAGMENT)
            getAllPermition()
            choiceActionDependOnUserGiveMarkOrNot()


      //  }

    }


    private fun showFirstlyShowFragment_YouVoted(){

        Book_Utility.changeFragmentTo(activity!!.supportFragmentManager,R.id.Book_marksPanelFragment, VotedBook(UserData.idUser,bookShowData.idBook))

        setNewFragmentAboutMark(BookFragmentTypeEnum.SHOW_YOUR_MARK_FRAGMENT)
    }


    private fun sendUserMarkOnServer(){
        Log.e("ocena","wysyłam na serwer nową ocenke")

            Fuel.post(resources.getString(R.string.MARK_BOOK_ADRESS), listOf("mark" to markUserView.rating,"opinion" to "","idUser" to UserData.idUser,"idBook" to bookShowData.idBook)).response { _, response, _ ->
                Log.e("ocena","Nowa ocenka wysłana")
             
            }


    }

    private fun addCallbackChangeOrSetMark(){

        descriptionActionMark.setOnClickListener{
            sendUserMarkOnServer()
            Toast.makeText(context, "Oceniono książkę na: ${markUserView.rating}", Toast.LENGTH_SHORT).show()
        }

        /*markUserView.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {

                if(howModificationRating>0) {
                    Toast.makeText(context, "Oceniono książkę na: $p1", Toast.LENGTH_SHORT).show()
                    sendUserMarkOnServer()
                }
                howModificationRating+=1
            }
        })  */
    }

    private fun addCallbackToBackArrowToMenu(){
        bookBackArrowView.setOnClickListener {
            Navigation.findNavController(view!!).navigate(R.id.navigation_home)
        }
    }

    private fun loadPhoto(){
        Picasso.with(context)
            .load(getContext()!!.resources.getString(R.string.GIVE_IMAGE_BOOK_ADRESS) + bookShowData.idBook)
            .placeholder(R.drawable.database_icon)
            .resize(230, 350)
            .centerCrop()
            .into(bookPhotoView)

    }

    private fun fillLayoutBookData(){

        TextUtility.calculateNameBookSize(bookTitleView,bookShowData.titleBook,55f)
        bookAuthorNameView.text=bookShowData.nameAuthor
        bookMarkView.text=bookShowData.markBook
        bookPageView.text="Liczba stron - "+bookShowData.pageBook
        bookYearView.text="Rok wydania - "+bookShowData.yearPublish
        bookDescriptionView.text=bookShowData.descriptionBook

        if(bookShowData.gentre_1!=" "){
            bookGentreView_1.text=bookShowData.gentre_1
        }else{
            bookGentreView_1.visibility=View.GONE
        }
        if(bookShowData.gentre_2!=" "){
            bookGentreView_2.text=bookShowData.gentre_2
        }else{
            bookGentreView_2.visibility=View.GONE
        }
        if(bookShowData.gentre_3!=" "){
            bookGentreView_3.text=bookShowData.gentre_3
        }else{
            bookGentreView_3.visibility=View.GONE
        }





        bookHowMarkView.text=TextUtility.getDescriptionHowManyMark(bookShowData.countMark)

        loadPhoto()

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


    private fun scrollViewToTop(){

        bookScrollView.fullScroll(ScrollView.FOCUS_UP)
        bookScrollView.smoothScrollTo(0,0)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeViewRefrence()
        setArrowBackAction()
        downloadBookFromServerWhichUserShowActually_Firstly()
        scrollViewToTop()
        setActionWhenUserClickShowScheduleBooks()
//        setActionWhenUserClickShowYouMark()
      //  showFirstlyShowFragment_YouVoted()
        fillLayoutBookData()
        setActionShowMarkAndGive()
        addActionToClickWriters()
        addCallbackChangeOrSetMark()




    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
