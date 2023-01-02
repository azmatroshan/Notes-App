package com.example.notesapp.fragments.list

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.model.User

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val topicTv: TextView = itemView.findViewById(R.id.topic_tv)
        val noteTv: TextView = itemView.findViewById(R.id.note_tv)
        val recycleViewItem: View = itemView.findViewById(R.id.card_note)
        val shareBtn: View = itemView.findViewById(R.id.share_note)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.topicTv.text = currentItem.topic
        holder.noteTv.text = currentItem.notes
        holder.recycleViewItem.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdate(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
        holder.shareBtn.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Topic: ${currentItem.topic}\n\n${currentItem.notes}")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(holder.itemView.context,shareIntent,null)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }
}