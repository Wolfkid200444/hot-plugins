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
import java.util.Collections;

@SuppressWarnings("unused")
@AliucordPlugin
public class ObamaAPI extends Plugin {
    private static final String TEXT_BASE = "https://api.obamabot.me/v1/";

    @Override
    public void start(Context context) {
        var Images = Collections.singletonList(
                Utils.createCommandOption(
                        ApplicationCommandType.USER,
                        "name",
                        "Username of the player",
                        null,
                        true)
        );
        var Sarguments = Arrays.asList(
                Utils.createCommandOption(ApplicationCommandType.STRING, "username", "Username of the player", null, false, false, null, null)
        );


        commands.registerCommand(
                "3000",
                "Is been 3,000 Years",
                Images,
                ctx -> {
                    User user = ctx.getRequiredUser("username");
                    String avatar = IconUtils.getForUser(user);
                    File file = null;
                    try {
                        file = The3000(avatar, context);
                    } catch (Throwable e) {
                        Main.logger.error(e);
                    }
                    assert file != null;
                    ctx.addAttachment(Uri.fromFile(file).toString(), "3000.png");
                    return new CommandsAPI.CommandResult("");
                });
    }

    private File The3000(String avatar, Context mContext) throws Throwable {
        var res = new Http.Request(TEXT_BASE + "image/3000" + avatar.replace("webp", "png")).execute();
        File f = File.createTempFile("temp", ".png", mContext.getCacheDir());
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