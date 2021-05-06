package com.example.haushaltsapp.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.haushaltsapp.R
import com.example.haushaltsapp.types.UserSummary
import javax.annotation.Nonnull

class UserAddRecyclerViewAdapter(private val listener: UserAddListener)
    : RecyclerView.Adapter<UserAddRecyclerViewAdapter.ViewHolder>() {

    private var users: List<UserSummary> = emptyList()
    private var members: List<UserSummary> = emptyList()
    private var filteredUsers: List<UserSummary> = emptyList()
    private var query: String = ""
    lateinit var owner: UserSummary

    fun updateUsers(users: List<UserSummary>) {
        this.users = users
        updateFilteredUsers()
        notifyDataSetChanged()
    }

    fun updateMembers(members: List<UserSummary>) {
        this.members = members
        updateFilteredUsers()
        notifyDataSetChanged()
    }

    fun updateSearchQuery(query: String) {
        this.query = query
        updateFilteredUsers()
        notifyDataSetChanged()
    }

    private fun updateFilteredUsers() {
        filteredUsers = users.minus(members).minus(owner).filter { it.name.contains(query) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = filteredUsers[position]
        holder.contentView.text = holder.item.name
    }

    override fun getItemCount(): Int {
        return filteredUsers.size
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val contentView: TextView = view.findViewById(R.id.user_list_item_name)
        lateinit var item: UserSummary

        init {
            view.setOnClickListener { listener.onClick(item) }
        }

        @Nonnull
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    interface UserAddListener {

        fun onClick(user: UserSummary)
    }
}