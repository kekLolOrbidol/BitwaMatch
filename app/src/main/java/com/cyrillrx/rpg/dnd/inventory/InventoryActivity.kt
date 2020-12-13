package com.cyrillrx.rpg.dnd.inventory

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cyrillrx.rpg.AssetReader
import com.cyrillrx.rpg.api.inventory.InventoryItem
import com.cyrillrx.rpg.template.SearchListActivity
import com.cyrillrx.utils.deserialize
import java.util.Locale

/**
 * @author Cyril Leroux
 *         Created on 30/03/2020.
 */
class InventoryActivity : SearchListActivity<InventoryAdapter>() {

    override val adapter = InventoryAdapter()
    private val locale by lazy { Locale.ROOT }

    override fun createLayoutManager(): RecyclerView.LayoutManager =
        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    override fun sendRequest() {

        val magicalItems: Inventory = loadFromFile()
        updateData(magicalItems)
    }

    override fun applyFilter(query: String) {

        val magicalItems: Inventory = loadFromFile()
        updateData(magicalItems.filter(query))
    }

    private fun updateData(magicalItems: Inventory) {

        startLoading()

        adapter.clear()
        adapter.addAll(magicalItems)

        stopLoading()
    }

    private fun loadFromFile(): Inventory {
        val serializedInventory = AssetReader.readAsString(this, "objets-magiques.json")
        return serializedInventory?.deserialize() ?: Inventory()
    }

    private fun Inventory.filter(query: String): Inventory = filterTo(Inventory()) { item -> item.filter(query) }

    private fun InventoryItem.filter(query: String): Boolean {
        val lowerCaseQuery = query.toLowerCase(locale)
        return title.toLowerCase(locale).contains(lowerCaseQuery) ||
                content.toLowerCase(locale).contains(lowerCaseQuery)
    }

    class Inventory : ArrayList<InventoryItem>()
}