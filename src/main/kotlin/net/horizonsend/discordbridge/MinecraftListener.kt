package net.horizonsend.discordbridge

import com.destroystokyo.paper.event.server.ServerTickEndEvent
import io.papermc.paper.event.player.AsyncChatEvent
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Activity
import net.ess3.api.events.MuteStatusChangeEvent
import net.horizonsend.discordbridge.DiscordBridge.Companion.discord
import net.horizonsend.discordbridge.DiscordBridge.Companion.globalChannel
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText
import org.apache.logging.log4j.message.Message
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getBannedPlayers
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class MinecraftListener: Listener {
	@EventHandler
	fun onPlayerJoin(event: PlayerJoinEvent) {
		discord.presence.activity = Activity.playing(" with ${Bukkit.getOnlinePlayers().size} player${if (Bukkit.getOnlinePlayers().size == 1) "" else "s"}")
	}

	@EventHandler
	fun onPlayerLeave(event: PlayerQuitEvent) {
		discord.presence.activity = Activity.playing(" with ${Bukkit.getOnlinePlayers().size} player${if (Bukkit.getOnlinePlayers().size - 1 == 1) "" else "s"}")
	}

	@EventHandler
	fun onPlayerChat(event: AsyncChatEvent) {
		discord.getTextChannelById(globalChannel)!!.sendMessage(plainText().serialize(event.message())).queue();
	}

	@EventHandler
	fun onPlayerMute(event: MuteStatusChangeEvent) {
		// Player mute event
	}
}