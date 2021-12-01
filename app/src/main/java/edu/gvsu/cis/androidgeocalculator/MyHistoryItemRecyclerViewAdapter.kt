package edu.gvsu.cis.androidgeocalculator

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import edu.gvsu.cis.androidgeocalculator.placeholder.HistoryContent.HistoryItem
import edu.gvsu.cis.androidgeocalculator.databinding.FragmentHistoryBinding

/**
 * [RecyclerView.Adapter] that can display a [HistoryItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyHistoryItemRecyclerViewAdapter(
    private val values: List<HistoryItem>
) : RecyclerView.Adapter<MyHistoryItemRecyclerViewAdapter.ViewHolder>() {

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
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}