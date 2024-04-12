package com.example.binlisttest.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binlisttest.domain.api.ApiService
import com.example.binlisttest.domain.model.BinlistData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sharedPrefUtils: SharedPrefUtils,
) : ViewModel() {
    private val _dataCard = MutableLiveData<BinlistData>()
    val dataCard: LiveData<BinlistData> = _dataCard

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        _state.value = false
        _error.value = null
    }
    fun getData(number: String) {
        try {
            _isLoading.value = true
            viewModelScope.launch {
                val response = apiService.getProducts(number)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _dataCard.postValue(it)
                    }
                    _state.postValue(true)
                    _isLoading.value = false
                } else if (response.code() == 404) {
                    _error.postValue("Error Ошибка 404: Банковская карта не найдена")
                    _isLoading.value = false

                } else if (response.code() == 429) {
                    _error.value = "Error Превышен допуск 5 запросов в час"
                    _isLoading.value = false
                }
            }

        } catch (e: Exception) {
            e.message?.let { Log.d("Error", it) }
            _isLoading.value = false
        }
    }

    fun getCard(): BinlistData? {
        return dataCard.value
    }

    fun setStatus(s: Boolean){
        _state.value = s
    }

    fun setError(){
        _error.value = null
    }

    fun getArchive(): List<BinlistData> {
        return sharedPrefUtils.loadList()
    }

    fun saveCard(card: BinlistData) {
        sharedPrefUtils.saveCard(card)
    }
}