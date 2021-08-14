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
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.Plugin;
import java.util.*;

@SuppressWarnings("unused")
public class clapclap extends Plugin {

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
        manifest.description = "Clap Clap?";
        manifest.version = "1.0.6";
        manifest.updateUrl = "https://raw.githubusercontent.com/Wolfkid200444/hot-plugins/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {

        commands.registerCommand(
                "clap",
                "Clap your messages",
                Collections.singletonList(CommandsAPI.requiredMessageOption),
                ctx -> {
            String msg = ctx.getRequiredString("message");
            String[] Messafe = msg.trim().split(" ");
            String Clap;
            if(Messafe.length <= 1) {
                String[] x = msg.split("");
                Clap = TextUtils.join(" \uD83D\uDC4F ", x);
            } else {
                Clap = TextUtils.join(" \uD83D\uDC4F ", msg.split(" ", 0));
            }
            return new CommandsAPI.CommandResult(Clap);
        });

    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}