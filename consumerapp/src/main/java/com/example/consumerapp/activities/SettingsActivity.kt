package com.example.consumerapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.consumerapp.R
import com.example.consumerapp.databinding.ActivitySettingsBinding
import com.example.consumerapp.fragment.SettingPreferenceFragment

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