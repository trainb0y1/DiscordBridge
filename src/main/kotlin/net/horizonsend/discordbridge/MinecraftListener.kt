package net.horizonsend.discordbridge

import com.destroystokyo.paper.event.server.ServerTickEndEvent
import io.papermc.paper.event.player.AsyncChatEvent
import net.dv8tion.jda.api.MessageBuilder
import net.ess3.api.events.MuteStatusChangeEvent
import net.horizonsend.discordbridge.DiscordBridge.Companion.discord
import net.horizonsend.discordbridge.DiscordBridge.Companion.globalChannel
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText
import org.apache.logging.log4j.message.Message
import org.bukkit.Bukkit.getBannedPlayers
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class MinecraftListener: Listener {
	@EventHandler
	fun onPlayerJoin(event: PlayerJoinEvent) {
		// Player join event
	}

	@EventHandler
	fun onPlayerLeave(event: PlayerQuitEvent) {
		// Player leave event
	}

	@EventHandler
	fun onPlayerChat(event: AsyncChatEvent) {
		discord.getTextChannelById(globalChannel)!!.sendMessage(plainText().serialize(event.message())).queue();
	}

	@EventHandler
	fun onPlayerMute(event: MuteStatusChangeEvent) {
		// Player mute event
	}

	@EventHandler
	fun onTick(event: ServerTickEndEvent) {
		getBannedPlayers().forEach {

		}
	}
}