package com.aliucord.plugins;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;

import com.aliucord.api.CommandsAPI;
import com.aliucord.api.SettingsAPI;
import com.aliucord.entities.Plugin;
import com.aliucord.fragments.SettingsPage;
import com.aliucord.views.TextInput;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.models.commands.ApplicationCommandOption;

import java.util.ArrayList;

import osuAPI.OsuAPI;
import osuAPI.OsuUser;

@SuppressWarnings("unused")
public class Osu extends Plugin {
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
            setActionBarTitle("OsU");

            var context = view.getContext();
            var input = new TextInput(context);

            input.setHint("Input your Osu API Token");

            var editText = input.getEditText();
            assert editText != null;

            editText.setMaxLines(1);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
            editText.setText(String.valueOf(settings.getString("token", "Blank")));
            editText.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                public void onTextChanged(CharSequence s, int start, int before, int count) { }
                public void afterTextChanged(Editable s) {
                    try {
                        settings.setString("token", s.toString());
                    } catch (Throwable ignored) {
                        settings.setString("token", "Blank");
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
        manifest.description = "Pet pet";
        manifest.version = "1.1.2";
        manifest.updateUrl = "https://raw.githubusercontent.com/Wolfkid200444/hot-plugins/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
        OsuAPI api = new OsuAPI(settings.getString("token", "Blank"));
        var arguments = new ArrayList<ApplicationCommandOption>();
        arguments.add(new ApplicationCommandOption(ApplicationCommandType.STRING, "username", "Username of the player", null, true, false, null, null));
        commands.registerCommand(
                "osu",
                "Search someone stats",
                arguments,
                ctx -> {
                    String user = ctx.getRequiredString("username");
                    OsuUser something = api.getUser(user, 0);
                    return new CommandsAPI.CommandResult("lol");
                });
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}
