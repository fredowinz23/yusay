package com.capstone.yusayhub

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.capstone.yusayhub.models.Program
import com.squareup.picasso.Picasso
import java.io.Serializable

class ProgramAdapter(private var context: Context?, private val mList: List<Program>) : RecyclerView.Adapter<ProgramAdapter.ViewHolder>() {

    var programType: String = ""

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
        holder.joiners.text = "Joiners: " + item.totalJoiner.toString()
        holder.address.text = "@" + item.address
        holder.dateTime.text = "${item.date} at ${item.time}"
        Picasso.with(context).load(item.image).fit().centerCrop()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .into(holder.imageView)
//        holder.item.setOnClickListener {
//            (context as ProgramActivity).checkDialog(item.id, item.description)
//        }
        holder.item.setOnClickListener {
            val intent = Intent(context, ProgramDetailActivity::class.java)
            intent.putExtra("id", item.id)
            intent.putExtra("title", item.title)
            intent.putExtra("joiners", "Joiners: " + item.totalJoiner.toString())
            intent.putExtra("description", item.description)
            intent.putExtra("address", item.address)
            intent.putExtra("dateTime", "${item.date} at ${item.time}")
            intent.putExtra("image", item.image)
            intent.putExtra("programType", programType)
            context?.startActivity(intent)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val address: TextView = itemView.findViewById(R.id.tvAddress)
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val dateTime: TextView = itemView.findViewById(R.id.tvDateTime)
        val joiners: TextView = itemView.findViewById(R.id.tvJoiners)
        val imageView: ImageView = itemView.findViewById(R.id.ivImage)
        val item: LinearLayout = itemView.findViewById(R.id.llItem)
    }

}
