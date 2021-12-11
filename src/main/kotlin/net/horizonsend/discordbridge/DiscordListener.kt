package net.horizonsend.discordbridge

import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.horizonsend.discordbridge.DiscordBridge.Companion.consoleChannel
import net.horizonsend.discordbridge.DiscordBridge.Companion.globalChannel
import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit.getServer

class DiscordListener: ListenerAdapter() {
	override fun onMessageReceived(event: MessageReceivedEvent) {
		if (event.author.isBot) return

		when (event.channel) {
			globalChannel -> {
				getServer().broadcast(text(event.message.contentRaw))
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