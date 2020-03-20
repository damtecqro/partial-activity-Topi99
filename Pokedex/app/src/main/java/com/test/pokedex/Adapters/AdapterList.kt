package com.test.pokedex.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import com.test.pokedex.R

class AdapterList: RecyclerView.Adapter<AdapterList.ViewHolder>() {
    private lateinit var data: JsonArray
    private lateinit var context: Context

    fun AdapterList(context: Context, data: JsonArray) {
        this.context = context
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size()
    }

    override fun onBindViewHolder(holder: AdapterList.ViewHolder, position: Int) {
        var item: JsonObject = data.get(position).asJsonObject

        holder.bind(item, context)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var pokeImage: ImageView = view.findViewById(R.id.poke_image)
        private var pokeName: TextView = view.findViewById(R.id.poke_name)
        private val defaultImageID = R.drawable.pokemon_logo_min

        private fun setDefaultImage(context: Context) {
            pokeImage.setImageDrawable(ContextCompat.getDrawable(context, defaultImageID))
        }

        fun bind(item: JsonObject, context: Context) {
            pokeName.text = item.get("name").asString

            Ion.with(context)
                .load(item.get("url").asString)
                .asJsonObject()
                .done { e, result ->
                    if (e == null) {
                        if (!result.get("sprites").isJsonNull) {
                            if (!result.get("sprites").asJsonObject.get("front_default").isJsonNull) {
                                // Print
                                Glide.with(context)
                                    .load(result.get("sprites").asJsonObject.get("front_default").asString)
                                    .placeholder(defaultImageID)
                                    .error(defaultImageID)
                                    .into(pokeImage)
                            } else {
                                setDefaultImage(context)
                            }
                        } else {
                            setDefaultImage(context)
                        }
                    } else {
                        setDefaultImage(context)
                    }
                }
        }
    }
}