package org.chicha.ttt.player.event;

import com.google.android.exoplayer2.PlaybackParameters;

import org.chicha.ttt.extractor.stream.StreamInfo;
import org.chicha.ttt.player.playqueue.PlayQueue;

public interface PlayerEventListener {
    void onQueueUpdate(PlayQueue queue);
    void onPlaybackUpdate(int state, int repeatMode, boolean shuffled,
                          PlaybackParameters parameters);
    void onProgressUpdate(int currentProgress, int duration, int bufferPercent);
    void onMetadataUpdate(StreamInfo info, PlayQueue queue);
    void onServiceStopped();
}
