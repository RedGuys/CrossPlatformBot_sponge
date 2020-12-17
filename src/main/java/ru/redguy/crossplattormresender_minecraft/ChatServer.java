package ru.redguy.crossplattormresender_minecraft;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;

public class ChatServer extends WebSocketServer {

    public ChatServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public ChatServer(InetSocketAddress address) {
        super(address);
    }

    public ChatServer(int port, Draft_6455 draft) {
        super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        JSONObject jsonObject = new JSONObject(message);
        if(jsonObject.getInt("status") == 301) {
            sendToAll(jsonObject.getString("text"));
        }
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
    }

    @Override
    public void onStart() {
        setConnectionLostTimeout(100);
    }

    public static void sendToAll(String text) {
        for (Player onlinePlayer : Sponge.getServer().getOnlinePlayers()) {
            onlinePlayer.sendMessage(Text.of(text));
        }
    }

}