package jp.co.yumemi.android.code_check

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * CustomAdapter クラス
 * @param Item
 * RecyclerViewのListAdapterクラスを継承して、itemのリストを表示するためのカスタムアダプターです。
 * アイテムがクリックされたときに、OnItemClickListenerインターフェースのitemClickメソッドを呼び出します。
 */
class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<RepositoryInfo, CustomAdapter.ViewHolder>(diffUtil) {
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
        fun itemClick(item: RepositoryInfo)
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
            .inflate(R.layout.layout_repository_info, parent, false)
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
        holder.itemView.findViewById<TextView>(R.id.repositoryNameView).text = item.name

        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }
}