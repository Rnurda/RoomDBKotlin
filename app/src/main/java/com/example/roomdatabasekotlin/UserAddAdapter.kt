package com.example.roomdatabasekotlin


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.useritem.view.*


class UserAddAdapter(val userlist: List<UserModel>?): RecyclerView.Adapter<UserAddAdapter.CustomViewHolder>() {

    var onItemClick: ((UserModel) -> Unit)? = null

    override fun getItemCount(): Int {
        println("||||<><>><>>|" + userlist?.count())
        if(userlist?.count() == null){
            return 0
        }
        return userlist?.count()
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): CustomViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(p0?.context)
        val cellForRow = layoutInflater.inflate(R.layout.useritem, p0, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(p0: CustomViewHolder, position: Int) {
        val user = userlist?.get(position)
        p0?.user_name?.text = user?.userName


        p0?.user_name.setOnClickListener{
            //Toast.makeText(, "Updated", Toast.LENGTH_LONG).show()
        }

    }

    inner class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val user_name = view.name
        init {
            if(userlist?.count() != null){
                itemView.setOnClickListener {
                    onItemClick?.invoke(userlist[adapterPosition])
                }
            }

        }
    }
}





















