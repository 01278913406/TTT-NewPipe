package org.chicha.ttt.player.resolver;

import static org.chicha.ttt.util.ListHelper.getNonTorrentStreams;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.source.MediaSource;

import org.chicha.ttt.extractor.stream.AudioStream;
import org.chicha.ttt.extractor.stream.StreamInfo;
import org.chicha.ttt.player.helper.PlayerDataSource;
import org.chicha.ttt.player.mediaitem.MediaItemTag;
import org.chicha.ttt.player.mediaitem.StreamInfoTag;
import org.chicha.ttt.util.ListHelper;

import java.util.List;

public class AudioPlaybackResolver implements PlaybackResolver {
    private static final String TAG = AudioPlaybackResolver.class.getSimpleName();

    @NonNull
    private final Context context;
    @NonNull
    private final PlayerDataSource dataSource;

    public AudioPlaybackResolver(@NonNull final Context context,
                                 @NonNull final PlayerDataSource dataSource) {
        this.context = context;
        this.dataSource = dataSource;
    }

    @Override
    @Nullable
    public MediaSource resolve(@NonNull final StreamInfo info) {
        final MediaSource liveSource = PlaybackResolver.maybeBuildLiveMediaSource(dataSource, info);
        if (liveSource != null) {
            return liveSource;
        }

        final List<AudioStream> audioStreams = getNonTorrentStreams(info.getAudioStreams());

        final int index = ListHelper.getDefaultAudioFormat(context, audioStreams);
        if (index < 0 || index >= info.getAudioStreams().size()) {
            return null;
        }

        final AudioStream audio = info.getAudioStreams().get(index);
        final MediaItemTag tag = StreamInfoTag.of(info);

        try {
            return PlaybackResolver.buildMediaSource(
                    dataSource, audio, info, PlaybackResolver.cacheKeyOf(info, audio), tag);
        } catch (final ResolverException e) {
            Log.e(TAG, "Unable to create audio source", e);
            return null;
        }
    }
}
