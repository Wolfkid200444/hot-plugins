package com.aliucord.plugins;

import android.content.Context;

import androidx.annotation.NonNull;

import com.aliucord.Http;
import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.MessageEmbedBuilder;
import com.aliucord.entities.Plugin;
import com.aliucord.plugins.zooapi.ApiResponse;

import java.io.IOException;
import java.util.*;

@SuppressWarnings("unused")
public class Zoo extends Plugin {

    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[]{new Manifest.Author("Wolfie", 282978672711827456L)};
        manifest.description = "Zoo";
        manifest.version = "1.0.0";
        manifest.updateUrl = "https://raw.githubusercontent.com/Wolfkid200444/hot-plugins/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {

        commands.registerCommand("Dog", "get a doggy image :3", Collections.emptyList(), args -> {
                try {
                        ApiResponse res = Http.simpleJsonGet("https://some-random-api.ml/img/dog", ApiResponse.class);
                        var eb = new MessageEmbedBuilder().setRandomColor().setTitle("is a e dog").setImage(res.link);
                        return new CommandsAPI.CommandResult(null, Collections.singletonList(eb.build()), false);
                    } catch (IOException ex) {
                        return new CommandsAPI.CommandResult("No Dog founds", null, false);
                    }
                });

    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

}