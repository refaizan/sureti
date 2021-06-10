package com.sureti.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sureti.R
import com.sureti.models.BranchCollaboratorModel
import com.sureti.models.CollaboratorModel

class CollaboratorAdapter(
    private val list: List<CollaboratorModel>
) :
    RecyclerView.Adapter<CollaboratorAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.findViewById(R.id.item_collaborator_img)
        var tvName: TextView = view.findViewById(R.id.item_collaborator_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_collaborator, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: CollaboratorModel = list[position]

        if (model.profilePicture.isNullOrBlank()) {
            if (model.firstName.isNullOrBlank().not())
                holder.tvName.text = model.firstName.uppercase()
            else
                holder.tvName.text = "?"
            holder.img.visibility = View.GONE
        } else {
            Glide.with(holder.itemView.context).load(model.profilePicture).into(holder.img)
            holder.tvName.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}