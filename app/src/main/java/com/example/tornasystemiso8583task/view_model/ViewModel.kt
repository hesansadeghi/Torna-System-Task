package com.example.tornasystemiso8583task.view_model


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.tornasystemiso8583task.db.ISO8583Entity
import com.example.tornasystemiso8583task.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

     val gelAllIsoMessage = repository.getAllIso8583().asLiveData()


     fun insertIOS8583(entity: ISO8583Entity) = viewModelScope.launch {

          repository.insertIso8583(entity)
     }


}