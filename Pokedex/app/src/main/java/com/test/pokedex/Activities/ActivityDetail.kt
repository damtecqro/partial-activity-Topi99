package com.test.pokedex.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.test.pokedex.R
import kotlinx.android.synthetic.main.activity_detail.toolbar
import org.json.JSONArray
import org.json.JSONObject

class ActivityDetail : AppCompatActivity() {
    private class Move {
        var levelLearnedAt = 0
        lateinit var learnMethod: String
        lateinit var name: String
    }

    private lateinit var pokemon: JSONObject
    private lateinit var types: JSONArray
    private lateinit var stats: JSONArray
    private lateinit var movesJSONArray: JSONArray

    private val context = this
    private var movesList: List<Move> = emptyList()

    private lateinit var pokeName: TextView
    private lateinit var pokeID: TextView
    private lateinit var pokeHeight: TextView
    private lateinit var typesTextView: TextView
    private lateinit var statsLayout: LinearLayout
    private lateinit var movesLayout: LinearLayout

    private lateinit var backDefaultImageView: ImageView
    private lateinit var backShinyImageView: ImageView
    private lateinit var frontDefaultImageView: ImageView
    private lateinit var frontShinyImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        initializeData()
        initializeComponents()
        setData()
    }

    private fun initializeData() {
        this.pokemon = JSONObject(intent.getStringExtra("item"))
        this.types = pokemon.getJSONArray("types")
        this.stats = pokemon.getJSONArray("stats")
        this.movesJSONArray = pokemon.getJSONArray("moves")

        for (i in 0 until movesJSONArray.length()) {
            var move = Move()
            move.name = movesJSONArray.getJSONObject(i).getJSONObject("move").getString("name")
            move.levelLearnedAt = movesJSONArray
                .getJSONObject(i)
                .getJSONArray("version_group_details")
                .getJSONObject(0)
                .getInt("level_learned_at")
            move.learnMethod = movesJSONArray
                .getJSONObject(i)
                .getJSONArray("version_group_details")
                .getJSONObject(0)
                .getJSONObject("move_learn_method")
                .getString("name")
            movesList += move
        }
    }

    private fun initializeComponents() {
        supportActionBar!!.title= (pokemon.get("name").toString().capitalize())
        pokeName = findViewById(R.id.name)
        pokeID = findViewById(R.id.id)
        pokeHeight = findViewById(R.id.height)
        typesTextView = findViewById(R.id.types)
        statsLayout = findViewById(R.id.stats_layout)
        movesLayout = findViewById(R.id.moves_layout)

        frontDefaultImageView = findViewById(R.id.front_default)
        frontShinyImageView = findViewById(R.id.front_shiny)
        backDefaultImageView = findViewById(R.id.back_default)
        backShinyImageView = findViewById(R.id.back_shiny)
    }

    private fun setData() {
        pokeName.text = pokeName.text.toString() + pokemon.getString("name").capitalize()
        pokeID.text = pokeID.text.toString() + pokemon.getString("id")
        pokeHeight.text = pokeHeight.text.toString() + pokemon.getString("height")

        for (i in 0 until types.length()) {
            val name = types.getJSONObject(i).getJSONObject("type").getString("name")
            typesTextView.text = typesTextView.text.toString() + name
            if (i != types.length() - 1) {
                typesTextView.text = typesTextView.text.toString() + ", "
            }
        }

        val inflater = LayoutInflater.from(context)

        for (i in 0 until stats.length()) {
            val name = stats.getJSONObject(i).getJSONObject("stat").getString("name")
            val baseStat = stats.getJSONObject(i).getString("base_stat")

            val layout = inflater.inflate(R.layout.stat_item, null, false)
            val statName = layout.findViewById<TextView>(R.id.name_stat)
            val statBase = layout.findViewById<TextView>(R.id.base_stat)

            statName.text = "Name: " + name
            statBase.text = "Base stat: " + baseStat

            statsLayout.addView(layout)
        }

        movesList.forEach {
            val layout = inflater.inflate(R.layout.move_item, null, false)
            val moveName = layout.findViewById<TextView>(R.id.name_move)
            val moveLevel = layout.findViewById<TextView>(R.id.move_level)
            val moveMethod = layout.findViewById<TextView>(R.id.move_method)

            moveName.text = "Name: " + it.name
            moveLevel.text = "Learned At lvl: " + it.levelLearnedAt
            moveMethod.text = "Learn Method: " + it.learnMethod

            movesLayout.addView(layout)
        }

        val defaultImageID = R.drawable.pokemon_logo_min

        Glide.with(context)
            .load(pokemon.getJSONObject("sprites").getString("front_default"))
            .placeholder(defaultImageID)
            .error(defaultImageID)
            .into(frontDefaultImageView)

        Glide.with(context)
            .load(pokemon.getJSONObject("sprites").getString("front_shiny"))
            .placeholder(defaultImageID)
            .error(defaultImageID)
            .into(frontShinyImageView)

        Glide.with(context)
            .load(pokemon.getJSONObject("sprites").getString("back_default"))
            .placeholder(defaultImageID)
            .error(defaultImageID)
            .into(backDefaultImageView)

        Glide.with(context)
            .load(pokemon.getJSONObject("sprites").getString("back_shiny"))
            .placeholder(defaultImageID)
            .error(defaultImageID)
            .into(backShinyImageView)
    }
}
