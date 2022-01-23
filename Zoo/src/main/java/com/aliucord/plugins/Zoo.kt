/*
 * Wolf's Aliucord Plugins
 * Copyright (C) 2021 Wolfkid200444
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.aliucord.plugins

import android.content.Context
import com.aliucord.Http
import com.aliucord.annotations.AliucordPlugin
import com.aliucord.api.CommandsAPI.CommandResult
import com.aliucord.entities.MessageEmbedBuilder
import com.aliucord.entities.Plugin
import com.aliucord.plugins.zooapi.ApiResponse
import java.io.IOException

@AliucordPlugin
class Zoo : Plugin() {
    override fun start(context: Context) {
        commands.registerCommand(
            "dog",
            "get a doggy image :3", emptyList()
        ) {
            try {
                val res = Http.simpleJsonGet(
                    "https://some-random-api.ml/img/dog",
                    ApiResponse::class.java
                )
                val eb =
                    MessageEmbedBuilder().setRandomColor().setTitle("is a e dog").setImage(res.link)
                return@registerCommand CommandResult(null, listOf(eb.build()), false)
            } catch (ex: IOException) {
                return@registerCommand CommandResult("No Dog founds", null, false)
            }
        }
        commands.registerCommand(
            "cat",
            "get a Cat image, meow", emptyList()
        ) {
            try {
                val res = Http.simpleJsonGet(
                    " https://some-random-api.ml/img/cat",
                    ApiResponse::class.java
                )
                val eb =
                    MessageEmbedBuilder().setRandomColor().setTitle("is a e cat").setImage(res.link)
                return@registerCommand CommandResult(null, listOf(eb.build()), false)
            } catch (ex: IOException) {
                return@registerCommand CommandResult("No Cats founds", null, false)
            }
        }
        commands.registerCommand(
            "panda",
            "get a PaNde ImaGE", emptyList()
        ) {
            try {
                val res = Http.simpleJsonGet(
                    "https://some-random-api.ml/img/panda",
                    ApiResponse::class.java
                )
                val eb = MessageEmbedBuilder().setRandomColor().setTitle("is a e PanDe")
                    .setImage(res.link)
                return@registerCommand CommandResult(null, listOf(eb.build()), false)
            } catch (ex: IOException) {
                return@registerCommand CommandResult("No Pandas founds", null, false)
            }
        }
        commands.registerCommand(
            "redpanda",
            "get a Red PandE image", emptyList()
        ) {
            try {
                val res = Http.simpleJsonGet(
                    " https://some-random-api.ml/img/red_panda",
                    ApiResponse::class.java
                )
                val eb =
                    MessageEmbedBuilder().setRandomColor().setTitle("is a e Red Mystical Pande")
                        .setImage(res.link)
                return@registerCommand CommandResult(null, listOf(eb.build()), false)
            } catch (ex: IOException) {
                return@registerCommand CommandResult("No Red Pandas founds", null, false)
            }
        }
        commands.registerCommand(
            "fox",
            "get a Fox Image", emptyList()
        ) {
            try {
                val res = Http.simpleJsonGet(
                    "https://some-random-api.ml/img/fox",
                    ApiResponse::class.java
                )
                val eb =
                    MessageEmbedBuilder().setRandomColor().setTitle("is a e Fox").setImage(res.link)
                return@registerCommand CommandResult(null, listOf(eb.build()), false)
            } catch (ex: IOException) {
                return@registerCommand CommandResult("No Foxes founds", null, false)
            }
        }
        commands.registerCommand(
            "bird",
            "get a Bird Image", emptyList()
        ) {
            try {
                val res = Http.simpleJsonGet(
                    "https://some-random-api.ml/img/birb",
                    ApiResponse::class.java
                )
                val eb = MessageEmbedBuilder().setRandomColor().setTitle("is a e BiRde")
                    .setImage(res.link)
                return@registerCommand CommandResult(null, listOf(eb.build()), false)
            } catch (ex: IOException) {
                return@registerCommand CommandResult("No Birds founds", null, false)
            }
        }
        commands.registerCommand(
            "koala",
            "get a Koala image", emptyList()
        ) {
            try {
                val res = Http.simpleJsonGet(
                    "https://some-random-api.ml/img/koala",
                    ApiResponse::class.java
                )
                val eb = MessageEmbedBuilder().setRandomColor().setTitle("is a e Koale")
                    .setImage(res.link)
                return@registerCommand CommandResult(null, listOf(eb.build()), false)
            } catch (ex: IOException) {
                return@registerCommand CommandResult("No Koala founds", null, false)
            }
        }
        commands.registerCommand(
            "kangaroo",
            "get a Kangaroo image", emptyList()
        ) {
            try {
                val res = Http.simpleJsonGet(
                    "https://some-random-api.ml/img/kangaroo",
                    ApiResponse::class.java
                )
                val eb = MessageEmbedBuilder().setRandomColor().setTitle("is a e Jumpy Boi")
                    .setImage(res.link)
                return@registerCommand CommandResult(null, listOf(eb.build()), false)
            } catch (ex: IOException) {
                return@registerCommand CommandResult("No Jumpy Bois Found founds", null, false)
            }
        }
        commands.registerCommand(
            "racoon",
            "get a Racoon image", emptyList()
        ) {
            try {
                val res = Http.simpleJsonGet(
                    "https://some-random-api.ml/img/racoon",
                    ApiResponse::class.java
                )
                val eb = MessageEmbedBuilder().setRandomColor().setTitle("is a e Stinky Boi")
                    .setImage(res.link)
                return@registerCommand CommandResult(null, listOf(eb.build()), false)
            } catch (ex: IOException) {
                return@registerCommand CommandResult("No Stinky Bois founds", null, false)
            }
        }
        commands.registerCommand(
            "whale",
            "get a Whale image", emptyList()
        ) {
            try {
                val res = Http.simpleJsonGet(
                    "https://some-random-api.ml/img/whale",
                    ApiResponse::class.java
                )
                val eb = MessageEmbedBuilder().setRandomColor()
                    .setTitle("is a Big uh Ocean Dolphine but bigger").setImage(res.link)
                return@registerCommand CommandResult(null, listOf(eb.build()), false)
            } catch (ex: IOException) {
                return@registerCommand CommandResult("No Whales found", null, false)
            }
        }
    }

    override fun stop(context: Context) {
        commands.unregisterAll()
    }
}