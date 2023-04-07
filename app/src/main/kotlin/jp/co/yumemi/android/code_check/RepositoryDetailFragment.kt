/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.SearchActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryDetailBinding

/**
 * RepositoryDetailFragment クラス
 * GitHub リポジトリの詳細を表示するためのフラグメントを実装するクラスです。
 */
class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {

    private val args: RepositoryDetailFragmentArgs by navArgs()

    private var binding: FragmentRepositoryDetailBinding? = null
    private val _binding
        get() = binding ?: throw java.lang.IllegalStateException("View binding is null")

    /**
     * onViewCreated メソッド
     * フラグメントが作成され、関連付けられたビューが作成された後に呼び出され、詳細データを表示するための UI 要素を設定する処理を行うメソッドです。
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            if (lastSearchDate == null) {
                Log.d("検索した日時", "検索されていません")
            } else {
                Log.d("検索した日時", lastSearchDate.toString())
            }
            binding = FragmentRepositoryDetailBinding.bind(view)

            var item = args.item ?: throw java.lang.IllegalStateException("Item is null")

            _binding.ownerIconView.load(item.ownerIconUrl);
            _binding.nameView.text = item.name;
            _binding.languageView.text = item.language;
            _binding.starsView.text = "${item.stargazersCount} stars";
            _binding.watchersView.text = "${item.watchersCount} watchers";
            _binding.forksView.text = "${item.forksCount} forks";
            _binding.openIssuesView.text = "${item.openIssuesCount} open issues";
        } catch (e: NullPointerException) {
            Log.e("RepositoryDetailFragment", "Failed to set up UI elements", e)
        } catch (e: RuntimeException) {
            Log.e("RepositoryDetailFragment", "Failed to set up UI elements", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            binding = null
        } catch (e: RuntimeException) {
            Log.e("RepositoryDetailFragment", "Failed to release binding", e)
        }
    }
}
