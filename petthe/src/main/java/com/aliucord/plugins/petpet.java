package com.aliucord.plugins;

import android.content.Context;
import android.util.Base64;
import androidx.annotation.NonNull;

import com.aliucord.Http;
import com.aliucord.Logger;
import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.MessageEmbedBuilder;
import com.aliucord.entities.Plugin;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.api.user.User;
import com.discord.models.commands.ApplicationCommandOption;
import com.discord.stores.StoreStream;

import java.io.ByteArrayOutputStream;
import java.util.*;

@SuppressWarnings("unused")
public class petpet extends Plugin {
    private static final String url = "https://api.obamabot.ml/image/petpet?avatar=";

    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[]{
                new Manifest.Author(
                        "Wolfie",
                        282978672711827456L
                )
        };
        manifest.description = "Pet pet";
        manifest.version = "1.0.0";
        manifest.updateUrl = "https://raw.githubusercontent.com/Wolfkid200444/hot-plugins/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
        ApplicationCommandOption UserId = new ApplicationCommandOption(ApplicationCommandType.USER, "name", "Mention someone", null, true, false, null, null);
        ApplicationCommandOption shouldSendArg = new ApplicationCommandOption(ApplicationCommandType.BOOLEAN, "send", "To send output in the chat or not", null, false, false, null, null);
        List<ApplicationCommandOption> arguments = Arrays.asList(UserId, shouldSendArg);
        commands.registerCommand(
                "petpet",
                "pet someone",
                arguments,
                args -> {
                    Boolean shouldSend = (Boolean) args.get("send");
                    var Username = (String) args.get("name");
                    var avatar = getUserAvatar(Username);
                    long parsedUserId = Long.parseLong(Username);
                try {
                    var uri = imageToDataUri(parsedUserId, avatar);

                } catch(Exception e) {
                        e.printStackTrace();
                    return new CommandsAPI.CommandResult("Failed to fetch data", null, false);

                }
                        var embed = new MessageEmbedBuilder();
                        embed.setTitle("Hello").setImage(uri).setColor(0x209CEE);
                    return new CommandsAPI.CommandResult(null, Collections.singletonList(embed.build()), false);
                });
    }

    private String getUserAvatar(String user) throws Exception {
        long parsedUserId = Long.parseLong(user);
        var userStore = StoreStream.getUsers();
        var userinfo = userStore.getUsers().get(parsedUserId).getAvatar();
        return userinfo;
    }

    private String imageToDataUri(Long userId, String avatar) {
        try {
            var res = new Http.Request(url + " https://cdn.discordapp.com/avatars/"+ userId + avatar + ".png" ).execute();
            try (var baos = new ByteArrayOutputStream()) {
                res.pipe(baos);
                var b64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                return String.format("data:image/%s;base64,%s.gif", b64);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}