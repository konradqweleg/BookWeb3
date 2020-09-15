package project.legto.twojaksiazka3.ui.databaseBooks

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.ViewCompat.animate
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.legto.twojaksiazka3.R
import project.legto.twojaksiazka3.CategoryBooks
import project.legto.twojaksiazka3.FromWhereBookShow

import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ShowBookWithOneCategory.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ShowBookWithOneCategory.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowBookWithOneCategory : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var listBooks:ListView
    private lateinit var nameCategoryBooks:TextView
    private lateinit var returnBookListButton:ImageView
    private lateinit var filterSettings:ImageView
    private lateinit var filterPanel:com.google.android.material.navigation.NavigationView
    private lateinit var lowerValueMark:EditText
    private lateinit var biggerValueMark:EditText

    private lateinit var lowerValueYears:EditText
    private lateinit var biggerValueYears:EditText
    private lateinit var exitPanel:ImageView
private lateinit var filterAddToAdapter:Button
    private lateinit var nameAuthors:EditText

    val Int.dp: Int
        get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
//
    //
    //
    //
    //
    //
    //Nazwisko pisarza zle filtruje

    private fun initializeViewRefrence(){
        listBooks= view!!.findViewById(R.id.ShowBookOneCategory_ListBooks)
        nameCategoryBooks=view!!.findViewById(R.id.ShowBookOneCategory_nameCategoryBooks)
        returnBookListButton =view!!.findViewById(R.id.ShowBookOneCategory_returnCategoryBookButton)
        filterSettings=view!!.findViewById(R.id.ShowBookOneCategory_filterSettings)
        filterPanel=view!!.findViewById(R.id.ShowBookOneCategory_filterPanel)
        lowerValueMark=view!!.findViewById(R.id.ShowBookOneCategory_od)
        biggerValueMark=view!!.findViewById(R.id.ShowBookOneCategory_do)

        lowerValueYears=view!!.findViewById(R.id.ShowBookOneCategory_fromYearsBookCreate)
        biggerValueYears=view!!.findViewById(R.id.ShowBookOneCategory_EndYearsBookCreate)
        exitPanel=view!!.findViewById(R.id.ShowBookOneCategory_exitFilterPanel)
        filterAddToAdapter=view!!.findViewById(R.id.ShowBookOneCategory_addFilterToAdapter)
        nameAuthors=view!!.findViewById(R.id.ShowBookOneCategory_authorsName)
    }

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
        return inflater.inflate(R.layout.fragment_show_book_with_one_category, container, false)
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




    private fun setAdapterToListBooksWithOneCategory(){
        val adapterBookWithOneCategory = AdapterShowBookWithOneCategoryWithFilter(
            activity as Activity
        )

        var nameFull= mutableListOf<String>("","")


        if(nameAuthorsC.toString().split(" ").size==2) {
            nameFull[0] = nameAuthorsC.toString().split(" ")[0]
            nameFull[1] = nameAuthorsC.toString().split(" ")[1]

        }else if(nameAuthorsC.toString().split(" ").size==1){
            nameFull[1] = nameAuthorsC.toString().split(" ")[0]
        }
        Log.e("Filtry","autor:"+nameFull[0]+"/"+nameFull[1])

        adapterBookWithOneCategory.setFilter(lowerValueMarkviewC,
            biggerValueMarkC, lowerValueYearsC, biggerValueYearsC,nameFull[0],nameFull[1])
        listBooks.adapter=adapterBookWithOneCategory
    }

    private fun addCallbackToClickListBookElem(){
        listBooks.setOnItemClickListener { parent, view, position, id ->



            if(lowerValueMark.text.toString().length<1){
                ShowBookWithOneCategory.lowerValueMarkviewC="0"
            }else{
                ShowBookWithOneCategory.lowerValueMarkviewC=lowerValueMark.text.toString()
            }


            if(biggerValueMark.text.toString().length<1){
                biggerValueMarkC="10"
            }else{
                biggerValueMarkC=biggerValueMark.text.toString()
            }


            if(lowerValueYears.text.toString().length<1){
                lowerValueYearsC="1400"
            }else{
                lowerValueYearsC=lowerValueYears.text.toString()
            }

            if(biggerValueYears.text.toString().length<1){
                biggerValueYearsC="10"
            }else{
                biggerValueYearsC=biggerValueYears.text.toString()
            }


            if(nameAuthors.text.toString().length<1){
                nameAuthorsC=""
            }else{
                nameAuthorsC=nameAuthors.text.toString()
            }

            FromWhereBookShow.setFromWherIMustShowBook(FromWhereBookShow.From.LIST_CATEGORY_BOOK,position)
            Navigation.findNavController(view!!).navigate(R.id.bookFragment)

        }
    }


    private fun addCallbackToReturnToListCategory(){
        returnBookListButton.setOnClickListener {

            Navigation.findNavController(view!!).navigate(R.id.navigation_dashboard)

        }

    }

    private fun setNameCategoryInView(){
        nameCategoryBooks.text= CategoryBooks.CategoryChoice.nameBookCategory

    }


    private fun addCallbackExitFilterPanel(){
        exitPanel.setOnClickListener {
            filterPanel.apply {
                // Set the content view to 0% opacity but visible, so that it is visible
                // (but fully transparent) during the animation.
                alpha = 1f
                visibility = View.VISIBLE

                // Animate the content view to 100% opacity, and clear any animation
                // listener set on the view.
                animate()

                    .setDuration(300)
                    .translationX((330.px.toFloat()))
                    .setListener(null)
            }
        }
    }

    private fun addCallbackClickFilterSettings(){
        filterSettings.setOnClickListener {



            filterPanel.apply {
                // Set the content view to 0% opacity but visible, so that it is visible
                // (but fully transparent) during the animation.
                alpha = 1f
                visibility = View.VISIBLE

                // Animate the content view to 100% opacity, and clear any animation
                // listener set on the view.
                animate()

                    .setDuration(300)
                    .translationX((1.px.toFloat()))
                    .setListener(null)
            }
           // showFilterPanel()
         //   Navigation.findNavController(view!!).navigate(R.id.FilterBookSettings)
          /*  val extras = FragmentNavigatorExtras(
              )
            view!!.findNavController().navigate(R.id.confirmationAction,
                null, // Bundle of args
                null, // NavOptions
                extras) */
         //   Navigation.findNavController(view!!).navigate(R.id.confirmationAction)

        }

    }



    private fun showFilterPanel(){
        filterPanel.visibility=View.VISIBLE
    }

    private fun hideFilterPanel(){
        filterPanel.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
          //  animate()

             //   .setDuration(3)
             //   .setListener(null)
        }
        filterPanel.visibility=View.GONE
    }

    private fun hideFilterPanelWithoutAnimation(){
        filterPanel.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
              animate()
                  .setDuration(3)
                  .translationX(330.px.toFloat())
              .setListener(null)
        }
        filterPanel.visibility=View.GONE
    }



    private fun setCallbackToYearsCreateBookLowerAndBigger(){

        lowerValueYears.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                try {
                    try {
                        if (lowerValueYears.text.toString().toInt() > 2020) {
                            lowerValueYears.setText("2020")
                        }

                    }catch(e:Exception){

                    }

                    try{
                        if(lowerValueYears.text.toString().toInt()<0){
                            lowerValueYears.setText("1440")
                        }

                    }catch(e:Exception){

                    }

                    try{
                        if(lowerValueYears.text.toString().toInt()>biggerValueYears.text.toString().toInt()){
                            lowerValueYears.setText((biggerValueYears.text.toString().toInt()).toString())
                        }

                    }catch(e:Exception){

                    }


                } catch (e: NumberFormatException) {

                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })





        biggerValueYears.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                try {
                    if(biggerValueYears.text.toString().toInt()>2020){
                        biggerValueYears.setText("2020")
                    }

                } catch (e: NumberFormatException) {

                }

                try {

                    if (biggerValueYears.text.toString().toInt() <0) {
                        biggerValueYears.setText("1440")
                    }


                }catch (e:Exception){

                }

                try {
                    if (biggerValueYears.text.toString().toInt() < lowerValueYears.text.toString()
                            .toInt()
                    ) {
                        biggerValueYears.setText((lowerValueYears.text.toString().toInt()).toString())
                    }

                }catch(e:Exception){

                }


            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

    }

    private fun setCallbackFilterMarkLowerAndBigger(){



        lowerValueMark.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                try {
                    try {
                        if (lowerValueMark.text.toString().toInt() > 10) {
                            lowerValueMark.setText("10")
                        }

                    }catch(e:Exception){

                    }

                    try{
                    if(lowerValueMark.text.toString().toInt()<0){
                        lowerValueMark.setText("0")
                    }

                    }catch(e:Exception){

                    }

                    try{
                    if(lowerValueMark.text.toString().toInt()>biggerValueMark.text.toString().toInt()){
                        lowerValueMark.setText((biggerValueMark.text.toString().toInt()).toString())
                    }

                    }catch(e:Exception){

                    }


                } catch (e: NumberFormatException) {

                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })





        biggerValueMark.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                try {
                    if(biggerValueMark.text.toString().toInt()>10){
                        lowerValueMark.setText("10")
                    }

                } catch (e: NumberFormatException) {

                }

                try {

                    if (biggerValueMark.text.toString().toInt() < 0) {
                        lowerValueMark.setText("0")
                    }


                }catch (e:Exception){

                }

                try {
                    if (biggerValueMark.text.toString().toInt() < lowerValueMark.text.toString()
                            .toInt()
                    ) {
                        biggerValueMark.setText((lowerValueMark.text.toString().toInt()).toString())
                    }

                }catch(e:Exception){

                }


            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

    }


    private fun addCallbackToFilterAdaptewr(){
        filterAddToAdapter.setOnClickListener {

            val adapterBookWithOneCategory = AdapterShowBookWithOneCategoryWithFilter(
                activity as Activity
            )

var nameFull= mutableListOf<String>("","")


            if(nameAuthors.text.toString().split(" ").size==2) {
                nameFull[0] = nameAuthors.text.toString().split(" ")[0]
                nameFull[1] = nameAuthors.text.toString().split(" ")[1]

            }else if(nameAuthors.text.toString().split(" ").size==1){
                nameFull[1] = nameAuthors.text.toString().split(" ")[0]
            }
            //Filtruj na żywo wpisywane dane a nie tak jak tu po
            Log.e("Filtry","->"+lowerValueMark.text.toString().length)
            if(lowerValueMark.text.toString().length<1){
                ShowBookWithOneCategory.lowerValueMarkviewC="0"
            }else{
                ShowBookWithOneCategory.lowerValueMarkviewC=lowerValueMark.text.toString()
            }


            if(biggerValueMark.text.toString().length<1){
                biggerValueMarkC="10"
            }else{
                biggerValueMarkC=biggerValueMark.text.toString()
            }


            if(lowerValueYears.text.toString().length<1){
                lowerValueYearsC="1400"
            }else{
                lowerValueYearsC=lowerValueYears.text.toString()
            }

            if(biggerValueYears.text.toString().length<1){
                biggerValueYearsC="10"
            }else{
                biggerValueYearsC=biggerValueYears.text.toString()
            }


            if(nameAuthors.text.toString().length<1){
                nameAuthorsC=""
            }else{
                nameAuthorsC=nameAuthors.text.toString()
            }




            adapterBookWithOneCategory.setFilter(lowerValueMarkviewC ,biggerValueMarkC,lowerValueYearsC,biggerValueYearsC,nameFull[0],nameFull[1])

            listBooks.adapter=adapterBookWithOneCategory


            filterPanel.apply {
                // Set the content view to 0% opacity but visible, so that it is visible
                // (but fully transparent) during the animation.
                alpha = 1f
                visibility = View.VISIBLE

                // Animate the content view to 100% opacity, and clear any animation
                // listener set on the view.
                animate()

                    .setDuration(300)
                    .translationX((330.px.toFloat()))
                    .setListener(null)
            }

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewRefrence()
        setAdapterToListBooksWithOneCategory()
        addCallbackToClickListBookElem()
        addCallbackToReturnToListCategory()
        setNameCategoryInView()
        addCallbackClickFilterSettings()
        hideFilterPanelWithoutAnimation()
        setCallbackFilterMarkLowerAndBigger()
        setCallbackToYearsCreateBookLowerAndBigger()
        addCallbackExitFilterPanel()
        addCallbackToFilterAdaptewr()
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

    var   lowerValueMarkviewC="0"
      var  biggerValueMarkC="10"

        var    lowerValueYearsC="1400"
        var    biggerValueYearsC="2020"


        var    nameAuthorsC=""
        var    nameAuthorsC2=""



        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShowBookWithOneCategory.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowBookWithOneCategory().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
