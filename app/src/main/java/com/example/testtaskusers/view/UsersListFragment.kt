package com.example.testtaskusers.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testtaskusers.R
import com.example.testtaskusers.viewmodel.UserViewModel

class UsersListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_users_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = UserAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val onClick = View.OnClickListener {
            val position = recyclerView.getChildAdapterPosition(it)
            val details = DetailsFragment.newInstance(adapter.getUsers()[position])
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.frame, details)
                .addToBackStack(null).commit()
        }
        adapter.setOnClick(onClick)

        val userViewModel: UserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
        swipeRefresh.isRefreshing = true
        swipeRefresh.setOnRefreshListener {
            userViewModel.update()
        }
        userViewModel.users.observe(viewLifecycleOwner, Observer { users ->
            adapter.setUsers(users)
            swipeRefresh.isRefreshing = false
        })

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            UsersListFragment()
    }
}