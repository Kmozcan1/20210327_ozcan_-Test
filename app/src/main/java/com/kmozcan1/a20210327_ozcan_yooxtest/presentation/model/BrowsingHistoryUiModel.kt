package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model

import java.util.*

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * Presentation layer model for BrowsingHistory
 */
data class BrowsingHistoryUiModel (
        val code10: String,
        val brand: String,
        val category: String,
        val imageUrl: String,
        val visitDateTime: Date)
