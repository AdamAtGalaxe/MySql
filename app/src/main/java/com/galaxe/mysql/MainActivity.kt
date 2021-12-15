package com.galaxe.mysql

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var enterName : EditText
    lateinit var enterAge : EditText

    lateinit var Name : TextView
    lateinit var Age : TextView
    lateinit var myContext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var addName  = findViewById<Button>(R.id.addName)
        var printName = findViewById<Button>(R.id.printName)

        enterName = findViewById(R.id.enterName)
        enterAge = findViewById<EditText>(R.id.enterAge)
        myContext = this
        Name = findViewById(R.id.Name)
        Age = findViewById(R.id.Age)


    }
    fun addPerson(v: View){


            // below we have created
            // a new DBHelper class,
            // and passed context to it
        val db = DatabaseHelper(this)

            // creating variables for values
            // in name and age edit texts
        val name = enterName.text.toString()
        val age = enterAge.text.toString().toInt()

            // calling method to add
            // name to our database
        db.addPerson(name, age)

            // Toast to message on the screen
        Toast.makeText(this, name + " added to database", Toast.LENGTH_LONG).show()

            // at last, clearing edit texts
        enterName.text.clear()
        enterAge.text.clear()

    }
    fun refreshList(v: View){

        val dbh = DatabaseHelper( this)
        dbh.getAllItems(Name, Age)

    }
    fun delete(v: View){
        val db = DatabaseHelper( this)
        db.deleteAll()

        refreshList(v)
    }
}