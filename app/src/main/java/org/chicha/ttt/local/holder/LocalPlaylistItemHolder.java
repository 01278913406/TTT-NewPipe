package org.chicha.ttt.local.holder;

import android.view.View;
import android.view.ViewGroup;

import org.chicha.ttt.database.LocalItem;
import org.chicha.ttt.database.playlist.PlaylistMetadataEntry;
import org.chicha.ttt.local.LocalItemBuilder;
import org.chicha.ttt.local.history.HistoryRecordManager;
import org.chicha.ttt.util.PicassoHelper;
import org.chicha.ttt.util.Localization;

import java.time.format.DateTimeFormatter;

public class LocalPlaylistItemHolder extends PlaylistItemHolder {
    public LocalPlaylistItemHolder(final LocalItemBuilder infoItemBuilder, final ViewGroup parent) {
        super(infoItemBuilder, parent);
    }

    LocalPlaylistItemHolder(final LocalItemBuilder infoItemBuilder, final int layoutId,
                            final ViewGroup parent) {
        super(infoItemBuilder, layoutId, parent);
    }

    @Override
    public void updateFromItem(final LocalItem localItem,
                               final HistoryRecordManager historyRecordManager,
                               final DateTimeFormatter dateTimeFormatter) {
        if (!(localItem instanceof PlaylistMetadataEntry)) {
            return;
        }
        final PlaylistMetadataEntry item = (PlaylistMetadataEntry) localItem;

        itemTitleView.setText(item.name);
        itemStreamCountView.setText(Localization.localizeStreamCountMini(
                itemStreamCountView.getContext(), item.streamCount));
        itemUploaderView.setVisibility(View.INVISIBLE);

        PicassoHelper.loadPlaylistThumbnail(item.thumbnailUrl).into(itemThumbnailView);

        super.updateFromItem(localItem, historyRecordManager, dateTimeFormatter);
    }
}
