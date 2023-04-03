package jp.co.yumemi.android.code_check

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * item データクラス
 * @param name リポジトリ名
 * @param ownerIconUrl オーナーアイコンのURL
 * @param language 使用言語
 * @param stargazersCount スターカウント
 * @param watchersCount ウォッチャー数
 * @param forksCount フォーク数
 * @param openIssuesCount オープンイシュー数
 * Parcelableインターフェースを実装した、GitHubリポジトリの情報を格納するデータクラスです。
 */
// TODO item を　Item　に変える
@Parcelize
data class item(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable
