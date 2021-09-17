package com.example.githubuser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FollowAdapter
import com.example.githubuser.databinding.FragmentFollowersBinding
import com.example.githubuser.models.User
import com.example.githubuser.viewmodel.UserViewModel

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding as FragmentFollowersBinding
    private lateinit var listUserAdapter: FollowAdapter
    private lateinit var userViewModel: UserViewModel
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.bringToFront()
        user = arguments?.getParcelable<User>(EXTRA_USER) as User
        showRecyclerView()
        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)
        userViewModel.setFollowersUser(user.login, requireContext())
        userViewModel.getFollowersUser().observe(viewLifecycleOwner, { followers ->
            if (followers.isNotEmpty()) {
                binding.tvNoFollower.visibility = View.GONE
                listUserAdapter.setData(followers)
            } else binding.tvNoFollower.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        })
    }

    private fun showRecyclerView() {
        listUserAdapter = FollowAdapter()
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.adapter = listUserAdapter
        listUserAdapter.notifyDataSetChanged()
        binding.rvFollowers.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}