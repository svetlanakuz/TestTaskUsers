package com.example.testtaskusers.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskusers.R
import com.example.testtaskusers.model.UserModel

class UserAdapter(private var items: List<UserModel> = emptyList()) :
    RecyclerView.Adapter<UserAdapter.UserHolder>() {

    private lateinit var onClick: View.OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.userName.text = items[position].name
        holder.userEmail.text = items[position].email
        holder.itemView.setOnClickListener(onClick)
        if (items[position].isActive) {
            holder.itemView.isClickable = true
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.active_item
                )
            )
        } else {
            holder.itemView.isClickable = false
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.not_active_item
                )
            )
        }
    }

    fun setOnClick(onClick: View.OnClickListener) {
        this.onClick = onClick
    }

    fun setUsers(users: List<UserModel>) {
        items = users
        notifyDataSetChanged()
    }

    fun getUsers(): List<UserModel> {
        return items
    }

    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val userEmail: TextView = itemView.findViewById(R.id.user_email)
    }
}