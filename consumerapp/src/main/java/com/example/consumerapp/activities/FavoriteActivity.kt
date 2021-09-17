package com.example.consumerapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.R
import com.example.consumerapp.adapter.FavoriteUserAdapter
import com.example.consumerapp.database.DatabaseContract.FavoriteUserColumns.Companion.CONTENT_URI
import com.example.consumerapp.databinding.ActivityFavoriteBinding
import com.example.consumerapp.models.User
import com.example.consumerapp.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var listFavoriteAdapter: FavoriteUserAdapter
    private lateinit var favoriteUserViewModel: FavoriteViewModel
    private lateinit var uriWithId: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        binding.progressBar.visibility = View.VISIBLE
        binding.progressBar.bringToFront()
        showRecyclerView()

        favoriteUserViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FavoriteViewModel::class.java)

        favoriteUserViewModel.setFavoriteUsers(applicationContext)
        favoriteUserViewModel.getFavoriteUsers().observe(this, { users ->
            if (users.isNotEmpty()) {
                binding.tvNoFavoriteUser.visibility = View.GONE
                binding.ivEmptyFavoriteUser.visibility = View.GONE
                binding.rvFavorite.visibility = View.VISIBLE
                listFavoriteAdapter.setData(users)
            } else {
                binding.tvNoFavoriteUser.visibility = View.VISIBLE
                binding.ivEmptyFavoriteUser.visibility = View.VISIBLE
            }
            binding.progressBar.visibility = View.GONE
        })
    }

    private fun showRecyclerView() {
        listFavoriteAdapter = FavoriteUserAdapter()
        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = listFavoriteAdapter
        }
        listFavoriteAdapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_USER, data)
                startActivity(intent)
            }
            override fun onRemoveClicked(data: User, position: Int) {
                uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + data.login)
                contentResolver.delete(uriWithId, null, null)
                listFavoriteAdapter.removeItem(position)
                Toast.makeText(this@FavoriteActivity, getString(R.string.removed_from_favorite, data.login), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarFavoriteUser)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = resources.getString(R.string.favorite_user)
        }
        binding.toolbarFavoriteUser.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}