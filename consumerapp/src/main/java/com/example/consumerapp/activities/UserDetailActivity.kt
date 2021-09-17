package com.example.consumerapp.activities

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.consumerapp.R
import com.example.consumerapp.adapter.SectionsPagerAdapter
import com.example.consumerapp.database.DatabaseContract
import com.example.consumerapp.database.DatabaseContract.FavoriteUserColumns.Companion.CONTENT_URI
import com.example.consumerapp.databinding.ActivityUserDetailBinding
import com.example.consumerapp.models.User
import com.example.consumerapp.models.UserDetail
import com.example.consumerapp.viewmodel.UserViewModel
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity(){

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var user: User
    private var favStatus = false
    private lateinit var uriWithId: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.bringToFront()
        user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        setupActionBar(user)

        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)
        userViewModel.setDetailUser(user.login, this)
        userViewModel.getDetailUser().observe(this, { user ->
            if (user != null) {
                setData(user)
                binding.progressBar.visibility = View.GONE
                binding.fabFavorite.setOnClickListener{
                    if(!favStatus) addFavoriteUser(user)
                    else deleteFavoriteUser(user.login)
                    favStatus = !favStatus
                    setFavoriteStatus(favStatus)
                }
            }
        })

        setViewPager()
        checkFavorite(user.login)
    }

    private fun setupActionBar(user: User) {
        setSupportActionBar(binding.toolbarUserDetail)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
        binding.collapsingToolbarLayout.title = user.login

        binding.toolbarUserDetail.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setData(user: UserDetail) {
        with(binding) {
            Glide.with(this@UserDetailActivity)
                .load(user.avatarUrl)
                .into(ivUser)
            tvName.text = user.name
            tvCompany.text = user.company
            tvLocation.text = user.location
            tvRepositoryCount.text = user.publicRepository.toString()
            tvFollowersCount.text = user.followers.toString()
            tvFollowingCount.text = user.following.toString()
        }
    }

    private fun setViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, user)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun addFavoriteUser(user: UserDetail) {
        val values = ContentValues().apply {
            put(DatabaseContract.FavoriteUserColumns.USERNAME, user.login)
            put(DatabaseContract.FavoriteUserColumns.URL, user.htmlUrl)
            put(DatabaseContract.FavoriteUserColumns.AVATAR, user.avatarUrl)
        }
        contentResolver.insert(CONTENT_URI, values)
        Toast.makeText(this, getString(R.string.added_to_favorite, user.login), Toast.LENGTH_SHORT).show()
    }

    private fun deleteFavoriteUser(username: String) {
        uriWithId = Uri.parse("$CONTENT_URI/$username")
        contentResolver.delete(uriWithId, null, null)
        Toast.makeText(this, getString(R.string.removed_from_favorite, user.login), Toast.LENGTH_SHORT).show()
    }

    private fun checkFavorite(username: String) {
        uriWithId = Uri.parse("$CONTENT_URI/$username")
        val cursor = contentResolver.query(uriWithId, null, username, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                favStatus = true
                setFavoriteStatus(favStatus)
            }
            cursor.close()
        }
    }

    private fun setFavoriteStatus(favStatus: Boolean) {
        if(favStatus) binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        else binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}