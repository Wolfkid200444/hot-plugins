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
        manifest.version = "1.0.5";
        manifest.updateUrl = "https://raw.githubusercontent.com/Wolfkid200444/hot-plugins/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {

        commands.registerCommand(
                "clap",
                "Clap your messages",
                Collections.singletonList(CommandsAPI.requiredMessageOption),
                args -> {
            String msg = (String) args.get("message");
            if (msg == null) return new CommandsAPI.CommandResult(msg);
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