package rs.jamie.serverpatches.utils;

import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisconnect;

public class InvalidPacketKicker {
    public void kickUser(User user, String reason) {
        user.sendPacket(new WrapperPlayServerDisconnect(TextUtil.formatColor(reason)));
        user.closeConnection();

    }
}
