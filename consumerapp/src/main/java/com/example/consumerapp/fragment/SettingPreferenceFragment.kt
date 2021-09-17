package com.example.consumerapp.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.example.consumerapp.R
import com.example.consumerapp.alarm.AlarmReceiver
import java.util.*

class SettingPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var reminder: String
    private lateinit var language: String

    private lateinit var reminderPreferences: SwitchPreference
    private lateinit var languagePreference: Preference

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        alarmReceiver = AlarmReceiver()
        init()
        setSummaries()

        languagePreference.setOnPreferenceClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            true
        }
    }

    private fun init() {
        reminder = getString(R.string.key_reminder)
        language = resources.getString(R.string.key_language)

        reminderPreferences = findPreference<SwitchPreference>(reminder) as SwitchPreference
        languagePreference = findPreference<Preference>(language) as Preference
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        reminderPreferences.isChecked = sh.getBoolean(reminder, false)
        val currentLanguage = Locale.getDefault().displayLanguage
        languagePreference.summary = currentLanguage
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key == reminder){
            if (sharedPreferences != null)
                reminderPreferences.isChecked = sharedPreferences.getBoolean(reminder, false)
        }

        val state : Boolean = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(reminder, false)
        setReminder(state)
    }

    private fun setReminder(state: Boolean) {
        if (state) context?.let { alarmReceiver.setRepeatingAlarm(it) }
        else context?.let { alarmReceiver.cancelAlarm(it) }
    }


}