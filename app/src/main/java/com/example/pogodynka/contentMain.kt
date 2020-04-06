package com.example.pogodynka

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.iceandfireapi.data.network.WeatherApiService
import com.example.pogodynka.Network.WeatherApi
import com.example.pogodynka.database.City
import com.example.pogodynka.database.CityDatabase
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*
import kotlinx.coroutines.*

class contentMain:Fragment() {
    private  val newCityActivityRequestCode = 1
    private lateinit var ContenMainViewModel: contentMainViewModel
    private var navController: NavController? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context: Context = activity!!.baseContext

        ContenMainViewModel =  ViewModelProviders.of(this).get(contentMainViewModel::class.java)

        val root = inflater.inflate(R.layout.content_main, container, false)
        val recyclerView:RecyclerView = root.findViewById(R.id.recyclerViewMain)
        recyclerView.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL, false)
        val adapter = recyclerViewAdapter(context)
        recyclerView.adapter = adapter
        ContenMainViewModel.allCities.observe(viewLifecycleOwner, Observer { cities -> cities?.let{adapter.setCities(it)}})
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                ContenMainViewModel.delete(adapter.getCityAt(position))
                Toast.makeText(context,"City deleted successfully",Toast.LENGTH_LONG).show()

            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        root.swipe_refresh.setOnRefreshListener {
            adapter.notifyDataSetChanged()
            swipe_refresh.isRefreshing = false
        }
        root.setOnClickListener{


        }
        return root
    }

/*    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == newCityActivityRequestCode && resultCode == Activity.RESULT_OK){
            data?.getStringExtra(Activity.)
        }
    }*/
}