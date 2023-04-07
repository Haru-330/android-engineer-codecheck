/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.content.Context
import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.SearchActivity.Companion.lastSearchDate
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.util.*

/**
 * GitHubSearchViewModel クラス
 * @param context Applicationのコンテキスト
 * GitHubリポジトリを検索するためのViewModelクラスです。
 * 検索キーワードを用いて、GitHub APIを呼び出し、該当するリポジトリの情報を取得します。
 * 取得した情報をitemクラスのオブジェクトに変換し、リストに格納して返却します。
 */
class GitHubSearchViewModel(
    val context: Context
) : ViewModel() {

    /**
     * searchGithubRepositories
     * @param inputText 検索キーワード
     * @return 検索結果のリスト
     * GitHubリポジトリを検索するメソッドです。
     * 検索キーワードを用いて、GitHub APIを呼び出し、該当するリポジトリの情報を取得します。
     * 取得した情報をitemクラスのオブジェクトに変換し、リストに格納して返却します。
     */
    val client = HttpClient(Android)
    fun searchGithubRepositories(inputText: String): List<Item> = runBlocking {

        val response: HttpResponse? = client?.get("https://api.github.com/search/repositories") {
            header("Accept", "application/vnd.github.v3+json")
            parameter("q", inputText)
        }

        val jsonBody = JSONObject(response?.receive<String>() ?: "")

        val jsonItems = jsonBody.optJSONArray("items")

        val items = mutableListOf<Item>()

        for (i in 0 until jsonItems.length()) {
            val jsonItem = jsonItems.optJSONObject(i)
            val name = jsonItem?.optString("full_name")
            val ownerIconUrl = jsonItem?.optJSONObject("owner")?.optString("avatar_url")
            val language = jsonItem?.optString("language")
            val stargazersCount = jsonItem?.optLong("stargazers_count")
            val watchersCount = jsonItem?.optLong("watchers_count")
            val forksCount = jsonItem?.optLong("forks_count")
            val openIssuesCount = jsonItem?.optLong("open_issues_count")

            items.add(
                Item(
                    name = name ?: "",
                    ownerIconUrl = ownerIconUrl ?: "",
                    language = context.getString(R.string.written_language, language),
                    stargazersCount = stargazersCount ?: 0,
                    watchersCount = watchersCount ?: 0,
                    forksCount = forksCount ?: 0,
                    openIssuesCount = openIssuesCount ?: 0
                )
            )
        }

        lastSearchDate = Date()

        return@runBlocking items.toList()
    }

    override fun onCleared() {
        super.onCleared()
        client.close()
    }
}