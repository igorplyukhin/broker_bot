import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.text

val botToken = System.getenv("BrokerBotToken") ?: ""

fun main(args: Array<String>) {
    val bot = bot {
        token = botToken
        dispatch {
            text { bot, update ->
                val text = update.message?.text ?: "Hello, World!"
                bot.sendMessage(chatId = update.message!!.chat.id, text = text)
            }
        }
    }
    bot.startPolling()
}