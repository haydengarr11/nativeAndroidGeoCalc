package edu.gvsu.cis.androidgeocalculator

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import edu.gvsu.cis.androidgeocalculator.placeholder.HistoryContent.HistoryItem
import edu.gvsu.cis.androidgeocalculator.databinding.FragmentHistoryBinding

/**
 * [RecyclerView.Adapter] that can display a [HistoryItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyHistoryItemRecyclerViewAdapter(
    private val values: List<HistoryItem>,
    val listener: ((HistoryItem)  -> Unit)? = null)
 : RecyclerView.Adapter<MyHistoryItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(values[position], listener)
    }


    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        val mP1 : TextView = binding.p1
        val mP2 : TextView = binding.p2
        val mDateTime: TextView = binding.timestamp
        var mItem: HistoryItem? = null
        val parentView: View = binding.root

        override fun toString(): String {
            return super.toString() + " '" + mDateTime.text + "'"
        }

        public fun bindTo(d: HistoryItem, listener: ((HistoryItem) -> Unit)?) {
            mItem = d
            mP1.text = "(${d.origLat},${d.origLng})"
            mP2.text = "(${d.destLat},${d.destLng})"
            mDateTime.text = d.timestamp.toString()
            if (listener != null) {
                parentView.setOnClickListener {
                    listener(d)
                }
            }
        }

    }



}