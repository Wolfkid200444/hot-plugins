package com.aliucord.plugins;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.Plugin;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class emojify extends Plugin {

    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[]{ new Manifest.Author("Wolfie", 282978672711827456L) };
        manifest.description = "Emojify just emojify your text because why not?";
        manifest.version = "1.0.0";
        manifest.updateUrl = "https://raw.githubusercontent.com/Wolfkid200444/hot-plugins/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
        commands.registerCommand("emojify", "Emojify your Messages", Collections.singletonList(CommandsAPI.requiredMessageOption), args -> {
            String msg = (String) args.get("message");

            if (msg == null) return new CommandsAPI.CommandResult(msg);
            String Emojify;
            Pattern sus = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE);
            Matcher matcher = sus.matcher(msg);
            boolean matchFound = matcher.find();

            if(matchFound) {
                Emojify = msg.split(" ").map(L -> { return ":regional_indicator_" + L + ":"; });
            } else {

            }

            return new CommandsAPI.CommandResult(Emojify);
        });

    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}