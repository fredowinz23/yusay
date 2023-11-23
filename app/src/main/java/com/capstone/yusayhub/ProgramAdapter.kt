package com.capstone.yusayhub

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.capstone.yusayhub.models.Program

class ProgramAdapter(private var context: Context?, private val mList: List<Program>) : RecyclerView.Adapter<ProgramAdapter.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_programs, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        holder.title.text = item.title
        holder.description.text = item.description
        holder.address.text = item.address
        holder.dateTime.text = "${item.date} at ${item.time}"
//        intent.putExtra("categoryId", customize.category.id)
//        intent.putExtra("type", type)
//        context?.startActivity(intent)
        holder.item.setOnClickListener {
            (context as ProgramActivity).checkDialog(item.id, item.description)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val address: TextView = itemView.findViewById(R.id.tvAddress)
        val description: TextView = itemView.findViewById(R.id.tvDescription)
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val dateTime: TextView = itemView.findViewById(R.id.tvDateTime)
        val item: LinearLayout = itemView.findViewById(R.id.llItem)
    }

}