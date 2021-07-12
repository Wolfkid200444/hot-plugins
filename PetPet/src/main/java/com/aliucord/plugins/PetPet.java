package com.aliucord.plugins;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.aliucord.Http;
import com.aliucord.Main;
import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.Plugin;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.models.commands.ApplicationCommandOption;
import com.discord.models.user.User;
import com.discord.utilities.icon.IconUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class PetPet extends Plugin {
    private static final String url = "https://api.obamabot.ml/image/petpet?avatar=";

    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[] {
                new Manifest.Author(
                        "Wolfie",
                        282978672711827456L
                ),
                new Manifest.Author(
                        "Alyxia",
                        465702500146610176L
                )
        };
        manifest.description = "Pet pet";
        manifest.version = "1.1.1";
        manifest.updateUrl = "https://raw.githubusercontent.com/Wolfkid200444/hot-plugins/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
        var arguments = new ArrayList<ApplicationCommandOption>();
        arguments.add(new ApplicationCommandOption(ApplicationCommandType.USER, "name", "The user to pet", null, true, false, null, null));
        commands.registerCommand(
                "petpet",
                "pet someone",
                arguments,
                ctx -> {
                    User user = ctx.getRequiredUser("name");
                    String avatar = IconUtils.getForUser(user);
                    File file = null;
                    try {
                        file = imageToDataUri(avatar, context);
                    } catch (Throwable e) {
                        Main.logger.error(e);
                    }
                    assert file != null;
                    ctx.addAttachment(Uri.fromFile(file).toString(), "petpet.gif");
                    return new CommandsAPI.CommandResult("");
                });
    }

    private File imageToDataUri(String avatar, Context mContext) throws Throwable {
        var res = new Http.Request(url + avatar.replace("webp", "png")).execute();
        File f = File.createTempFile("temp", ".gif", mContext.getCacheDir());
        try (var fos = new FileOutputStream(f)) {
            res.pipe(fos);
        }
        f.deleteOnExit();
        return f;
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}
