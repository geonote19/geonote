package com.geonote.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.geonote.R
import com.geonote.data.model.db.Note
import com.geonote.databinding.FragmentListItemBinding

class ListFragmentAdapter(
    context: Context,
    private val mCallback: Callback
) : RecyclerView.Adapter<ListFragmentAdapter.ViewHolder>() {

    private val mData = mutableListOf<Note>()
    private val mInflater = LayoutInflater.from(context)

    override fun getItemCount() = mData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<FragmentListItemBinding>(
            mInflater,
            R.layout.fragment_list_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    fun setData(data: List<Note>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val mDataBinding: FragmentListItemBinding) :
        RecyclerView.ViewHolder(mDataBinding.root) {

        fun bind(note: Note) {
            mDataBinding.note = note
            mDataBinding.callback = mCallback
            mDataBinding.executePendingBindings()
        }
    }

    companion object {
        interface Callback {
            fun onItemClick(note: Note)
        }
    }
}