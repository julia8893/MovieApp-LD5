package com.example.mad03_fragments_and_navigation


import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mad03_fragments_and_navigation.adapters.FavoritesListAdapter
import com.example.mad03_fragments_and_navigation.database.AppDatabase
import com.example.mad03_fragments_and_navigation.database.MovieDao
import com.example.mad03_fragments_and_navigation.databinding.DialogEditMovieBinding
import com.example.mad03_fragments_and_navigation.databinding.FragmentFavoritesBinding
import com.example.mad03_fragments_and_navigation.models.Movie
import com.example.mad03_fragments_and_navigation.repositories.MovieRepository
import com.example.mad03_fragments_and_navigation.viewmodels.MovieFavoritesViewModel
import com.example.mad03_fragments_and_navigation.viewmodels.MovieFavoritesViewModelFactory
import kotlinx.android.synthetic.main.dialog_edit_movie.*


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var bindingDialog: DialogEditMovieBinding

    private lateinit var sharedViewModel: MovieFavoritesViewModel
    private lateinit var dao: MovieDao
    private lateinit var repository: MovieRepository
    private lateinit var factory: MovieFavoritesViewModelFactory

    private lateinit var textViewNote: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        //bindingDialog = DataBindingUtil.inflate(inflater, R.layout.fragment_note_dialog, container, false)

        val application = requireNotNull(this.activity).application
        dao = AppDatabase.getInstance(application).MovieDao
        //dao = AppDatabase.getInstance(requireActivity().application).MovieDao

        repository = MovieRepository.getInstance(dao)
        factory = MovieFavoritesViewModelFactory(repository)

        sharedViewModel = ViewModelProvider(this, factory).get(MovieFavoritesViewModel::class.java)
        binding.viewModelMovieFavorites = sharedViewModel
        binding.lifecycleOwner = this


        val adapter = FavoritesListAdapter(
            dataSet = listOf(),     // start with empty list
            onDeleteClicked = { movieId -> onDeleteMovieClicked(movieId) },   // pass functions to adapter
            onEditClicked = { movie -> onEditMovieClicked(movie) },           // pass functions to adapter
        )

        with(binding) {
            recyclerView.adapter = adapter
        }

        subscribeUI(adapter)

        return binding.root
    }

    // This is called when recyclerview item edit button is clicked
    private fun onEditMovieClicked(movieObj: Movie) {
        // TODO implement me

        showDialog(movieObj)

        //sharedViewModel.editMovie(movieObj)
        Log.i("FavoritesFragment", "onEditMovieClicked: $movieObj")
    }

    // This is called when recyclerview item remove button is clicked
    private fun onDeleteMovieClicked(movieId: Long) {
        // TODO implement me
        sharedViewModel.deleteMovie(movieId)
        Log.i("FavoritesFragment", "onDeleteMovieClicked: $movieId")
    }

    private fun onClearClicked() {

    }

    private fun subscribeUI(adapter: FavoritesListAdapter) {

        sharedViewModel.favoriteMovies.observe(
            viewLifecycleOwner,
            Observer { favoriteMovies -> adapter.updateDataSet(favoriteMovies) })

    }

    fun showNoteDialog(movieObj: Movie) {

    }

    fun showDialog(movieObj: Movie) {

        val builder = AlertDialog.Builder(requireContext())
        // Get the layout inflater
        val inflater = requireActivity().layoutInflater;
        val dialogLayout = DialogEditMovieBinding.inflate(layoutInflater)


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogLayout.root)
            // Add action buttons
            .setPositiveButton(R.string.save) { _, _ ->
                var newNote: String = editText_add_a_note.text.toString()
                Log.i("FavoritesFragment","newNote: $newNote")
                movieObj.note = newNote
            }
            .setNegativeButton(R.string.cancel) { _, _ ->

            }
        builder.create().show()
    }


/*
// The dialog fragment receives a reference to this Activity through the
// Fragment.onAttach() callback, which it uses to call the following methods
// defined by the NoticeDialogFragment.NoticeDialogListener interface
override fun onDialogPositiveClick(dialog: DialogFragment) {
    // User touched the dialog's positive button
    Log.i("FavoritesFragment","Save pressed")
}

override fun onDialogNegativeClick(dialog: DialogFragment) {
    // User touched the dialog's negative button
    Log.i("FavoritesFragment","Cancel pressed")
}
 */


}

