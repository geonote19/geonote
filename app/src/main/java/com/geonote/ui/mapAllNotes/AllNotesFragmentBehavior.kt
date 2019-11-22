package com.geonote.ui.mapAllNotes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geonote.R
import com.geonote.data.model.db.Note
import com.geonote.databinding.FragmentRecyclerBinding
import com.geonote.ui.base.BaseFragment

class AllNotesFragmentBehavior : BaseFragment<FragmentRecyclerBinding,MapFragmentViewModel,MapActivity>(), AllNotesAdapter.ClickListener {

    private var clickListener: Listener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.allNotesMapRecycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapterAllNotesMap = AllNotesAdapter(listPoi, this)
        recyclerView.adapter = adapterAllNotesMap

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            clickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    override fun onClickNote(item: Note) {
        clickListener?.onCarNote(item)
    }

    interface Listener {
        fun onCarNote(item: Note)
    }
}