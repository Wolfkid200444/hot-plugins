/*
 * Wolf's Aliucord Plugins
 * Copyright (C) 2021 Wolfkid200444
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.aliucord.plugins;

import android.content.Context;
import android.net.Uri;

import com.aliucord.Http;
import com.aliucord.Main;
import com.aliucord.Utils;
import com.aliucord.annotations.AliucordPlugin;
import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.Plugin;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.models.user.User;
import com.discord.utilities.icon.IconUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

@SuppressWarnings("unused")
@AliucordPlugin
public class PetPet extends Plugin {
    private static final String url = "https://api.obamabot.ml/v1/image/petpet?avatar=";


    @Override
    public void start(Context context) {
        var arguments = Arrays.asList(
       Utils.createCommandOption(ApplicationCommandType.USER, "name", "The user to pet", null, true, false)
        );
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
