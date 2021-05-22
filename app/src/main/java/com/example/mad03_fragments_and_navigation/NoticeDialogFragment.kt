package com.example.mad03_fragments_and_navigation


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.mad03_fragments_and_navigation.database.AppDatabase
import com.example.mad03_fragments_and_navigation.database.MovieDao
import com.example.mad03_fragments_and_navigation.databinding.DialogEditMovieBinding
import com.example.mad03_fragments_and_navigation.databinding.FragmentFavoritesBinding
import com.example.mad03_fragments_and_navigation.models.Movie
import com.example.mad03_fragments_and_navigation.repositories.MovieRepository
import com.example.mad03_fragments_and_navigation.viewmodels.MovieFavoritesViewModel
import com.example.mad03_fragments_and_navigation.viewmodels.MovieFavoritesViewModelFactory
import kotlinx.android.synthetic.main.dialog_edit_movie.*

class NoticeDialogFragment(movieObj: Movie) : DialogFragment() {

    private lateinit var editTextNote: String

    private lateinit var binding: DialogEditMovieBinding
    private lateinit var sharedViewModel: MovieFavoritesViewModel
    private lateinit var dao: MovieDao
    private lateinit var repository: MovieRepository
    private lateinit var factory: MovieFavoritesViewModelFactory
    private var selectedMovie = movieObj


    // Use this instance of the interface to deliver action events
    internal lateinit var listener: NoticeDialogListener

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface NoticeDialogListener {
        fun onDialogPositiveClick(note: String)
    }


    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_edit_movie, container, false)
        //bindingDialog = DataBindingUtil.inflate(inflater, R.layout.fragment_note_dialog, container, false)

        val application = requireNotNull(this.activity).application
        dao = AppDatabase.getInstance(application).MovieDao
        //dao = AppDatabase.getInstance(requireActivity().application).MovieDao

        repository = MovieRepository.getInstance(dao)
        factory = MovieFavoritesViewModelFactory(repository)

        sharedViewModel = ViewModelProvider(this, factory).get(MovieFavoritesViewModel::class.java)
        binding.movieFavoritesViewModel = sharedViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_edit_movie, null))
                // Add action buttons
                .setPositiveButton(R.string.save
                ) { _, _ ->
                    var addedNote = editText_add_a_note.text.toString()
                    // Send the positive button event back to the host activity
                    Log.i("NoticeDialogFragment", "Added Note: $addedNote")
                    listener.onDialogPositiveClick(addedNote)
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    Log.i("NoticeDialogFragment", "Cancel pressed")
                }

            editTextNote = editText_add_a_note.text.toString()

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        const val TAG = "NoticeDialogFragment"
    }


    //Log.i("NoticeDialogFragment", "Save pressed, $selectedMovie")

    //var noteEntered: String = editText_add_a_note.text.toString()

    //Log.i("NoticeDialogFragment", "Note: $noteEntered")
    //val updatedMovie = Movie(
    //    selectedMovie.title,
    //    selectedMovie.note
    //)
    //selectedMovie.note = noteEntered
    //Log.i("NoticeDialogFragment", "Changed: $selectedMovie")
    // Add to DB
    //sharedViewModel.editMovie(selectedMovie)
    //}
}