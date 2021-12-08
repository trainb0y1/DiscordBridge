package net.horizonsend.discordbridge

import net.horizonsend.discordbridge.DiscordBridge.Companion.plugin
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent
import net.dv8tion.jda.api.events.message.GenericMessageEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class DiscordListener: ListenerAdapter() {
	override fun onReady(event: ReadyEvent) {
		plugin.logger.info("Connected to Discord")
	}

	override fun onGenericMessage(event: GenericMessageEvent) {

	}

	override fun onGuildMemberRoleAdd(event: GuildMemberRoleAddEvent) {

	}
}