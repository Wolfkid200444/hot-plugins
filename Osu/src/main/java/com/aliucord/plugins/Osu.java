package com.aliucord.plugins;


import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
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
import com.discord.models.commands.ApplicationCommandOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

@SuppressWarnings("unused")
public class Osu extends Plugin {
    private static final String BASE = "https://api.obamabot.ml/text/osu";
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

            input.setHint("Input your Osu API Token");

            var editText = input.getEditText();
            assert editText != null;

            editText.setMaxLines(1);
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.setText(String.valueOf(settings.getString("token", "")));
            editText.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                public void onTextChanged(CharSequence s, int start, int before, int count) { }
                public void afterTextChanged(Editable s) {
                    try {
                        settings.setString("token", s.toString());
                    } catch (Throwable ignored) {
                        settings.setString("token", "");
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
        manifest.version = "1.0.1";
        manifest.updateUrl = "https://raw.githubusercontent.com/Wolfkid200444/hot-plugins/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
        String key = settings.getString("token", "");
        if (key == "") key = "";
        var arguments = new ArrayList<ApplicationCommandOption>();
        arguments.add(new ApplicationCommandOption(ApplicationCommandType.STRING, "username", "Username of the player", null, true, false, null, null));
        arguments.add(new ApplicationCommandOption(ApplicationCommandType.STRING, "send", "Send stats to chat or not", null, true, false, null, null));

        String finalKey = key;
        commands.registerCommand(
                "osu",
                "Search someone stats",
                arguments,
                ctx -> {
                    String user = ctx.getRequiredString("username");
                    var shouldSend = ctx.getBool("send");
                    try {
                        var data = getUser(user, finalKey);
                        return shouldSend ? text(data) : embed(data);
                    } catch (Throwable e) {
                        Main.logger.error(e);
                        return new CommandsAPI.CommandResult("Failed to fetch Data lol", null, false);
                    }
                });
    }
    // Thanks for Xinto Lyrics Plugin https://github.com/X1nto/AliucordPlugins/blob/master/Lyrics/src/main/java/com/aliucord/plugins/Lyrics.java
    private CommandsAPI.CommandResult text(getUserData data) {
        var dad = String.format(Locale.ENGLISH,
                                "__%s Stats__\n" +
                                        "Global Rank: **%s** (:flag_%s: #%s)\n" +
                                        "PP: **%s**\n" +
                                        "Play Count: **%s**\n" +
                                        "Accuracy: **%s**\n" +
                                        "Time Played: **%s**",
                data.username,
                data.formated_pp_rank,
                data.country.toLowerCase(),
                data.formated_pp_country_rank,
                data.pp_raw,
                data.playcount,
                data.short_accuracy,
                data.time_played
                                );
        return new CommandsAPI.CommandResult(dad);
    }

    private CommandsAPI.CommandResult embed(getUserData data) {
        var embed = new MessageEmbedBuilder()
                .setTitle(data.username)
                .addField("INFO:", String.format(Locale.ENGLISH,
                          "❯ Global Rank: **%s** (:flag_%s: #%s)\n" +
                                "❯ PP: **%s**\n" +
                                "❯ Play Count: **%s**\n" +
                                "❯ Accuracy: **%s**\n" +
                                "❯ Time Played: **%s**",
                        data.formated_pp_rank,
                        data.country.toLowerCase(),
                        data.formated_pp_country_rank,
                        data.pp_raw,
                        data.playcount,
                        data.short_accuracy,
                        data.time_played
                ), false)
                .setUrl(data.user_profile)
                .setThumbnail(data.picture)
                .setColor(0x209CEE)
                .build();

        return new CommandsAPI.CommandResult(null, Collections.singletonList(embed), false, "Osu Stats");
    }

    @NonNull
    private getUserData getUser(String user, String API_KEY) throws Throwable {
        var url = (getUserData) Http.simpleJsonGet(BASE + "?user=" + user + "&token=" + API_KEY, getUserData.class);
        return url;
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}