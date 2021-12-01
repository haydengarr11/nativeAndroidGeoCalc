package edu.gvsu.cis.androidgeocalculator.placeholder

import org.joda.time.DateTime
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object HistoryContent {

    val ITEMS: MutableList<HistoryItem> = ArrayList()

    fun addItem(item: HistoryItem) {
        ITEMS.add(item)
    }

    data class HistoryItem(val origLat: String, val origLng: String,
                           val destLat: String, val destLng: String, val timestamp: DateTime
    ) {
        override fun toString(): String {
            return "($origLat, $origLng)"
        }
    }
}
