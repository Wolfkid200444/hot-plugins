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
import android.net.Uri
import com.aliucord.Http
import com.aliucord.Main
import com.aliucord.Utils.createCommandOption
import com.aliucord.annotations.AliucordPlugin
import com.aliucord.api.CommandsAPI.CommandResult
import com.aliucord.entities.CommandContext
import com.aliucord.entities.Plugin
import com.discord.api.commands.ApplicationCommandType
import com.discord.utilities.icon.IconUtils
import java.io.File
import java.io.FileOutputStream

@AliucordPlugin
class PetPet : Plugin() {
    override fun start(context: Context) {
        val arguments = listOf(
            createCommandOption(
                ApplicationCommandType.USER,
                "name",
                "The user to pet",
                null,
                true,
                false
            )
        )
        commands.registerCommand(
            "petpet",
            "pet someone",
            arguments
        ) { ctx: CommandContext ->
            val user = ctx.getRequiredUser("name")
            val avatar = IconUtils.getForUser(user)
            var file: File? = null
            try {
                file = imageToDataUri(avatar, context)
            } catch (e: Throwable) {
                Main.logger.error(e)
            }
            assert(file != null)
            ctx.addAttachment(Uri.fromFile(file).toString(), "petpet.gif")
            CommandResult("")
        }
    }

    @Throws(Throwable::class)
    private fun imageToDataUri(avatar: String, mContext: Context): File {
        val res = Http.Request(url + avatar.replace("webp", "png")).execute()
        val f = File.createTempFile("temp", ".gif", mContext.cacheDir)
        FileOutputStream(f).use { fos -> res.pipe(fos) }
        f.deleteOnExit()
        return f
    }

    override fun stop(context: Context) {
        commands.unregisterAll()
    }

    companion object {
        private const val url = "https://api.obamabot.ml/v1/image/petpet?avatar="
    }
}