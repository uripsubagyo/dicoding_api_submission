package com.example.submitiondicoding2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submitiondicoding2.data.response.GetFollowItem
import com.example.submitiondicoding2.data.retrofit.ApiConfig
import com.example.submitiondicoding2.databinding.FragmentFollowersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragment : Fragment() {

    var _initDataFollowers: MutableLiveData<List<GetFollowItem?>?> = MutableLiveData()

    val dataFollowers: LiveData<List<GetFollowItem?>?> = _initDataFollowers

    private lateinit var binding: FragmentFollowersBinding

    companion object {
        const val USERNAME  = "USERNAME"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUserList.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvUserList.addItemDecoration(itemDecoration)

        if(arguments != null){
            getFollowersData(arguments?.getString(USERNAME).toString())

            dataFollowers.observe(viewLifecycleOwner){
                resultView(it)
            }
        }
    }


    private fun getFollowersData(username: String){
        val client = ApiConfig.getApiService().getFollowers(username)

        client.enqueue(object : Callback<List<GetFollowItem>> {
            override fun onResponse(
                call: Call<List<GetFollowItem>>,
                response: Response<List<GetFollowItem>>
            ){

                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _initDataFollowers.value = responseBody
                    }
                } else{
                    Log.e("ERROR_FOLLOWERS", "onFailure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GetFollowItem>>, t: Throwable) {
                Log.e("ERROR_FOLLOWERS", "onFailure: ${t.message}")
            }
        })
    }


    private fun resultView(listItems: List<GetFollowItem?>?){
        if(listItems?.count() == 0){
            binding.textInformation.setText("Tidak memiliki followers")
        } else{
            binding.textInformation.visibility = View.GONE


            val adapter = UserListFollowAdapter()
            adapter.submitList(listItems)

            binding.rvUserList.adapter = adapter
        }
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}