package com.example.githubuser.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivitySettingsBinding
import com.example.githubuser.fragment.SettingPreferenceFragment

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        supportFragmentManager.beginTransaction().add(R.id.setting_holder, SettingPreferenceFragment()).commit()
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            title = getString(R.string.settings)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}