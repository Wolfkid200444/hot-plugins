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


import android.content.Context
import android.text.TextUtils
import com.aliucord.annotations.AliucordPlugin
import com.aliucord.api.CommandsAPI
import com.aliucord.entities.Plugin


@AliucordPlugin
class clapclap : Plugin() {
    override fun start(context: Context?) {
        commands.registerCommand(
            "clap",
            "Clap your messages", listOf(CommandsAPI.requiredMessageOption)
        ) { ctx ->
            val msg: String = ctx.getRequiredString("message")
            val Messafe = msg.trim { it <= ' ' }.split(" ").toTypedArray()
            val Clap: String
            Clap = if (Messafe.size <= 1) {
                val x = msg.split("").toTypedArray()
                TextUtils.join(" \uD83D\uDC4F ", x)
            } else {
                TextUtils.join(
                    " \uD83D\uDC4F ",
                    msg.split(" ").dropLastWhile { it.isEmpty() }.toTypedArray()
                )
            }
            CommandsAPI.CommandResult(Clap)
        }
    }

    override fun stop(context: Context?) {
        commands.unregisterAll()
    }
}