package org.chicha.ttt.database.stream

import androidx.room.ColumnInfo
import androidx.room.Embedded
import org.chicha.ttt.database.stream.model.StreamEntity
import org.chicha.ttt.database.stream.model.StreamStateEntity

data class StreamWithState(
    @Embedded
    val stream: StreamEntity,

    @ColumnInfo(name = StreamStateEntity.STREAM_PROGRESS_MILLIS)
    val stateProgressMillis: Long?
)
