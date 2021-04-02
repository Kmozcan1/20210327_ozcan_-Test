package com.kmozcan1.a20210327_ozcan_yooxtest.domain.model

import java.util.*

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * Data layer model for browsing history
 */
data class BrowsingHistory(
        val code10: String,
        val brand: String,
        val category: String,
        val imageUrl: String,
        val  visitDateTime: Date)
