package com.test.pokedex.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.koushikdutta.ion.Ion
import com.test.pokedex.Adapters.AdapterList
import com.test.pokedex.R

import kotlinx.android.synthetic.main.activity_list.*

class ActivityList : AppCompatActivity() {
    private var context: Context = this

    private lateinit var data: JsonArray
    private lateinit var gridLayoutManager: LinearLayoutManager
    private lateinit var adapter: AdapterList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        initializeData()
    }

    private fun initializeData() {
        Ion.with(context)
            .load("https://pokeapi.co/api/v2/pokemon/")
            .asJsonObject()
            .setCallback { _, result ->
                Log.i("Output", result.getAsJsonArray("results").size().toString())
                data =  result.getAsJsonArray("results")
                initializeList()
            }
    }

    private fun initializeList() {
        gridLayoutManager = GridLayoutManager(context, 3)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        gridLayoutManager.scrollToPosition(0)

        adapter = AdapterList()
        adapter.AdapterList(context, data)

        recycler_view_list.layoutManager = gridLayoutManager
        recycler_view_list.adapter = adapter
        recycler_view_list.setHasFixedSize(true)
        recycler_view_list.itemAnimator = DefaultItemAnimator()
    }
}
