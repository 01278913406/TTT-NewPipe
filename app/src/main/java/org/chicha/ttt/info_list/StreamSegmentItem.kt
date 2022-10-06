package org.chicha.ttt.info_list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import org.chicha.ttt.R
import org.chicha.ttt.extractor.stream.StreamSegment
import org.chicha.ttt.util.Localization
import org.chicha.ttt.util.PicassoHelper

class StreamSegmentItem(
    private val item: StreamSegment,
    private val onClick: StreamSegmentAdapter.StreamSegmentListener
) : Item<GroupieViewHolder>() {

    companion object {
        const val PAYLOAD_SELECT = 1
    }

    var isSelected = false

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        item.previewUrl?.let {
            PicassoHelper.loadThumbnail(it)
                .into(viewHolder.root.findViewById<ImageView>(R.id.previewImage))
        }
        viewHolder.root.findViewById<TextView>(R.id.textViewTitle).text = item.title
        if (item.channelName == null) {
            viewHolder.root.findViewById<TextView>(R.id.textViewChannel).visibility = View.GONE
            // When the channel name is displayed there is less space
            // and thus the segment title needs to be only one line height.
            // But when there is no channel name displayed, the title can be two lines long.
            // The default maxLines value is set to 1 to display all elements in the AS preview,
            viewHolder.root.findViewById<TextView>(R.id.textViewTitle).maxLines = 2
        } else {
            viewHolder.root.findViewById<TextView>(R.id.textViewChannel).text = item.channelName
            viewHolder.root.findViewById<TextView>(R.id.textViewChannel).visibility = View.VISIBLE
        }
        viewHolder.root.findViewById<TextView>(R.id.textViewStartSeconds).text =
            Localization.getDurationString(item.startTimeSeconds.toLong())
        viewHolder.root.setOnClickListener { onClick.onItemClick(this, item.startTimeSeconds) }
        viewHolder.root.isSelected = isSelected
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(PAYLOAD_SELECT)) {
            viewHolder.root.isSelected = isSelected
            return
        }
        super.bind(viewHolder, position, payloads)
    }

    override fun getLayout() = R.layout.item_stream_segment
}
