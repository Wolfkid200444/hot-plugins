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
import com.aliucord.Utils;
import com.aliucord.annotations.AliucordPlugin;
import com.aliucord.api.CommandsAPI;
import com.aliucord.api.SettingsAPI;
import com.aliucord.entities.MessageEmbedBuilder;
import com.aliucord.entities.Plugin;
import com.aliucord.fragments.SettingsPage;
import com.aliucord.plugins.Data.getUserData;
import com.aliucord.plugins.Data.getUserRecent;
import com.aliucord.views.TextInput;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.api.message.embed.MessageEmbed;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

@SuppressWarnings("unused")
@AliucordPlugin
public class Osu extends Plugin {
    private static final String BASE = "https://api.obamabot.me/v2/text/osu";
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

    @Override
    public void start(Context context) {
        String key = settings.getString("username", "");
        if (key.equals("")) key = "";
        var farguments = Arrays.asList(
                Utils.createCommandOption(ApplicationCommandType.STRING, "username", "Username of the player", null, false, false, null, null),
                Utils.createCommandOption(ApplicationCommandType.STRING, "send", "Send stats to chat or not", null, false, false, null, null)
        );
        var Sarguments = Arrays.asList(
                Utils.createCommandOption(ApplicationCommandType.STRING, "username", "Username of the player", null, false, false, null, null),
                Utils.createCommandOption(ApplicationCommandType.STRING, "send", "Send stats to chat or not", null, false, false, null, null)
        );
        String finalKey = key;
        commands.registerCommand(
                "osu",
                "Search someone stats",
                farguments,
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
                commands.registerCommand(
                "recent",
                "Recent Score",
                farguments,
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
                }
        );
    }
    // Thanks for Xinto Lyrics Plugin https://github.com/X1nto/AliucordPlugins/blob/master/Lyrics/src/main/java/com/aliucord/plugins/Lyrics.java
    private CommandsAPI.CommandResult userText(getUserData data) {
        var custom = data.custom.get(0);
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
                custom.pp_rank,
                data.country_code.toLowerCase(),
                custom.pp_country_rank,
                custom.pp_raw,
                custom.playcount,
                custom.hit_accuracy,
                custom.time_played,
                custom.playstyles,
                custom.format_last_visit,
                custom.format_join_date


        );
        return new CommandsAPI.CommandResult(dad);
    }

    private CommandsAPI.CommandResult userEmbed(getUserData data) {
        var custom = data.custom.get(0);
        MessageEmbed embed = new MessageEmbedBuilder()
                .setTitle(data.username)
                .addField("INFO:", String.format(Locale.ENGLISH,
                          "❯ Global Rank: **%s** (:flag_%s: #%s)\n" +
                                "❯ Total PP: **%s**\n" +
                                "❯ Play Count: **%s**\n" +
                                "❯ Hit Accuracy: **%s**%%\n" +
                                "❯ Time Played: **%s**\n" +
                                "❯ Playstyle: **%s**",
                        custom.pp_rank,
                        data.country_code.toLowerCase(),
                        custom.pp_country_rank,
                        custom.pp_raw,
                        custom.playcount,
                        custom.hit_accuracy,
                        custom.time_played,
                        custom.playstyles
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

    private getUserRecent getRecent(String user, String storeUsername) throws Throwable {
        if (user == null) user = storeUsername;
        return Http.simpleJsonGet(BASE + "?user=" + user + "&mode=", getUserRecent.class);
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}
