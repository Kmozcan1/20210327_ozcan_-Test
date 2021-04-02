package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model

import java.util.*

/**
 * Created by Kadir Mert Özcan on 02-Apr-21.
 *
 * Presentation layer model for BrowsingHistory
 */
data class BrowsingHistoryUiModel (
        val brand: String,
        val category: String,
        val image: Base64,
        val visitDateTime: Date)