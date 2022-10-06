package org.chicha.ttt.database.playlist;

import org.chicha.ttt.database.LocalItem;
import org.chicha.ttt.database.playlist.model.PlaylistRemoteEntity;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface PlaylistLocalItem extends LocalItem {
    String getOrderingName();

    static List<PlaylistLocalItem> merge(
            final List<PlaylistMetadataEntry> localPlaylists,
            final List<PlaylistRemoteEntity> remotePlaylists) {
        return Stream.concat(localPlaylists.stream(), remotePlaylists.stream())
                .sorted(Comparator.comparing(PlaylistLocalItem::getOrderingName,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
                .collect(Collectors.toList());
    }
}
