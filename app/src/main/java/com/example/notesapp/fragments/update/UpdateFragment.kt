package com.example.notesapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.R
import com.example.notesapp.model.User
import com.example.notesapp.viewmodel.UserViewModel


@Suppress("DEPRECATION")
class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // To Handle View
        super.onViewCreated(view, savedInstanceState)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val topicUpdate: EditText = view.findViewById(R.id.topic_update)
        val notesUpdate: EditText = view.findViewById(R.id.notes_update)
        val btnUpdate: View = view.findViewById(R.id.update_btn)
        topicUpdate.setText(args.currentNote.topic)
        notesUpdate.setText(args.currentNote.notes)
        btnUpdate.setOnClickListener {
            insertDataToDatabase(view)
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
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setIcon(R.drawable.ic_warning)
        builder.setTitle("Delete ${args.currentNote.topic}")
        builder.setMessage("Do you really want to delete ${args.currentNote.topic}?")
        builder.setPositiveButton("Yes"){_,_->
            mUserViewModel.deleteUser(args.currentNote)
            findNavController().navigate(R.id.action_update_to_listFragment)
            Toast.makeText(requireContext(),"Deleted Successfully", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_,_->}
        builder.show()
    }

    private fun insertDataToDatabase(view: View) {
        val topicInput : EditText = view.findViewById(R.id.topic_update)
        val noteInput : EditText = view.findViewById(R.id.notes_update)
        val topic = topicInput.text.toString()
        val note = noteInput.text.toString()

        if(topic.isNotEmpty() && note.isNotEmpty()){
            val user = User(args.currentNote.id,topic,note)
            mUserViewModel.updateUser(user)

            findNavController().navigate(R.id.action_update_to_listFragment)
            Toast.makeText(requireContext(),"Done", Toast.LENGTH_SHORT).show()
        }
        else if(topic.isEmpty()){
            Toast.makeText(requireContext(),"please, provide topic name", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(requireContext(),"please, enter your note", Toast.LENGTH_SHORT).show()
        }
    }
}