package com.example.notebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.notebook.db.Constant
import com.notebook.db.Note
import com.notebook.db.NoteDB
import kotlinx.android.synthetic.main.form.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class form : AppCompatActivity() {
    private val db by lazy { NoteDB(this) }
    private var noteId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form)
        setupView()
        setupLstener()
    }

    private fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        when (intentType()) {
            Constant.TYPE_CREATE -> {
                supportActionBar!!.title = "TAMBAH CATATAN"
                buttonsave.visibility = View.VISIBLE
                buttonupdate.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                supportActionBar!!.title = "BACA CATATAN"
                buttonsave.visibility = View.GONE
                buttonupdate.visibility = View.GONE
                getNote()
            }
            Constant.TYPE_UPDATE -> {
                supportActionBar!!.title = "EDIT CATATAN"
                buttonsave.visibility = View.GONE
                buttonupdate.visibility = View.VISIBLE
                getNote()
            }
        }
    }

    private fun setupLstener(){
        buttonsave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().addNote(
                    Note(
                        0,
                        edittitle.text.toString(),
                        editnote.text.toString()
                    )
                )
                finish()
            }
        }
        buttonupdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().updateNote(
                    Note(
                        noteId,
                        edittitle.text.toString(),
                        editnote.text.toString()
                    )
                )
                finish()
            }
        }
    }

    private fun getNote(){
        noteId = intent.getIntExtra("note_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNote(noteId).get(0)
            edittitle.setText( notes.title )
            editnote.setText( notes.note )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun intentType(): Int {
        return intent.getIntExtra("intent_type", 0)
    }
}