package com.example.edutest.ui.home.visitor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.edutest.R
import com.example.edutest.data.db.entities.User
import com.example.edutest.data.db.entities.Visitor
import kotlinx.android.synthetic.main.item_visitor_preview.view.*

class VisitorAdapter: RecyclerView.Adapter<VisitorAdapter.VisitorViewHolder> (){

    inner class VisitorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Visitor> (){
        override fun areItemsTheSame(oldItem: Visitor, newItem: Visitor): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Visitor, newItem: Visitor): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitorViewHolder {
        return VisitorViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_visitor_preview,parent,false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: VisitorViewHolder, position: Int) {
        val visitor = differ.currentList[position]
        Log.d("noticeres",visitor.toString())
        holder.itemView.apply {
            tvNoticeTitle.text = visitor.name
            tvNoticeContent.text = visitor.status
            tvNoticeDate.text = visitor.checkin
            setOnClickListener{
                onItemClickListener?.let { it(visitor) }
            }
        }
    }

    private var onItemClickListener:((Visitor) -> Unit)? = null
    fun setOnItemClickListener(listener: (Visitor) -> Unit){
        onItemClickListener = listener
    }
}