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

import com.aliucord.Http;
import com.aliucord.annotations.AliucordPlugin;
import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.MessageEmbedBuilder;
import com.aliucord.entities.Plugin;
import com.aliucord.plugins.zooapi.ApiResponse;

import java.io.IOException;
import java.util.Collections;

@SuppressWarnings("unused")
@AliucordPlugin
public class Zoo extends Plugin {

    @Override
    public void start(Context context) {

        commands.registerCommand(
                "dog",
                "get a doggy image :3",
                Collections.emptyList(),
                ctx -> {
                try {
                        ApiResponse res = Http.simpleJsonGet("https://some-random-api.ml/img/dog", ApiResponse.class);
                        var eb = new MessageEmbedBuilder().setRandomColor().setTitle("is a e dog").setImage(res.link);
                        return new CommandsAPI.CommandResult(null, Collections.singletonList(eb.build()), false);
                    } catch (IOException ex) {
                        return new CommandsAPI.CommandResult("No Dog founds", null, false);
                    }
                });
        commands.registerCommand(
                "cat",
                "get a Cat image, meow",
                Collections.emptyList(),
                ctx -> {
                    try {
                        ApiResponse res = Http.simpleJsonGet(" https://some-random-api.ml/img/cat", ApiResponse.class);
                        var eb = new MessageEmbedBuilder().setRandomColor().setTitle("is a e cat").setImage(res.link);
                        return new CommandsAPI.CommandResult(null, Collections.singletonList(eb.build()), false);
                    } catch (IOException ex) {
                        return new CommandsAPI.CommandResult("No Cats founds", null, false);
                    }
                });
        commands.registerCommand(
                "panda",
                "get a PaNde ImaGE",
                Collections.emptyList(),
                ctx -> {
                    try {
                        ApiResponse res = Http.simpleJsonGet("https://some-random-api.ml/img/panda", ApiResponse.class);
                        var eb = new MessageEmbedBuilder().setRandomColor().setTitle("is a e PanDe").setImage(res.link);
                        return new CommandsAPI.CommandResult(null, Collections.singletonList(eb.build()), false);
                    } catch (IOException ex) {
                        return new CommandsAPI.CommandResult("No Pandas founds", null, false);
                    }
                });
        commands.registerCommand(
                "redpanda",
                "get a Red PandE image",
                Collections.emptyList(),
                ctx -> {
                    try {
                        ApiResponse res = Http.simpleJsonGet(" https://some-random-api.ml/img/red_panda", ApiResponse.class);
                        var eb = new MessageEmbedBuilder().setRandomColor().setTitle("is a e Red Mystical Pande").setImage(res.link);
                        return new CommandsAPI.CommandResult(null, Collections.singletonList(eb.build()), false);
                    } catch (IOException ex) {
                        return new CommandsAPI.CommandResult("No Red Pandas founds", null, false);
                    }
                });
        commands.registerCommand(
                "fox",
                "get a Fox Image",
                Collections.emptyList(),
                ctx -> {
                    try {
                        ApiResponse res = Http.simpleJsonGet("https://some-random-api.ml/img/fox", ApiResponse.class);
                        var eb = new MessageEmbedBuilder().setRandomColor().setTitle("is a e Fox").setImage(res.link);
                        return new CommandsAPI.CommandResult(null, Collections.singletonList(eb.build()), false);
                    } catch (IOException ex) {
                        return new CommandsAPI.CommandResult("No Foxes founds", null, false);
                    }
                });
        commands.registerCommand(
                "bird",
                "get a Bird Image",
                Collections.emptyList(),
                ctx -> {
                    try {
                        ApiResponse res = Http.simpleJsonGet("https://some-random-api.ml/img/birb", ApiResponse.class);
                        var eb = new MessageEmbedBuilder().setRandomColor().setTitle("is a e BiRde").setImage(res.link);
                        return new CommandsAPI.CommandResult(null, Collections.singletonList(eb.build()), false);
                    } catch (IOException ex) {
                        return new CommandsAPI.CommandResult("No Birds founds", null, false);
                    }
                });
        commands.registerCommand(
                "koala",
                "get a Koala image",
                Collections.emptyList(),
                ctx -> {
                    try {
                        ApiResponse res = Http.simpleJsonGet("https://some-random-api.ml/img/koala", ApiResponse.class);
                        var eb = new MessageEmbedBuilder().setRandomColor().setTitle("is a e Koale").setImage(res.link);
                        return new CommandsAPI.CommandResult(null, Collections.singletonList(eb.build()), false);
                    } catch (IOException ex) {
                        return new CommandsAPI.CommandResult("No Koala founds", null, false);
                    }
                });
        commands.registerCommand(
                "kangaroo",
                "get a Kangaroo image",
                Collections.emptyList(),
                ctx -> {
                    try {
                        ApiResponse res = Http.simpleJsonGet("https://some-random-api.ml/img/kangaroo", ApiResponse.class);
                        var eb = new MessageEmbedBuilder().setRandomColor().setTitle("is a e Jumpy Boi").setImage(res.link);
                        return new CommandsAPI.CommandResult(null, Collections.singletonList(eb.build()), false);
                    } catch (IOException ex) {
                        return new CommandsAPI.CommandResult("No Jumpy Bois Found founds", null, false);
                    }
                });
        commands.registerCommand(
                "racoon",
                "get a Racoon image",
                Collections.emptyList(),
                ctx -> {
                    try {
                        ApiResponse res = Http.simpleJsonGet("https://some-random-api.ml/img/racoon", ApiResponse.class);
                        var eb = new MessageEmbedBuilder().setRandomColor().setTitle("is a e Stinky Boi").setImage(res.link);
                        return new CommandsAPI.CommandResult(null, Collections.singletonList(eb.build()), false);
                    } catch (IOException ex) {
                        return new CommandsAPI.CommandResult("No Stinky Bois founds", null, false);
                    }
                });
        commands.registerCommand(
                "whale",
                "get a Whale image",
                Collections.emptyList(),
                ctx -> {
                    try {
                        ApiResponse res = Http.simpleJsonGet("https://some-random-api.ml/img/whale", ApiResponse.class);
                        var eb = new MessageEmbedBuilder().setRandomColor().setTitle("is a Big uh Ocean Dolphine but bigger").setImage(res.link);
                        return new CommandsAPI.CommandResult(null, Collections.singletonList(eb.build()), false);
                    } catch (IOException ex) {
                        return new CommandsAPI.CommandResult("No Whales found", null, false);
                    }
                });

    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}