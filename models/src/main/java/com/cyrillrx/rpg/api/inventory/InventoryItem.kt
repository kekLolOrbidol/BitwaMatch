package com.cyrillrx.rpg.api.inventory

/**
 * @author Cyril Leroux
 *         Created on 30/03/2020.
 */
class InventoryItem(
    val title: String,
    val content: String,
    val type: String,
    val subtype: String?,
    val rarity: String,
    val attunement: String?,
    val header: Header
) {
    class Header(val magicitem: MagicalItem, val taxonomy: Taxonomy) {

        class MagicalItem(
            val type: String,
            val rarity: String,
            val attunement: String?
        )

        class Taxonomy(
            val category: Array<String>,
            val source: Array<String>
        )
    }
}