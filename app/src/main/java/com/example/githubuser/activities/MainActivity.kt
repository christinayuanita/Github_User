package com.example.githubuser.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.models.User
import com.example.githubuser.viewmodel.UserViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listUserAdapter: UserAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.bringToFront()
        showRecyclerView()
        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)
        binding.svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isNotEmpty()!!) {
                    binding.rvUsers.visibility = View.VISIBLE
                    userViewModel.setUsers(newText, this@MainActivity)
                    binding.ivHomeSearch.visibility = View.GONE
                    showLoading(true)
                } else {
                    binding.rvUsers.visibility = View.GONE
                    binding.ivHomeSearch.visibility = View.VISIBLE
                    binding.ivUserNotFound.visibility = View.GONE
                    showLoading(false)
                }
                return true
            }
        })

        userViewModel.getUsers().observe(this, { users ->
            if (users.isNotEmpty()) {
                showUserFound(true)
                listUserAdapter.setData(users)
            } else showUserFound(false)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_change_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.action_view_favorite_users -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showRecyclerView() {
        listUserAdapter = UserAdapter()
        with(binding.rvUsers) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listUserAdapter
        }
        listUserAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_USER, data)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    private fun showUserFound(state: Boolean) {
        if (state) {
            binding.ivUserNotFound.visibility = View.GONE
            binding.rvUsers.visibility = View.VISIBLE
        } else {
            binding.ivUserNotFound.visibility = View.VISIBLE
            binding.rvUsers.visibility = View.GONE
        }
        showLoading(false)
    }

}