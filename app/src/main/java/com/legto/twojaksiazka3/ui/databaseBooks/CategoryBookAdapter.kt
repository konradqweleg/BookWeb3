package project.legto.twojaksiazka3.ui.databaseBooks

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.legto.twojaksiazka3.R


class CategoryBookAdapter(private val context: Activity)
    : ArrayAdapter<String>(context,
    R.layout.onecategorybook) {


    val CATEGORY_BOOKS= listOf<String>("Horror","Thriller","Fantastyka","Kryminał","Dramat","Akcja","Science Fiction","Romans","Polska","Wszystkie")
    val IMAGE_CATEGORY_BOOKS= listOf<Int>(R.drawable.horror,R.drawable.thriller,R.drawable.fantastyka,R.drawable.kryminal,R.drawable.dramat,R.drawable.akcja,R.drawable.sciencefiction,R.drawable.romans,R.drawable.polska,R.drawable.wszystkie)
    val THE_BEST_POPULAR_AUTHORS_GENTRE= listOf<String>("King Lovecraft Simmons","Paris Coben Michaelides","Martin Tolkien Sapkowski","Läckberg Nesbø Horst","Mrożek Mickiewicz Shakespeare","Larsson Puzzo Harris","Watts Liu Miller","Reilly Wolf Keeland","Mróz Bonda Tokarczuk","Wszystkie książki")


    override fun getCount(): Int {
        return super.getCount()+10
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.onecategorybook, null, true)

        val categoryName=rowView.findViewById<TextView>(R.id.OneCategory_Name)
        categoryName.text = CATEGORY_BOOKS[position]

        val categoryImage=rowView.findViewById<ImageView>(R.id.OneCategory_image)
        categoryImage.setImageDrawable(context.resources.getDrawable(IMAGE_CATEGORY_BOOKS[position]))

      //  var popularCategoryAuthors:TextView=rowView.findViewById(R.id.OneCategory_PopularAuthor)
     //  popularCategoryAuthors.text = THE_BEST_POPULAR_AUTHORS_GENTRE[position]




        return rowView
    }

}