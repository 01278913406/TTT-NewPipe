package org.chicha.ttt.database.history.dao;

import org.chicha.ttt.database.BasicDAO;

public interface HistoryDAO<T> extends BasicDAO<T> {
    T getLatestEntry();
}
