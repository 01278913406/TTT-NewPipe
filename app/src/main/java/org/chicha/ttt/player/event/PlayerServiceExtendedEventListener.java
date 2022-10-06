package org.chicha.ttt.player.event;

import org.chicha.ttt.player.PlayerService;
import org.chicha.ttt.player.Player;

public interface PlayerServiceExtendedEventListener extends PlayerServiceEventListener {
    void onServiceConnected(Player player,
                            PlayerService playerService,
                            boolean playAfterConnect);
    void onServiceDisconnected();
}
