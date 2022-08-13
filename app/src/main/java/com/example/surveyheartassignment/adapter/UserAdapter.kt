package com.example.surveyheartassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.surveyheartassignment.OnUserClickListener
import com.example.surveyheartassignment.R
import com.example.surveyheartassignment.model.Results
import com.example.surveyheartassignment.model.UserResponse
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(var userList: ArrayList<Results>,var onUserClickListener: OnUserClickListener) : RecyclerView.Adapter<UserAdapter.viewHolder>(), Filterable {
    var userFilterList = ArrayList<Results>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.userName.text = "${userList[position].name.first} ${userList[position].name.last}"
        holder.userEmail.text = userList[position].email
        Picasso.get()
            .load(userList[position].picture.large)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.userImage)

        ViewCompat.setTransitionName(holder.userImage, "${userList[position].name.first} ${userList[position].name.last}")

        holder.itemView.setOnClickListener {
            onUserClickListener.onUserClickListener(userList[position])
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun update(userList: ArrayList<Results>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val userEmail: TextView = itemView.findViewById(R.id.user_email)
        val userImage = itemView.findViewById<CircleImageView>(R.id.circleImageView)

    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(keyword: CharSequence?): FilterResults {
            userFilterList = userList
            val filteredList = ArrayList<Results>()
            if(keyword.toString().isEmpty()){
                filteredList.addAll(userFilterList)
            }
            else{
                val filteredStrings: String = keyword.toString().toLowerCase().trim()

                for (results: Results in userFilterList) {
                    val fullName = "${results.name.first} ${results.name.last}"
                    if (fullName.toLowerCase().contains(filteredStrings)) {
                        filteredList.add(results)
                    }
                }
            }
            return FilterResults().apply { values = filteredList }
        }

        override fun publishResults(p0: CharSequence?, result: FilterResults?) {
            userList.clear()
            userList.addAll(result?.values as ArrayList<Results>)
            notifyDataSetChanged()
        }

    }
}