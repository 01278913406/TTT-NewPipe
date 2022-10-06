package org.chicha.ttt.local.subscription.item

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import org.chicha.ttt.R
import org.chicha.ttt.databinding.ListEmptyViewBinding

class EmptyPlaceholderItem : BindableItem<ListEmptyViewBinding>() {
    override fun getLayout(): Int = R.layout.list_empty_view
    override fun bind(viewBinding: ListEmptyViewBinding, position: Int) {}
    override fun getSpanSize(spanCount: Int, position: Int): Int = spanCount
    override fun initializeViewBinding(view: View) = ListEmptyViewBinding.bind(view)
}
