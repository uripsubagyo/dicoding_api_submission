package com.example.submitiondicoding2.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submitiondicoding2.R
import com.example.submitiondicoding2.data.response.DetailUser
import com.example.submitiondicoding2.data.retrofit.ApiConfig
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUser : AppCompatActivity() {

    private var _initDataDetail: MutableLiveData<DetailUser> = MutableLiveData()

    val detailData: LiveData<DetailUser> = _initDataDetail


    companion object {
        const val USERNAME = "id"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following_text,
            R.string.follower_text
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        sectionsPagerAdapter.USERNAME = intent.getStringExtra(USERNAME).toString()

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        callDetailUser(intent.getStringExtra(USERNAME).toString())

        detailData.observe(this, Observer { detailUser ->
            val progresBar: ProgressBar = findViewById(R.id.load)
            progresBar.visibility = View.GONE

            val textUserName : TextView  = findViewById(R.id.username)
            textUserName.setText(detailData.value?.login)
            textUserName.visibility = View.VISIBLE

            val textFullName : TextView = findViewById(R.id.full_name)
            textFullName.setText(detailData.value?.name)
            textFullName.visibility = View.VISIBLE

            val avatarView : ImageView = findViewById(R.id.imageView)
            avatarView.visibility =  View.VISIBLE
            Glide.with(this).load(detailData.value?.avatarUrl).into(avatarView)

            val textFollowing : TextView = findViewById(R.id.Following)
            textFollowing.setText("${detailData.value?.following} Following")
            textFollowing.visibility = View.VISIBLE

            val textFollowers : TextView = findViewById(R.id.Followers)
            textFollowers.setText("${detailData.value?.followers} Followers")
            textFollowers.visibility = View.VISIBLE


//            val tabLayout: ConstraintLayout = findViewById(R.id.tabs)
//
//            val constraintSet = ConstraintSet()
//
//            constraintSet.clone(tabLayout)
//
//            constraintSet.connect(R.id.Followers, ConstraintSet.BOTTOM, R.id.Followers, ConstraintSet.BOTTOM,10)
//
//            constraintSet.applyTo(tabLayout)

        })


    }

    private fun callDetailUser(username: String){
        val client = ApiConfig.getApiService().getDetailUser(username)

        client.enqueue(object : Callback<DetailUser> {
            override fun onResponse(
                call: Call<DetailUser>,
                response: Response<DetailUser>
            ){

                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _initDataDetail.value = responseBody.copy()
                    }
                } else{
                    Log.e("UserViewModel", "onFailure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                Log.e("UserViewModel", "onFailure: ${t.message}")
            }
        })
    }


}