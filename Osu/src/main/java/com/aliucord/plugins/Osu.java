package com.aliucord.plugins;

import static com.aliucord.Http.simpleGet;

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
import com.aliucord.entities.Plugin;
import com.aliucord.fragments.SettingsPage;
import com.aliucord.views.TextInput;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.models.commands.ApplicationCommandOption;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Osu extends Plugin {
    private static final String BASE = "https://osu.ppy.sh/api/";

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
        manifest.description = "Search OSU Stats of someone";
        manifest.version = "1.0.0";
        manifest.updateUrl = "https://raw.githubusercontent.com/Wolfkid200444/hot-plugins/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
         String key = settings.getString("token", "Blank");

        var arguments = new ArrayList<ApplicationCommandOption>();
        arguments.add(new ApplicationCommandOption(ApplicationCommandType.STRING, "username", "Username of the player", null, true, false, null, null));
        commands.registerCommand(
                "osu",
                "Search someone stats",
                arguments,
                ctx -> {
                    String user = ctx.getRequiredString("username");
                    String something = null;
                    try {
                        something = getUser(user, key);
                        var obj = new JSONArray(new JSONTokener(something));
                        String username = obj.getJSONObject(0).getString("username");
                    } catch (Throwable e) {
                        Main.logger.error(e);
                    }

                    return new CommandsAPI.CommandResult(something);
                });
    }

    @NonNull
    private String getUser(String user, String API_KEY) throws Throwable {
        String url = simpleGet(BASE + "get_user?k=" + API_KEY + "&u=" + user);

        return url;
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}