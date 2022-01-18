package com.example.notebook

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notebook.db.Note
import kotlinx.android.synthetic.main.list.view.*

class NoteAdapter (var notes: ArrayList<Note>, var listener: OnAdapterListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.view.texttitle.text = note.title
        holder.view.texttitle.setOnClickListener {
            listener.onClick(note)
        }
        holder.view.iconedit.setOnClickListener {
            listener.onUpdate(note)
        }
        holder.view.icondelete.setOnClickListener {
            listener.onDelete(note)
        }
    }

    class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(newList: List<Note>) {
        notes.clear()
        notes.addAll(newList)
    }

    interface OnAdapterListener {
        fun onClick(note: Note)
        fun onUpdate(note: Note)
        fun onDelete(note: Note)
    }
}