package org.chicha.ttt.fragments.list;

import org.chicha.ttt.fragments.ViewContract;

public interface ListViewContract<I, N> extends ViewContract<I> {
    void showListFooter(boolean show);

    void handleNextItems(N result);
}
