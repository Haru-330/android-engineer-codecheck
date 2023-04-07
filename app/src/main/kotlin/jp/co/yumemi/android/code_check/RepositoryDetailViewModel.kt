package jp.co.yumemi.android.code_check

import androidx.lifecycle.ViewModel
import coil.load
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryDetailBinding

/**
 * RepositoryDetailViewModel クラス
 * @param なし
 * リポジトリの詳細情報を表示するためのUI要素を更新するためのupdateDetailUIメソッドを持っています。
 */
class RepositoryDetailViewModel : ViewModel() {
    /**
     * updateDetailUI メソッド
     * @param repositoryInfo:RepositoryInfo 表示するリポジトリの詳細情報
     * @param binding : FragmentRepositoryDetailBinding 詳細情報を表示するためのUI要素
     * 与えられたRepositoryInfoオブジェクトから、GitHubリポジトリの詳細情報を表示するためのUI要素を更新します。
     */
    fun updateDetailUI(repositoryInfo: RepositoryInfo, binding: FragmentRepositoryDetailBinding) {
        binding.ownerIconView.load(repositoryInfo.ownerIconUrl)
        binding.nameView.text = repositoryInfo.name
        binding.languageView.text = repositoryInfo.language
        binding.starsView.text = "${repositoryInfo.stargazersCount} stars"
        binding.watchersView.text = "${repositoryInfo.watchersCount} watchers"
        binding.forksView.text = "${repositoryInfo.forksCount} forks"
        binding.openIssuesView.text = "${repositoryInfo.openIssuesCount} open issues"
    }
}