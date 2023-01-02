package com.example.notesapp.fragments.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.model.User
import com.example.notesapp.viewmodel.UserViewModel


class AddFragment : Fragment() {


    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val btn: View = view.findViewById(R.id.add_btn)
        btn.setOnClickListener {
            insertDataToDatabase(view)
        }
    }

    private fun insertDataToDatabase(view: View) {
        val topicInput : EditText = view.findViewById(R.id.topic)
        val noteInput : EditText = view.findViewById(R.id.notes)
        val topic = topicInput.text.toString()
        val note = noteInput.text.toString()

        if(topic.isNotEmpty() && note.isNotEmpty()){
            val user = User(0,topic,note)
            mUserViewModel.addUser(user)

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
            Toast.makeText(requireContext(),"Done",Toast.LENGTH_SHORT).show()
        }
        else if(topic.isEmpty()){
            Toast.makeText(requireContext(),"please, provide topic name", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(requireContext(),"please, enter your note", Toast.LENGTH_SHORT).show()
        }
    }

}