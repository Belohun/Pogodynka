package com.example.pogodynka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.example.pogodynka.database.City
import com.example.pogodynka.database.CityDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_layout.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    private lateinit var activityViewModel : MainAcivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val floating_button_add:FloatingActionButton  = findViewById(R.id.floating_btn_add)
        activityViewModel = ViewModelProvider(this).get(MainAcivityViewModel::class.java)
        floating_button_add.setOnClickListener{
            val dialogView = LayoutInflater.from(this).inflate(R.layout.add_layout,null)
       val mBuilder = AlertDialog.Builder(this)
           .setView(dialogView)

           //.setTitle("Add City")
            val mAlertDialog = mBuilder.show()
            dialogView.btn_add.setOnClickListener{
                var noErrors = true
                    if(dialogView.add_city_text.text!!.isEmpty())
                {
                    dialogView.add_city_layout.error = resources.getString(R.string.error_string)
                    noErrors=false
                }else{
                    dialogView.add_city_layout.error = null
                }
                if(noErrors)
                {
                    val cityInsert = City(dialogView.add_city_text.text.toString())
                        activityViewModel.insert(cityInsert)
                        mAlertDialog.dismiss()

                }
            }
            dialogView.btn_cancel.setOnClickListener{
                mAlertDialog.dismiss()
            }

               }
        }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
