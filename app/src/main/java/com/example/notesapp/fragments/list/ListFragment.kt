package com.example.notesapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.viewmodel.UserViewModel

@Suppress("DEPRECATION")
class ListFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        //RecyclerView
        val adapter = ListAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //UserViewModel
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mUserViewModel.readAllData.observe(viewLifecycleOwner) { user ->
            adapter.setData(user)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val btnAdd: View = view.findViewById(R.id.floatingActionButton)
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        setHasOptionsMenu(true)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("super.onCreateOptionsMenu(menu, inflater)",
        "androidx.fragment.app.Fragment"))
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("super.onOptionsItemSelected(item)",
        "androidx.fragment.app.Fragment"))
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.delete_icon){
            deleteNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNotes(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setIcon(R.drawable.ic_warning)
        builder.setTitle("Delete")
        builder.setMessage("Do you really want to delete all notes?")
        builder.setPositiveButton("Yes"){_,_->
            mUserViewModel.deleteAll()
            Toast.makeText(requireContext(),"Deleted Successfully", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_,_->}
        builder.show()

    }
}