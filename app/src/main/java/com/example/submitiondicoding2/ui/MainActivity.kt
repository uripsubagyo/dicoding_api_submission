package com.example.submitiondicoding2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submitiondicoding2.data.response.PersonUser
import com.example.submitiondicoding2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this,layoutManager.orientation)
        binding.rvUserList.addItemDecoration(itemDecoration)

        showLoading(true)

        viewModel.getDataNew()

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    if(searchView.text.toString() == ""){
                        viewModel.getDataNew()
                    } else {
                        viewModel.getDataNew(searchView.text.toString())
                    }
                    false
                }
        }

        viewModel.listItems.observe(this@MainActivity) {
            resultView(it)
        }
    }

    private fun resultView(userList: List<PersonUser?>?){
        if(userList?.count() == 0){
            showLoading(false)
            binding.resultType.visibility = View.VISIBLE
        } else{
            binding.resultType.visibility = View.GONE

            val adapter = UserListAdapter()
            adapter.submitList(userList)
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