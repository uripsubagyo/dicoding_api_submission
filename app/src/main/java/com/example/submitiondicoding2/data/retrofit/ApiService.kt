package com.example.submitiondicoding2.data.retrofit
import com.example.submitiondicoding2.data.response.DetailUser
import com.example.submitiondicoding2.data.response.GetFollowItem
import com.example.submitiondicoding2.data.response.ResponseUser
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") q:String
    ): Call<ResponseUser>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<GetFollowItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<GetFollowItem>>
}