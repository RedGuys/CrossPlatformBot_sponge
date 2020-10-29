package ru.redguy.crossplattormbot_minecraft;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.plugin.Plugin;

import java.net.UnknownHostException;

@Plugin(
        id = "crossplattormbot_minecraft",
        name = "Crossplattormbot=",
        description = "bot in mine!",
        url = "https://redguy.ru",
        authors = {
                "RedGuy"
        }
)
public class Crossplattormbot {

    @Inject
    private Logger logger;

    ChatServer s = null;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        try {
            s = new ChatServer(80);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        assert s != null;
        s.start();
    }

    @Listener
    public void onMessage(MessageEvent event) {
        if(event.getCause().containsType(Player.class)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 301);
            jsonObject.put("text", event.getOriginalMessage().toPlain());
            jsonObject.put("name", event.getCause().first(Player.class).get().getName());
            s.broadcast(jsonObject.toString());
        }
    }
}
