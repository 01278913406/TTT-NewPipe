package org.chicha.ttt.database;

import static org.chicha.ttt.database.Migrations.DB_VER_5;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import org.chicha.ttt.database.feed.dao.FeedDAO;
import org.chicha.ttt.database.feed.dao.FeedGroupDAO;
import org.chicha.ttt.database.feed.model.FeedEntity;
import org.chicha.ttt.database.feed.model.FeedGroupEntity;
import org.chicha.ttt.database.feed.model.FeedGroupSubscriptionEntity;
import org.chicha.ttt.database.feed.model.FeedLastUpdatedEntity;
import org.chicha.ttt.database.history.dao.SearchHistoryDAO;
import org.chicha.ttt.database.history.dao.StreamHistoryDAO;
import org.chicha.ttt.database.history.model.SearchHistoryEntry;
import org.chicha.ttt.database.history.model.StreamHistoryEntity;
import org.chicha.ttt.database.playlist.dao.PlaylistDAO;
import org.chicha.ttt.database.playlist.dao.PlaylistRemoteDAO;
import org.chicha.ttt.database.playlist.dao.PlaylistStreamDAO;
import org.chicha.ttt.database.playlist.model.PlaylistEntity;
import org.chicha.ttt.database.playlist.model.PlaylistRemoteEntity;
import org.chicha.ttt.database.playlist.model.PlaylistStreamEntity;
import org.chicha.ttt.database.stream.dao.StreamDAO;
import org.chicha.ttt.database.stream.dao.StreamStateDAO;
import org.chicha.ttt.database.stream.model.StreamEntity;
import org.chicha.ttt.database.stream.model.StreamStateEntity;
import org.chicha.ttt.database.subscription.SubscriptionDAO;
import org.chicha.ttt.database.subscription.SubscriptionEntity;

@TypeConverters({Converters.class})
@Database(
        entities = {
                SubscriptionEntity.class, SearchHistoryEntry.class,
                StreamEntity.class, StreamHistoryEntity.class, StreamStateEntity.class,
                PlaylistEntity.class, PlaylistStreamEntity.class, PlaylistRemoteEntity.class,
                FeedEntity.class, FeedGroupEntity.class, FeedGroupSubscriptionEntity.class,
                FeedLastUpdatedEntity.class
        },
        version = DB_VER_5
)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "newpipe.db";

    public abstract SearchHistoryDAO searchHistoryDAO();

    public abstract StreamDAO streamDAO();

    public abstract StreamHistoryDAO streamHistoryDAO();

    public abstract StreamStateDAO streamStateDAO();

    public abstract PlaylistDAO playlistDAO();

    public abstract PlaylistStreamDAO playlistStreamDAO();

    public abstract PlaylistRemoteDAO playlistRemoteDAO();

    public abstract FeedDAO feedDAO();

    public abstract FeedGroupDAO feedGroupDAO();

    public abstract SubscriptionDAO subscriptionDAO();
}
