/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
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
// TODO RepositoryFragment　を　RepositoryDetailFragment　に名前を変える
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

        val viewModel = GitHubSearchViewModel(context!!)

        val layoutManager = LinearLayoutManager(context!!)
        val dividerItemDecoration =
            DividerItemDecoration(context!!, layoutManager.orientation)
        val adapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: item) {
                gotoRepositoryFragment(item)
            }
        })

        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        viewModel.searchGithubRepositories(it).apply {
                            adapter.submitList(this)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    /**
     * gotoRepositoryFragment メソッド
     * @param item タップされたアイテム
     * アイテムをタップした際に呼び出され、選択されたアイテムに対応するリポジトリの詳細を表示する RepositoryDetailFragment に遷移します。
     */
    fun gotoRepositoryFragment(item: item) {
        val action = SearchFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(action)
    }
}

/**
 * diffUtil
 * @param T 型引数: item
 * DiffUtilのItemCallbackクラスを継承して、二つのitemオブジェクトを比較し、アイテムが同じであるか、コンテンツが同じであるかを判定します。
 */
val diffUtil = object : DiffUtil.ItemCallback<item>() {
    override fun areItemsTheSame(oldItem: item, newItem: item): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: item, newItem: item): Boolean {
        return oldItem == newItem
    }

}

/**
 * CustomAdapter クラス
 * @param T 型引数: item
 * RecyclerViewのListAdapterクラスを継承して、itemのリストを表示するためのカスタムアダプターです。
 * アイテムがクリックされたときに、OnItemClickListenerインターフェースのitemClickメソッドを呼び出します。
 */
class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<item, CustomAdapter.ViewHolder>(diffUtil) {
    /**
     * ViewHolderクラス
     * @param view RecyclerViewで使用されるView
     * カスタムアダプターのビューホルダーです。
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    /**
     * OnItemClickListenerインターフェース
     * itemがクリックされたときに呼び出されるメソッドを定義します。
     */
    interface OnItemClickListener {
        fun itemClick(item: item)
    }

    /**
     * onCreateViewHolderメソッド
     * @param parent RecyclerViewの親ViewGroup
     * @param viewType ビュータイプ
     * @return ViewHolder
     * 新しいViewHolderインスタンスを作成します。
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * onBindViewHolderメソッド
     * @param holder RecyclerViewのViewHolder
     * @param position リストアイテムのポジション
     * アイテムのデータをViewHolderのビューにバインドします。
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text =
            item.name

        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }
}
