package com.example.consumerapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.adapter.FollowAdapter
import com.example.consumerapp.databinding.FragmentFollowingBinding
import com.example.consumerapp.models.User
import com.example.consumerapp.viewmodel.UserViewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding as FragmentFollowingBinding
    private lateinit var listUserAdapter: FollowAdapter
    private lateinit var userViewModel: UserViewModel
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.bringToFront()
        user = arguments?.getParcelable<User>(EXTRA_USER) as User
        showRecyclerView()
        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserViewModel::class.java
        )
        userViewModel.setFollowingUser(user.login, requireContext())
        userViewModel.getFollowingUser().observe(viewLifecycleOwner, { following ->
            if (following.isNotEmpty()) {
                binding.tvNoFollowing.visibility = View.GONE
                listUserAdapter.setData(following)
            } else binding.tvNoFollowing.visibility = View.VISIBLE
            showLoading(false)
        })
    }

    private fun showRecyclerView() {
        listUserAdapter = FollowAdapter()
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.adapter = listUserAdapter
        listUserAdapter.notifyDataSetChanged()
        binding.rvFollowing.setHasFixedSize(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}