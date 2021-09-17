package com.example.consumerapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.consumerapp.fragment.FollowersFragment
import com.example.consumerapp.fragment.FollowingFragment
import com.example.consumerapp.models.User

class SectionsPagerAdapter(activity: AppCompatActivity, private var user: User) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        val mBundle = Bundle()
        mBundle.putParcelable(EXTRA_USER, user)
        when (position) {
            0 -> {
                fragment = FollowersFragment()
                fragment.arguments = mBundle
            }
            1 -> {
                fragment = FollowingFragment()
                fragment.arguments = mBundle
            }
        }
        return fragment as Fragment

    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}