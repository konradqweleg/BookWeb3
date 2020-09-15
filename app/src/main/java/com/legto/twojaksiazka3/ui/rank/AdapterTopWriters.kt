package project.legto.twojaksiazka3.ui.rank

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.legto.twojaksiazka3.R
import com.squareup.picasso.Picasso

class AdapterTopWriters(private val context: Activity)
    : ArrayAdapter<String>(context,
    R.layout.single_writers_in_rank) {



    val WIDTH_IMAGE=230
    val HEIGHT_IAMGE=350


    private var writersMap=mutableMapOf<Int, authors>()
    private var imageMap=mutableMapOf<Int, ImageView>()





    private fun saveDataWritersToMap(book: authors, position: Int){
        writersMap[position] = book
    }

    @ExperimentalStdlibApi
    override fun getCount(): Int {
        return 100
    }

    private fun fillZeroMarkEnds(writersData: authors, markBookView: TextView){
        when ((""+writersData.markAuthors).length) {
            1 -> {
                markBookView.text = ""+writersData.markAuthors + ".00"

            }
            2 -> {
                markBookView.text = "10.00"

            }
            3 -> {


                markBookView.text = ""+writersData.markAuthors + "0"
            }
            else -> {
                markBookView.text = ""+writersData.markAuthors
            }
        }

    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.single_writers_in_rank, null, true)


        val nameAuthors = rowView.findViewById(R.id.SingleWritersRank_nameAuthors) as TextView
        val markAuthors = rowView.findViewById(R.id.SingleWritersRank_markAuthors) as TextView
        val imageAuthors: ImageView =rowView.findViewById(R.id.SingleWritersRank_photoAuthors) as ImageView
        val positionRankWriter:TextView=rowView.findViewById(R.id.SingleWriterRank_positionRank) as TextView

        positionRankWriter.text=""+(position+1)

        var writersData: authors

        if(writersMap.containsKey(position)){

            writersData=writersMap[position]!!

            imageAuthors.setImageDrawable(imageMap[position]!!.drawable)
            //TextUtility.calculateNameBookSize(titleText, bookData.titleBook, 35f)
            nameAuthors.text = writersData.name+" "+writersData.surname

            fillZeroMarkEnds(writersData,markAuthors)


        } else {

            Fuel.get(
                context.getString(R.string.GET_DATA_RANK_WRITERS_ADRESS),
                listOf(
                    "position" to position
                )
            ).response { _, _, result ->
                result.fold(
                    success = {
                        val writersData =
                            RankAuthorsMapper().deserialize(
                                it
                            )

                        saveDataWritersToMap(writersData, position)
                        var imageToSaveOnTemp= ImageView(context)
                        Picasso.with(context)
                            .load(getContext().resources.getString(R.string.GET_WRITERS_IMAGE) + writersData.idWriters)
                            .placeholder(R.drawable.database_icon)
                            .resize(WIDTH_IMAGE, HEIGHT_IAMGE)
                            .centerCrop()
                            .into(imageToSaveOnTemp)
                        imageMap[position] = imageToSaveOnTemp




                     //   TextUtility.calculateNameBookSize(titleText, bookData.titleBook, 35f)
                       nameAuthors.text = writersData.name+" "+writersData.surname
                        //authorBook.text = bookData.nameAuthor
                        fillZeroMarkEnds(writersData,markAuthors)





                        Picasso.with(context)
                            .load(getContext().resources.getString(R.string.GET_WRITERS_IMAGE) + writersData.idWriters)
                            .placeholder(R.drawable.database_icon)
                            .resize(230, 350)
                            .centerCrop()
                            .into(imageAuthors)

                    },
                    failure = { error ->

                    }
                )
            }
        }



        return rowView







    }

}