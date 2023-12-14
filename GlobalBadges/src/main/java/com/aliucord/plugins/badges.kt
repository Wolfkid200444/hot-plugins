/*
 * Wolf's Aliucord Plugins
 * Copyright (C) 2023 Wolfkid200444
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.aliucord.plugins


import android.content.Context
import android.graphics.Bitmap
import com.aliucord.Http
import com.aliucord.Main
import com.aliucord.Utils
import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.Hook
import com.aliucord.patcher.Patcher
import com.discord.utilities.views.SimpleRecyclerAdapter
import com.discord.widgets.user.Badge
import com.discord.widgets.user.profile.UserProfileHeaderView
import com.discord.widgets.user.profile.UserProfileHeaderViewModel
import com.lytefast.flexinput.R
import java.util.concurrent.atomic.AtomicBoolean


@AliucordPlugin
class GlobalBadges : Plugin() {
    class CustomBadge(val id: String?, val url: String?, val text: String) {
        fun getDrawableId() = if (id == null) 0 else try {
            R.e::class.java.getDeclaredField(id).getInt(null)
        } catch (e: Throwable) {
            Main.logger.error("Failed to get drawable for $id", e)
            0
        }

        fun toDiscordBadge() = Badge(getDrawableId(), null, text, false, url)
    }

    class UserBadges(val roles: Array<String>?, val custom: Array<CustomBadge>?)
    private val url = "https://api.obamabot.me/v2/text/badges?user"
    private val userBadges = HashMap<Long, List<Badge>?>()
    private val cache = HashMap<String, Bitmap>()

    private val badgesAdapter = UserProfileHeaderView::class.java.getDeclaredField("badgesAdapter").apply { isAccessible = true }
    private val dataField = SimpleRecyclerAdapter::class.java.getDeclaredField("data").apply { isAccessible = true }

    override fun load(context: Context?) {
        val fetchingBadges = AtomicBoolean(false)
        Patcher.addPatch(
            UserProfileHeaderView::class.java.getDeclaredMethod("updateViewState", UserProfileHeaderViewModel.ViewState.Loaded::class.java),
            Hook { (it, state: UserProfileHeaderViewModel.ViewState.Loaded) ->
                val id = state.user.id
                if (userBadges.containsKey(id)) addUserBadges(id, it.thisObject)
                else if (!fetchingBadges.getAndSet(true)) Utils.threadPool.execute {
                    try {
                        userBadges[id] = getUserBadges(Http.simpleJsonGet("$url=$id", UserBadges::class.java))
                        addUserBadges(id, it.thisObject)
                    } catch (e: Throwable) {
                        if (e is Http.HttpException && e.statusCode == 404)
                            userBadges[id] = null
                        else
                            logger.error("Failed to get badges for user $id", e)
                    } finally {
                        fetchingBadges.set(false)
                    }
                }
            }
        )
    }

    private fun getUserBadges(badges: UserBadges): List<Badge> {
        val list = ArrayList<Badge>(1)
        badges.roles?.forEach { when(it) {} }
        if (badges.custom?.isNotEmpty() == true) list.addAll(badges.custom.map { it.toDiscordBadge() })
        return list
    }

    @Suppress("UNCHECKED_CAST")
    private fun addUserBadges(id: Long, userProfileHeaderView: Any) {
        val badges = userBadges[id] ?: return
        val adapter = badgesAdapter[userProfileHeaderView] as SimpleRecyclerAdapter<*, *>
        val data = dataField[adapter] as MutableList<Badge>
        data.addAll(badges)
    }

    override fun start(context: Context) {}
    override fun stop(context: Context?) {}
}