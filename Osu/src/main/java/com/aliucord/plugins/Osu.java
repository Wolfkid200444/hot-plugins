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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;

import com.aliucord.Http;
import com.aliucord.Main;
import com.aliucord.api.CommandsAPI;
import com.aliucord.api.SettingsAPI;
import com.aliucord.entities.MessageEmbedBuilder;
import com.aliucord.entities.Plugin;
import com.aliucord.fragments.SettingsPage;
import com.aliucord.plugins.Data.getUserData;
import com.aliucord.views.TextInput;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.api.message.embed.MessageEmbed;
import com.discord.models.commands.ApplicationCommandOption;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

@SuppressWarnings("unused")
public class Osu extends Plugin {
    private static final String BASE = "https://api.obamabot.ml/v2/text/osu";
    private Object getUserData;

    public Osu () {
        settingsTab = new SettingsTab(PluginSettings.class).withArgs(settings);
    }

    public static class PluginSettings extends SettingsPage {
        private final SettingsAPI settings;
        public PluginSettings(SettingsAPI settings) {
            this.settings = settings;
        }

        @Override
        @SuppressWarnings("ResultOfMethodCallIgnored")
        public void onViewBound(View view) {
            super.onViewBound(view);
            setActionBarTitle("Osu Settings");

            var context = view.getContext();
            var input = new TextInput(context);

            input.setHint("Input your Osu Name");

            var editText = input.getEditText();
            assert editText != null;

            editText.setMaxLines(1);
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.setText(String.valueOf(settings.getString("username", "")));
            editText.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                public void onTextChanged(CharSequence s, int start, int before, int count) { }
                public void afterTextChanged(Editable s) {
                    try {
                        settings.setString("username", s.toString());
                    } catch (Throwable ignored) {
                        settings.setString("username", "");
                    }
                }
            });

            addView(input);
        }
    }

    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[] {
                new Manifest.Author(
                        "Wolfie",
                        282978672711827456L
                )
        };
        manifest.description = "Search OSU Stats of someone";
        manifest.version = "1.0.3";
        manifest.updateUrl = "https://raw.githubusercontent.com/Wolfkid200444/hot-plugins/builds/updater.json";
        manifest.changelog = "Switch from API from V1 to V2 which means more access to the osu api also this means this is a unstable Version but theres like not alot breaking changes as well\n" +
                "Ive also added PlayStyle, Joined time and last seen on both text and embed( Embed has feature to check if the user is online or not)";
        manifest.changelogMedia = "https://media.discordapp.net/attachments/786033348577067029/875455571237670972/2201021.png";
        return manifest;
    }

    @Override
    public void start(Context context) {
        String key = settings.getString("username", "");
        if (key.equals("")) key = "";
        var arguments = Arrays.asList(
        new ApplicationCommandOption(ApplicationCommandType.STRING, "username", "Username of the player", null, false, false, null, null),
        new ApplicationCommandOption(ApplicationCommandType.STRING, "send", "Send stats to chat or not", null, false, false, null, null)
        );
        String finalKey = key;
        commands.registerCommand(
                "osu",
                "Search someone stats",
                arguments,
                ctx -> {
                    String user = ctx.getString("username");
                    var shouldSend = ctx.getBool("send");
                    if (shouldSend == null) shouldSend = false;
                    try {
                        getUserData data = getUser(user, finalKey);
                        return shouldSend ? userText(data) : userEmbed(data);
                    } catch (Throwable e) {
                        Main.logger.error(e);
                        return new CommandsAPI.CommandResult("Failed to fetch Data lol", null, false, "Peppi sad", "https://i.imgur.com/eRTuzdt.png");
                    }
                });
    }
    // Thanks for Xinto Lyrics Plugin https://github.com/X1nto/AliucordPlugins/blob/master/Lyrics/src/main/java/com/aliucord/plugins/Lyrics.java
    private CommandsAPI.CommandResult userText(getUserData data) {
        String dad = String.format(Locale.ENGLISH,
                                "__%s Stats__\n" +
                                        "Global Rank: **%s** (:flag_%s: #%s)\n" +
                                        "Total PP: **%s**\n" +
                                        "Play Count: **%s**\n" +
                                        "Hit Accuracy: **%s**%%\n" +
                                        "Time Played: **%s**\n" +
                                        "Playstyle: **%s**\n" +
                                        "Last seen: **%s**\n" +
                                        "Joined: **%s**",
                data.username,
                data.custom.get(0).pp_rank,
                data.country_code.toLowerCase(),
                data.custom.get(0).pp_country_rank,
                data.custom.get(0).pp_raw,
                data.custom.get(0).playcount,
                data.custom.get(0).hit_accuracy,
                data.custom.get(0).time_played,
                data.custom.get(0).playstyles,
                data.custom.get(0).format_last_visit,
                data.custom.get(0).format_join_date


        );
        return new CommandsAPI.CommandResult(dad);
    }

    private CommandsAPI.CommandResult userEmbed(getUserData data) {
        MessageEmbed embed = new MessageEmbedBuilder()
                .setTitle(data.username)
                .addField("INFO:", String.format(Locale.ENGLISH,
                          "❯ Global Rank: **%s** (:flag_%s: #%s)\n" +
                                "❯ Total PP: **%s**\n" +
                                "❯ Play Count: **%s**\n" +
                                "❯ Hit Accuracy: **%s**%%\n" +
                                "❯ Time Played: **%s**\n" +
                                "❯ Playstyle: **%s**",
                        data.custom.get(0).pp_rank,
                        data.country_code.toLowerCase(),
                        data.custom.get(0).pp_country_rank,
                        data.custom.get(0).pp_raw,
                        data.custom.get(0).playcount,
                        data.custom.get(0).hit_accuracy,
                        data.custom.get(0).time_played,
                        data.custom.get(0).playstyles
                ), false)
                .setUrl(data.user_profile)
                .setThumbnail(data.avatar_url)
                .setColor(0x209CEE)
                .setFooter(String.format("%s", data.is_online ? "They are currently Online" : "They are offline" ))
                .build();

        return new CommandsAPI.CommandResult(null, Collections.singletonList(embed), false, "Osu Stats");
    }

    @NonNull
    private getUserData getUser(String user, String storeUsername) throws Throwable {
        if (user == null) user = storeUsername;
        return Http.simpleJsonGet(BASE + "?user=" + user + "&mode=", getUserData.class);
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}