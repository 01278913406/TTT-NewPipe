package org.chicha.ttt.local.subscription.item

import android.content.Context
import android.os.Parcelable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.viewbinding.BindableItem
import com.xwray.groupie.viewbinding.GroupieViewHolder
import org.chicha.ttt.R
import org.chicha.ttt.databinding.FeedItemCarouselBinding
import org.chicha.ttt.local.subscription.decoration.FeedGroupCarouselDecoration

class FeedGroupCarouselItem(
    context: Context,
    private val carouselAdapter: GroupAdapter<GroupieViewHolder<FeedItemCarouselBinding>>
) : BindableItem<FeedItemCarouselBinding>() {
    private val feedGroupCarouselDecoration = FeedGroupCarouselDecoration(context)

    private var linearLayoutManager: LinearLayoutManager? = null
    private var listState: Parcelable? = null

    override fun getLayout() = R.layout.feed_item_carousel

    fun onSaveInstanceState(): Parcelable? {
        listState = linearLayoutManager?.onSaveInstanceState()
        return listState
    }

    fun onRestoreInstanceState(state: Parcelable?) {
        linearLayoutManager?.onRestoreInstanceState(state)
        listState = state
    }

    override fun initializeViewBinding(view: View): FeedItemCarouselBinding {
        val viewHolder = FeedItemCarouselBinding.bind(view)

        linearLayoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

        viewHolder.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = carouselAdapter
            addItemDecoration(feedGroupCarouselDecoration)
        }

        return viewHolder
    }

    override fun bind(viewBinding: FeedItemCarouselBinding, position: Int) {
        viewBinding.recyclerView.apply { adapter = carouselAdapter }
        linearLayoutManager?.onRestoreInstanceState(listState)
    }

    override fun unbind(viewHolder: GroupieViewHolder<FeedItemCarouselBinding>) {
        super.unbind(viewHolder)

        listState = linearLayoutManager?.onSaveInstanceState()
    }
}
