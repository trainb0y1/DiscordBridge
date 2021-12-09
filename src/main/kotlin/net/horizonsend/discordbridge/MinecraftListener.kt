package net.horizonsend.discordbridge

import io.papermc.paper.event.player.AsyncChatEvent
import io.papermc.paper.text.PaperComponents
import net.dv8tion.jda.api.entities.Activity
import net.ess3.api.events.MuteStatusChangeEvent
import net.horizonsend.discordbridge.DiscordBridge.Companion.discord
import net.horizonsend.discordbridge.DiscordBridge.Companion.globalChannel
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class MinecraftListener: Listener {
	@EventHandler(priority = EventPriority.MONITOR)
	fun onPlayerJoin(event: PlayerJoinEvent) {
		discord.presence.activity = Activity.playing(" with ${Bukkit.getOnlinePlayers().size} player${if (Bukkit.getOnlinePlayers().size == 1) "" else "s"}")
	}

	@EventHandler(priority = EventPriority.MONITOR)
	fun onPlayerLeave(event: PlayerQuitEvent) {
		discord.presence.activity = Activity.playing(" with ${Bukkit.getOnlinePlayers().size - 1} player${if (Bukkit.getOnlinePlayers().size - 1 == 1) "" else "s"}")
	}

	@EventHandler(priority = EventPriority.MONITOR)
	fun onPlayerChat(event: AsyncChatEvent) {
		discord.getTextChannelById(globalChannel)!!.sendMessage(plainText().serialize(event.message())).queue();
	}

	@EventHandler(priority = EventPriority.MONITOR)
	fun onPlayerDeath(event: PlayerDeathEvent) {
		discord.getTextChannelById(globalChannel)!!.sendMessage(PaperComponents.plainSerializer().serialize(event.deathMessage()!!)).queue();
	}

	@EventHandler(priority = EventPriority.MONITOR)
	fun onPlayerMute(event: MuteStatusChangeEvent) {
		// Player mute event
	}
}