package com.aliucord.plugins;

import android.content.Context;

import androidx.annotation.NonNull;

import com.aliucord.Http;
import com.aliucord.api.CommandsAPI;
import com.aliucord.entities.MessageEmbedBuilder;
import com.aliucord.entities.Plugin;
import com.discord.api.commands.ApplicationCommandType;
import com.discord.api.message.embed.MessageEmbed;
import com.discord.models.commands.ApplicationCommandOption;
import com.discord.models.user.CoreUser;
import com.discord.models.user.User;
import com.discord.stores.StoreStream;
import com.discord.utilities.rest.RestAPI;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class petpet extends Plugin {

    private static final String baseUrl = "https://api.obamabot.ml/image/petpet?avatar=";

    @NonNull
    @Override
    public Manifest getManifest() {
        Manifest manifest = new Manifest();
        manifest.authors = new Manifest.Author[] { new Manifest.Author("Wolfie",282978672711827456L) };
        manifest.description = "Petpet someone i guess?.";
        manifest.version = "1.1.1";
        manifest.updateUrl = "https://raw.githubusercontent.com/Wolfkid200444/hot-plugins/builds/updater.json";
        return manifest;
    }

    @Override
    public void start(Context context) {
        ApplicationCommandOption UserId = new ApplicationCommandOption(ApplicationCommandType.USER, "name", "Mention someone", null, true, false, null, null);
        ApplicationCommandOption shouldSendArg = new ApplicationCommandOption(ApplicationCommandType.BOOLEAN, "send", "To send output in the chat or not", null, false, false, null, null);
        List<ApplicationCommandOption> arguments = Arrays.asList(UserId, shouldSendArg);

        commands.registerCommand("petpet", "pet someone cutely", arguments, args -> {
            Boolean shouldSend = (Boolean) args.get("send");
            String Username = (String) args.get("name");

            if (shouldSend == null) {
                shouldSend = false;
            }

            try {
                ResponseModel.Data data = fetch(Username);
                return shouldSend ? PictureText(data) : PictureEmbed(data);
            } catch (Exception e) {
                e.printStackTrace();
                return new CommandsAPI.CommandResult("Failed to fetch data", null, false);
            }
        });
    }

    @Override
    public void stop(Context context) {
        commands.unregisterAll();
    }

    private CommandsAPI.CommandResult PictureText(ResponseModel.Data data) {
        String Name = data.name;

        return new CommandsAPI.CommandResult(Name);
    }

    private CommandsAPI.CommandResult PictureEmbed(ResponseModel.Data data) {
        MessageEmbed embed = new MessageEmbedBuilder()
                .setImage(data.url)
                .setColor(0x209CEE)
                .build();

        return new CommandsAPI.CommandResult(null, Collections.singletonList(embed), false, "Sussy baka");
    }
    private ResponseModel.Data fetch(String Username) throws Exception {
        long num = Long.parseLong(Username);

        User user = StoreStream.getUsers().getUsers().get(num);
//        if(user == null) {
//            user = RestAPI.getApi().userGet();
//        }
        // String userID = StoreStream.getUsers().getMe().getId();
        // String avatarUrl = "https://cdn.discordapp.com/avatars/" + userID + "/" + avatar + ".png?size=128";
        ResponseModel responseModel = Http.simpleJsonGet(baseUrl + "https://cdn.dscordapp.com/avatars/606186285090209845/362aebf42531e50695b5bb96c9b93d25.png?size=4096", ResponseModel.class);

        return responseModel.data.get(0);
    }

    @SuppressWarnings({"UnusedDeclaration", "MPuvroUmiBJimDWaXFzpvd38saLvn1ruhy"})
    public static class ResponseModel {

        private List<Data> data;

        private static class Data {
            private String name;
            private String url;
        }

    }

}