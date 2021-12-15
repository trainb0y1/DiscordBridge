package net.horizonsend.discordbridge

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.horizonsend.discordbridge.DiscordBridge.Companion.consoleChannel
import net.horizonsend.discordbridge.DiscordBridge.Companion.globalChannel
import net.horizonsend.discordbridge.DiscordBridge.Companion.plugin
import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit.getServer
import java.awt.Color

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
	override fun onSlashCommand(event: SlashCommandEvent) {
		when (event.name) {
			"playerlist" -> onPlayerlist(event)
		}
	}

	override fun onGuildMemberRoleAdd(event: GuildMemberRoleAddEvent) {

	}

	override fun onGuildMemberRoleRemove(event: GuildMemberRoleRemoveEvent) {

	}

	fun onPlayerlist(event: SlashCommandEvent) {
		val embed = EmbedBuilder()
		embed.setTitle("Minecraft Player List")
		when (plugin.server.onlinePlayers.size) {
			0 -> {
				embed.setColor(Color.RED)
				embed.setDescription("No online players!")
			}
			else -> {
				embed.setColor(Color.GREEN)
				embed.setDescription(plugin.server.onlinePlayers.joinToString(", ") { it.name })
			}
		}
		event.replyEmbeds(embed.build()).setEphemeral(true).queue()
	}
}