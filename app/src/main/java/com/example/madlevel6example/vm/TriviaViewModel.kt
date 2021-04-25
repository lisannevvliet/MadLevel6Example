package com.example.madlevel6example.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madlevel6example.repository.TriviaRepository
import kotlinx.coroutines.launch

class TriviaViewModel(application: Application) : AndroidViewModel(application) {
    private val triviaRepository = TriviaRepository()

    // This property points direct to the LiveData in the repository, that value get's updated when user clicks FAB. This happens through the refreshNumber() in this class.
    val trivia = triviaRepository.trivia

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    // Expose non MutableLiveData via getter. errorText can be observed from Activity for error showing. Encapsulation.
    val errorText: LiveData<String>
        get() = _errorText

    // The viewModelScope is bound to Dispatchers.Main and will automatically be cancelled when the ViewModel is cleared. Extension method of lifecycle-viewmodel-ktx library.
    fun getTriviaNumber() {
        viewModelScope.launch {
            try {
                // The triviaRepository sets it's own LiveData property. Our own trivia property points to this one.
                triviaRepository.getRandomNumberTrivia()
            } catch (error: TriviaRepository.TriviaRefreshError) {
                _errorText.value = error.message
                Log.e("Triva error", error.cause.toString())
            }
        }
    }
}