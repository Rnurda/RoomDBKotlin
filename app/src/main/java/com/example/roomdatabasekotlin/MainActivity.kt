package com.example.roomdatabasekotlin

import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class FMainActivity : AppCompatActivity() {
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "state"
    lateinit var sharedPref: SharedPreferences

    private var userDatabase: UserDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        val defaultValue = sharedPref.getBoolean(PREF_NAME,false)
        recycler_view.layoutManager = LinearLayoutManager(this)
        userDatabase = UserDatabase.getDatabase(this)!!


        checkbox.isChecked = defaultValue
        checkbox.setOnClickListener{
            check()
        }


        toast("sad")



        btnSave.setOnClickListener {
            if (!etEnterText.text.toString().isEmpty()) {
                val chapterObj = UserModel(etEnterText.text.toString())
                InsertTask(this, chapterObj).execute()
            }
        }

        deletebyindex.setOnClickListener {
            if (!etEnterText.text.toString().isEmpty()) {
                Delete_BY_ID_Database(this, etEnterText.text.toString()).execute()
            }
        }

        GetDataFromDb(this).execute()

        btnDisplay.setOnClickListener {
            GetDataFromDb(this).execute()
        }


        update.setOnClickListener {
            if (!etEnterText.text.toString().isEmpty() && !age.text.toString().isEmpty()) {
                updatefunc(etEnterText.text.toString(),age.text.toString())
            }
        }


        if((recycler_view.adapter as UserAddAdapter?) != null){
            (recycler_view.adapter as UserAddAdapter).onItemClick = { contact ->
                Toast.makeText(this, contact.userName, Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this, "NOOOOOOOO", Toast.LENGTH_LONG).show()
        }


    }




    fun check(){
        if(checkbox.isChecked){
            deleteAlltable(this).execute()
            println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! + delete all database")
            val editor = sharedPref.edit()
            editor.putBoolean(PREF_NAME, checkbox.isChecked)
            editor.apply()

        }
        else {
            println("?????????????????????????????????")
            val editor = sharedPref.edit()
            editor.putBoolean(PREF_NAME, checkbox.isChecked)
            editor.apply()

        }
    }

    private class InsertTask(var context: MainActivity, var chapter: UserModel) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            context.userDatabase!!.userDao().insert(chapter)
            return true
        }
        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
                Toast.makeText(context, "Added to Database", Toast.LENGTH_LONG).show()
            }
        }

    }

    private class GetDataFromDb(var context: MainActivity) : AsyncTask<Void, Void, List<UserModel>>() {
        override fun doInBackground(vararg params: Void?): List<UserModel> {
            return context.userDatabase!!.userDao().getAllChapter()
        }

        override fun onPostExecute(userList: List<UserModel>?) {
            //context.recycler_view.adapter = UserAddAdapter(chapterList)
            if (userList!!.size > 0) {
                //for (i in 0..chapterList.size - 1) {
                    println("?" + userList)
                    context.runOnUiThread {
                        context.recycler_view.adapter = UserAddAdapter(userList)
                    }

                //}
                    //context.tvDatafromDb.append(chapterList[i].chapterName)
                //}
            }
        }
    }

    private class Delete_BY_ID_Database(var context: MainActivity,var user: String) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            context.userDatabase!!.userDao().deletetablebyid(user)
            return true
        }

        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
                Toast.makeText(context, "Database empty", Toast.LENGTH_LONG).show()
            }
        }
    }

    private class deleteAlltable(var context: MainActivity) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            context.userDatabase!!.userDao().deletealltable()
            return true
        }
        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
                context.recycler_view.adapter = UserAddAdapter(null)
                Toast.makeText(context, "all database deleted! at the moment", Toast.LENGTH_LONG).show()
            }
        }
    }

    private class UpdateByUserId(var context: MainActivity,var up_user: String,var user: String) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            context.userDatabase!!.userDao().updateTable(up_user,user)
            return true
        }
        override fun onPostExecute(bool: Boolean?) {
            if (bool!!) {
                context.recycler_view.adapter = UserAddAdapter(null)
                Toast.makeText(context, "Updated Database", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun updatefunc(user_name:String,upuser_name:String){
        val builder = AlertDialog.Builder(this)
            builder.setTitle(user_name)
            builder.setMessage(upuser_name)

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(applicationContext, android.R.string.yes, Toast.LENGTH_SHORT).show()
                UpdateByUserId(this,upuser_name,user_name)
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(applicationContext, android.R.string.no, Toast.LENGTH_SHORT).show()
            }

            builder.setNeutralButton("Maybe") { dialog, which ->
                Toast.makeText(applicationContext, "Maybe", Toast.LENGTH_SHORT).show()
            }
            builder.show()
    }



}


