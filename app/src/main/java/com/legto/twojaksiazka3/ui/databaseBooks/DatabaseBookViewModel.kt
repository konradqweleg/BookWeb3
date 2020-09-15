package project.legto.twojaksiazka3.ui.databaseBooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DatabaseBookViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Ranking"
    }
    val text: LiveData<String> = _text
}