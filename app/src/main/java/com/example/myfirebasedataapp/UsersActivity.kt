package com.example.myfirebasedataapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UsersActivity : AppCompatActivity() {
    var listUsers : ListView ?= null
    var adapter : CustomAdapter ?= null
    var users : ArrayList<User> ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        listUsers = findViewById(R.id.mListEmployees)
        users = ArrayList()
        adapter = CustomAdapter(this,users!!)
//        connect to users table/child
        val reference = FirebaseDatabase.getInstance().getReference().child("Employees")
//        start Fetching the Data
        reference.addValueEventListener(object  : ValueEventListener{
//            override the on data change
            override fun onDataChange(snapshot: DataSnapshot) {
                users!!.clear()
//    use for each loop to add he users on the Arraylist
               for (snap in snapshot.children){
                   var user = snap.getValue(User::class.java)
                   users!!.add(user!!)
               }
    adapter!!.notifyDataSetChanged()
            }
//            override on cancel method
            override fun onCancelled(error: DatabaseError) {
//    coz u had set the rules on the database as True, True,
                Toast.makeText(applicationContext,"Please contact the Admin",
                                Toast.LENGTH_LONG).show()
            }
        })
        listUsers!!.adapter=adapter!!
//        set on an item click listener to the list view
     listUsers!!.setOnItemClickListener { adapterView, view, i, l ->
         val UserId = users!!.get(i).id
         val deletionReference = FirebaseDatabase.getInstance().
         getReference().child("Employees/$UserId")
//         set an alert when someone clicks on an item
         val alertDialog = AlertDialog.Builder(this)
         alertDialog.setTitle("Alert!!")
         alertDialog.setMessage("Select an action you want to perform")
         alertDialog.setNegativeButton("Update",DialogInterface.OnClickListener { dialogInterface, i ->
             //Dimiss
         })
         alertDialog.setPositiveButton("Delete",DialogInterface.OnClickListener { dialogInterface, i ->
             deletionReference.removeValue()
             Toast.makeText(applicationContext,"Deleted Successfully",
             Toast.LENGTH_LONG).show()
         })

     }
    }
}