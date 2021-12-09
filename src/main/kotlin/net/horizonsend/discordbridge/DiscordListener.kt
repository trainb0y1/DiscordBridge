package net.horizonsend.discordbridge

import net.horizonsend.discordbridge.DiscordBridge.Companion.plugin
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.horizonsend.discordbridge.DiscordBridge.Companion.consoleChannel
import net.horizonsend.discordbridge.DiscordBridge.Companion.globalChannel
import org.bukkit.Bukkit.getServer

class DiscordListener: ListenerAdapter() {
	override fun onReady(event: ReadyEvent) {
		plugin.logger.info("Connected to Discord")
	}

	override fun onMessageReceived(event: MessageReceivedEvent) {
		when (event.channel.id) {
			globalChannel -> {
				getServer().broadcastMessage(event.message.contentRaw)
			}
			consoleChannel -> {
				getServer().dispatchCommand(getServer().consoleSender, event.message.contentRaw)
			}
		}
	}

	override fun onGuildMemberRoleAdd(event: GuildMemberRoleAddEvent) {

	}

	override fun onGuildMemberRoleRemove(event: GuildMemberRoleRemoveEvent) {

	}
}