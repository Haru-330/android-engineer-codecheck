/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentSearchBinding

/**
 * SearchFragment クラス
 *　このクラスは、GitHub リポジトリを検索するための Fragment です。
 * 検索欄に検索ワードを入力すると、RecyclerView に検索結果が表示されます。
 * リスト内のアイテムをタップすると、選択されたアイテムに対応するリポジトリの詳細を表示する RepositoryDetailFragment に遷移します。
 */
class SearchFragment : Fragment(R.layout.fragment_search) {
    /**
     * onViewCreated メソッド
     * @param view Fragment の View
     * @param savedInstanceState 状態を保存する Bundle オブジェクト
     * Fragment の View が生成された際に呼び出されます。
     *　ViewBinding を使用して View の参照を取得し、RecyclerView と Adapter を初期化しています。
     * 検索欄に入力されたワードで検索を行い、検索結果を RecyclerView に反映させます。
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSearchBinding.bind(view)

        val viewModel = GitHubSearchViewModel(view.context)

        val layoutManager = LinearLayoutManager(view.context)
        val dividerItemDecoration = DividerItemDecoration(view.context, layoutManager.orientation)
        val adapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(repositoryInfo: RepositoryInfo) {
                gotoRepositoryDetailFragment(repositoryInfo)
            }
        })

        binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                try {
                    val searchQuery = binding.searchInputText.text.toString().trim()
                    if (searchQuery.isEmpty()) {
                        throw IllegalArgumentException("検索クエリが空です")
                    }
                    viewModel.searchGithubRepositories(searchQuery).apply {
                        adapter.submitList(this)
                    }
                    binding.recyclerView.also {
                        it.layoutManager = layoutManager
                        it.addItemDecoration(dividerItemDecoration)
                        it.adapter = adapter
                    }
                    return@setOnEditorActionListener true
                } catch (e: IllegalArgumentException) {
                    Toast.makeText(view.context, "検索クエリが空です。再度入力してください", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(view.context, "入力が不正です。再度入力してください", Toast.LENGTH_SHORT).show()
                }
                return@setOnEditorActionListener false
            }
            false
        }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    /**
     * gotoRepositoryDetailFragment メソッド
     * @param Item タップされたアイテム
     * アイテムをタップした際に呼び出され、選択されたアイテムに対応するリポジトリの詳細を表示する RepositoryDetailFragment に遷移します。
     */
    private fun gotoRepositoryDetailFragment(repositoryInfo: RepositoryInfo) {
        val action =
            SearchFragmentDirections.actionRepositoriesFragmentToRepositoryDetailFragment(item = repositoryInfo)
        findNavController().navigate(action)
    }
}

/**
 * diffUtil
 * @param Item
 * DiffUtilのItemCallbackクラスを継承して、二つのitemオブジェクトを比較し、アイテムが同じであるか、コンテンツが同じであるかを判定します。
 */
val diffUtil = object : DiffUtil.ItemCallback<RepositoryInfo>() {
    override fun areItemsTheSame(
        oldRepositoryInfo: RepositoryInfo, newRepositoryInfo: RepositoryInfo
    ): Boolean {
        return oldRepositoryInfo.name == newRepositoryInfo.name
    }

    override fun areContentsTheSame(
        oldRepositoryInfo: RepositoryInfo, newRepositoryInfo: RepositoryInfo
    ): Boolean {
        return oldRepositoryInfo == newRepositoryInfo
    }

}