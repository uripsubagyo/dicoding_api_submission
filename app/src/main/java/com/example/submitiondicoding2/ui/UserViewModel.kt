package com.example.submitiondicoding2.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submitiondicoding2.data.response.PersonUser
import com.example.submitiondicoding2.data.response.ResponseUser
import com.example.submitiondicoding2.data.retrofit.ApiConfig
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class UserViewModel : ViewModel() {

    private var _initList: MutableLiveData<List<PersonUser?>?> = MutableLiveData()

    val listItems: LiveData<List<PersonUser?>?> = _initList

    fun getDataNew(newData:String = "ALL"){
        val client = ApiConfig.getApiService().getUser(newData)

        client.enqueue(object : Callback<ResponseUser>{
            override fun onResponse(
                call: Call<ResponseUser>,
                response: Response<ResponseUser>
            ){

                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                            _initList.value = responseBody.items
                    }
                } else{
                    Log.e("UserViewModel", "onFailure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Log.e("UserViewModel", "onFailure: ${t.message}")
            }
        })
    }


}