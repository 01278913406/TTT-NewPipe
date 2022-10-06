package org.chicha.ttt.player.playqueue;


import org.chicha.ttt.extractor.Page;
import org.chicha.ttt.extractor.channel.ChannelInfo;
import org.chicha.ttt.extractor.stream.StreamInfoItem;
import org.chicha.ttt.util.ExtractorHelper;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public final class ChannelPlayQueue extends AbstractInfoPlayQueue<ChannelInfo> {

    public ChannelPlayQueue(final ChannelInfo info) {
        super(info);
    }

    public ChannelPlayQueue(final int serviceId,
                            final String url,
                            final Page nextPage,
                            final List<StreamInfoItem> streams,
                            final int index) {
        super(serviceId, url, nextPage, streams, index);
    }

    @Override
    protected String getTag() {
        return "ChannelPlayQueue@" + Integer.toHexString(hashCode());
    }

    @Override
    public void fetch() {
        if (this.isInitial) {
            ExtractorHelper.getChannelInfo(this.serviceId, this.baseUrl, false)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getHeadListObserver());
        } else {
            ExtractorHelper.getMoreChannelItems(this.serviceId, this.baseUrl, this.nextPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getNextPageObserver());
        }
    }
}
