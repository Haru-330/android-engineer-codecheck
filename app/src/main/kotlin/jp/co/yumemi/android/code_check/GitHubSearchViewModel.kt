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
     * searchGithubRepositoriesメソッド
     * @param inputText 検索するテキスト
     * @return RepositoryInfoのリスト 一致するリポジトリの情報を含む
     * 指定されたテキストに一致するGitHubのリポジトリを検索します。結果は、リポジトリの情報を含むRepositoryInfoのリストとして返されます。
     * エラーが発生した場合は、空のリストが返されます。
     */
    fun searchGithubRepositories(inputText: String): List<RepositoryInfo> = runBlocking {
        try {
            val response = fetchGitHubRepositories(inputText)
            val repositoryInfos = parseGitHubRepositories(response)
            updateLastSearchDate()
            return@runBlocking repositoryInfos
        } catch (e: Exception) {
            e.printStackTrace()
            return@runBlocking emptyList()
        }
    }

    /**
     * fetchGitHubRepositoriesメソッド
     * @param inputText: 検索するリポジトリ名のテキスト文字列
     * @return HttpResponse: HTTP レスポンス
     * 指定されたテキスト文字列を GitHub の API に送信し、レスポンスを受け取るために使用されます。
     */
    suspend fun fetchGitHubRepositories(inputText: String): HttpResponse {
        val client = HttpClient(Android)
        return client?.get("https://api.github.com/search/repositories") {
            header("Accept", "application/vnd.github.v3+json")
            parameter("q", inputText)
        }
    }

    /**
     * parseGitHubRepositoriesメソッド
     * @param response HTTPレスポンスオブジェクト 検索されたGitHubリポジトリの情報が含まれる。
     * @return List<RepositoryInfo リポジトリ情報のリスト
     * 引数として渡されたHTTPレスポンスオブジェクトから、検索されたGitHubリポジトリの情報を抽出し、リポジトリ情報のリストを返す。
     */
    suspend fun parseGitHubRepositories(response: HttpResponse): List<RepositoryInfo> {
        val jsonBody = JSONObject(response?.receive<String>() ?: "")

        val jsonItems = jsonBody.optJSONArray("items")

        val repositoryInfos = mutableListOf<RepositoryInfo>()

        for (i in 0 until jsonItems.length()) {
            val jsonItem = jsonItems.optJSONObject(i)
            val name = jsonItem?.optString("full_name")
            val ownerIconUrl = jsonItem?.optJSONObject("owner")?.optString("avatar_url")
            val language = jsonItem?.optString("language")
            val stargazersCount = jsonItem?.optLong("stargazers_count")
            val watchersCount = jsonItem?.optLong("watchers_count")
            val forksCount = jsonItem?.optLong("forks_count")
            val openIssuesCount = jsonItem?.optLong("open_issues_count")

            repositoryInfos.add(
                RepositoryInfo(
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
        return repositoryInfos.toList()
    }

    /**
     * updateLastSearchDateメソッド
     * lastSearchDateに現在の日付をセットする
     */
    fun updateLastSearchDate() {
        lastSearchDate = Date()
    }
}