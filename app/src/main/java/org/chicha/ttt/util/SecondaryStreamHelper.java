package org.chicha.ttt.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.chicha.ttt.extractor.MediaFormat;
import org.chicha.ttt.extractor.stream.AudioStream;
import org.chicha.ttt.extractor.stream.Stream;
import org.chicha.ttt.extractor.stream.VideoStream;
import org.chicha.ttt.util.StreamItemAdapter.StreamSizeWrapper;

import java.util.List;

public class SecondaryStreamHelper<T extends Stream> {
    private final int position;
    private final StreamSizeWrapper<T> streams;

    public SecondaryStreamHelper(@NonNull final StreamSizeWrapper<T> streams,
                                 final T selectedStream) {
        this.streams = streams;
        this.position = streams.getStreamsList().indexOf(selectedStream);
        if (this.position < 0) {
            throw new RuntimeException("selected stream not found");
        }
    }

    /**
     * Find the correct audio stream for the desired video stream.
     *
     * @param audioStreams list of audio streams
     * @param videoStream  desired video ONLY stream
     * @return selected audio stream or null if a candidate was not found
     */
    @Nullable
    public static AudioStream getAudioStreamFor(@NonNull final List<AudioStream> audioStreams,
                                                @NonNull final VideoStream videoStream) {
        final MediaFormat mediaFormat = videoStream.getFormat();
        if (mediaFormat == null) {
            return null;
        }

        switch (mediaFormat) {
            case WEBM:
            case MPEG_4:// ¿is mpeg-4 DASH?
                break;
            default:
                return null;
        }

        final boolean m4v = (mediaFormat == MediaFormat.MPEG_4);

        for (final AudioStream audio : audioStreams) {
            if (audio.getFormat() == (m4v ? MediaFormat.M4A : MediaFormat.WEBMA)) {
                return audio;
            }
        }

        if (m4v) {
            return null;
        }

        // retry, but this time in reverse order
        for (int i = audioStreams.size() - 1; i >= 0; i--) {
            final AudioStream audio = audioStreams.get(i);
            if (audio.getFormat() == MediaFormat.WEBMA_OPUS) {
                return audio;
            }
        }

        return null;
    }

    public T getStream() {
        return streams.getStreamsList().get(position);
    }

    public long getSizeInBytes() {
        return streams.getSizeInBytes(position);
    }
}
