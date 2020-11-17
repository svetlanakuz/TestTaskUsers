package com.example.testtaskusers.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskusers.R
import com.example.testtaskusers.databinding.FragmentDetailsBinding
import com.example.testtaskusers.model.UserModel
import com.example.testtaskusers.viewmodel.FriendViewModel
import java.text.SimpleDateFormat
import java.util.*


private const val USER_ARG = "userArg"

class DetailsFragment : Fragment() {
    private lateinit var user: UserModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = arguments!!.getParcelable(USER_ARG)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater)
        binding.user = user

        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.friends_recycler_view)
        val adapter = UserAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val onClick = View.OnClickListener {
            val position = recyclerView.getChildAdapterPosition(it)
            val details = newInstance(adapter.getUsers()[position])
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.frame, details)
                .addToBackStack(null).commit()
        }
        adapter.setOnClick(onClick)

        val friendViewModel: FriendViewModel =
            ViewModelProvider(this).get(FriendViewModel::class.java)
        friendViewModel.getUsers(user.friends).observe(viewLifecycleOwner, Observer { users ->
            adapter.setUsers(users)
        })

        val coordinatesText = binding.root.findViewById<TextView>(R.id.user_coordinates)
        coordinatesText.setOnClickListener {
            val uri = "http://maps.google.com/maps?q=loc:${user.latitude},${user.longitude}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            context!!.startActivity(intent)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(user: UserModel) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_ARG, user)
                }
            }
    }
}

object Converter {
    @JvmStatic
    fun formatDate(dateString: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZ", Locale.ROOT).parse(dateString)
        return SimpleDateFormat("HH:mm dd.MM.yy", Locale.ROOT).format(date!!)
    }

    @JvmStatic
    fun getEyesColor(colorName: String): Int {
        return when (colorName) {
            "brown" -> R.color.brown_eyes
            "blue" -> R.color.blue_eyes
            "green" -> R.color.green_eyes
            else -> 0
        }
    }

    @JvmStatic
    fun getFruitImage(colorName: String): Int {
        return when (colorName) {
            "banana" -> R.drawable.banana
            "apple" -> R.drawable.apple
            "strawberry" -> R.drawable.strawberry
            else -> 0
        }
    }
}