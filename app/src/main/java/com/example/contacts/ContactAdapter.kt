package com.example.myproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R

class ContactAdapter(private val listOfDataItems: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.DataEntryViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataEntryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data_entry_list_item, parent, false)
        return DataEntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataEntryViewHolder, position: Int) {
        val currentItem = listOfDataItems[position]
        holder.name.text = currentItem.textName
        holder.phoneNumber.text = currentItem.textPhoneNumber
        holder.image.setImageURI(currentItem.profileImage)

    }

    override fun getItemCount(): Int {
        return listOfDataItems.size
    }


    class DataEntryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
     val image : ImageView = view.findViewById(R.id.profile_image)
      val name: TextView = view.findViewById(R.id.text_name)
      val phoneNumber: TextView = view.findViewById(R.id.text_phone_number)
    }
}