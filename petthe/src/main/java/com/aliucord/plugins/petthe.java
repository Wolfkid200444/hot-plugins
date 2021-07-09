package com.aliucord.plugins;

import android.content.Context;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.aliucord.Http;
import com.aliucord.Main;
import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.MessageEmbedBuilder;
import com.aliucord.entities.Plugin;
import com.aliucord.utils.ReflectUtils;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.models.commands.ApplicationCommandOption;
import com.discord.stores.StoreStream;
import com.lytefast.flexinput.model.Attachment;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class petthe extends Plugin {
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
        List<ApplicationCommandOption> arguments = Arrays.asList(UserId);
        commands.registerCommand(
                "petpet",
                "pet someone",
                arguments,
                ctx -> {
                    var c = Attachment.class;

                    var Username = ctx.getRequiredString("name");
                    var avatar = getUserAvatar(Username);
                    long parsedUserId = Long.parseLong(Username);
                String uri = null;
                try {
                    uri = imageToDataUri(parsedUserId, avatar);
                    // ctx.addAttachment()
                } catch(Throwable e) { Main.logger.error(e); }
//                    var embed = new MessageEmbedBuilder();
//                    embed.setTitle("Hello").setImage(uri).setColor(0x209CEE);
                    ctx.addAttachment(uri, "Petpet.gif");
                    return new CommandsAPI.CommandResult();
                });
    }

    private String getUserAvatar(String user) {
        long parsedUserId = Long.parseLong(user);
        var userStore = StoreStream.getUsers();
        var userinfo = userStore.getUsers().get(parsedUserId).getAvatar();
        return userinfo;
    }

    private String imageToDataUri(Long userId, String avatar) throws Throwable {
            var res = new Http.Request(url + "https://cdn.discordapp.com/avatars/"+ userId + "/" + avatar + ".png" ).execute();
            try (var baos = new ByteArrayOutputStream()) {
                res.pipe(baos);
                String b64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                return String.format("data:image/gif;base64," + b64);
            }
}

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}
