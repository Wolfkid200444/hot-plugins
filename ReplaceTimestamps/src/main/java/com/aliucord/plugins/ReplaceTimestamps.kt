package com.aliucord.plugins

import android.content.Context
import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.before
import com.discord.utilities.SnowflakeUtils
import com.discord.widgets.chat.MessageContent
import com.discord.widgets.chat.MessageManager
import com.discord.widgets.chat.input.ChatInputViewModel
import java.text.SimpleDateFormat

@AliucordPlugin(requiresRestart = true)
class ReplaceTimestamps : Plugin() {
    val field = MessageContent::class.java.getDeclaredField("textContent").apply { isAccessible = true }

    override fun start(context: Context) {

        patcher.before<ChatInputViewModel>(
            "sendMessage",
            Context::class.java,
            MessageManager::class.java,
            MessageContent::class.java,
            List::class.java,
            Boolean::class.javaPrimitiveType!!,
            Function1::class.java
        ) {
            try {
                val MessageContent = it.args[2] as MessageContent


                val format = SimpleDateFormat("HH:mm")
                try {
                    val date = format.parse("10:00")
                    val something = SnowflakeUtils.fromTimestamp()
                    field[it.args[2]] = "<t:$date:t>"
                } catch (ex: Throwable) {
                    logger.error(ex)
                }
                //it.args[2]
            } catch (e: Exception) {
                logger.error(e)
            }

        }


    }

//    private fun isANumber(s: String): Boolean {
//        val regex = "([0-2]?+[0-9]\\:[0-6]?[0-9])".toRegex()
//        return regex.matches(s);
//    }

    override fun stop(context: Context) {
        patcher.unpatchAll()
    }
}
