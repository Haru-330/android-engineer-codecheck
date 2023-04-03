/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * SearchActivity クラス
 * GitHub リポジトリを検索するためのアクティビティを実装するクラスです。
 */
class SearchActivity : AppCompatActivity(R.layout.activity_search) {

    companion object {
        lateinit var lastSearchDate: Date
    }
}
