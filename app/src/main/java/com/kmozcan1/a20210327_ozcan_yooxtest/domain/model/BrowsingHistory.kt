package com.kmozcan1.a20210327_ozcan_yooxtest.domain.model

import java.util.*

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * Data layer model for browsing history
 */
data class BrowsingHistory(
        val brand: String,
        val category: String,
        val image: Base64,
        val  visitDateTime: Date)
