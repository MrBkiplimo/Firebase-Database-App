package com.example.myfirebasedataapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    var eidtTextName: EditText? = null
    var eidtTextEmail: EditText? = null
    var eidtTextIdNumber: EditText? = null
    var buttonSave: Button? = null
    var buttonView: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eidtTextName = findViewById(R.id.mEdtName)
        eidtTextEmail = findViewById(R.id.mEdtEmail)
        eidtTextIdNumber = findViewById(R.id.mEdtIDNumber)
        buttonSave = findViewById(R.id.mBtnsave)
        buttonView = findViewById(R.id.mBtnView)


        buttonView!!.setOnClickListener {
            val goToUserIntent=Intent(applicationContext,UsersActivity::class.java)
            startActivity(goToUserIntent)
        }
//FireBaaaaaaaaaaaaase!!!!
        buttonSave!!.setOnClickListener {
//receive the data
            val username = eidtTextName!!.text.toString().trim()
            val userEmail = eidtTextEmail!!.text.toString().trim()
            val userIdNumber = eidtTextIdNumber!!.text.toString().trim()
            val id =System.currentTimeMillis().toString()
//                check if user is submitting empty fields
            if (username.isEmpty()){
                eidtTextName!!.setError("Please fill this Field!!")
                eidtTextName!!.requestFocus()
            }else if (userEmail.isEmpty()){
                 eidtTextEmail!!.setError("Please fill this Field!!")
                eidtTextEmail!!.requestFocus()
            }else if (userIdNumber.isEmpty()){
                eidtTextIdNumber  !!.setError("Please fill this Field!!")
               eidtTextIdNumber !!.requestFocus()
            }else {
//                Save the data
//                start by creating the user object
                val userData = User(username,userEmail,userIdNumber,id)
//                create a Reference to the Database to store data
                val reference = FirebaseDatabase.getInstance().getReference().child("Employees/$id")
//                start saving userData
                reference.setValue(userData).addOnCompleteListener {
                    task->
                    if (task.isSuccessful){
                            Toast.makeText(applicationContext,"Data saved Successfully",
                                Toast.LENGTH_LONG).show()                    }
                }



            }

        }



//            .....
    }


}
