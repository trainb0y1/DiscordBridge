package net.horizonsend.discordbridge

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import net.dv8tion.jda.api.utils.cache.CacheFlag
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getPluginManager
import org.bukkit.plugin.java.JavaPlugin

class DiscordBridge: JavaPlugin() {
	companion object {
		lateinit var plugin: DiscordBridge
			private set

		private lateinit var token: String

		lateinit var globalChannel: String
			private set

		lateinit var consoleChannel: String
			private set

		lateinit var discord: JDA
			private set
	}

	override fun onEnable() {
		plugin = this

		getPluginManager().registerEvents(MinecraftListener(), this)

		// /!\ Must be called after registering events
		saveDefaultConfig()
		reloadConfig()

		val discordBuilder = JDABuilder.createDefault(token)

		// Keep track of members, so we know when role changes happen.
		discordBuilder.setMemberCachePolicy(MemberCachePolicy.ALL)

		// Disable caching of things we do not need.
		discordBuilder.disableCache(
			CacheFlag.ACTIVITY,
			CacheFlag.CLIENT_STATUS,
			CacheFlag.EMOTE,
			CacheFlag.MEMBER_OVERRIDES,
			CacheFlag.ONLINE_STATUS,
			CacheFlag.VOICE_STATE
		)

		// Enable ROLE_TAGS
		discordBuilder.enableCache(CacheFlag.ROLE_TAGS)

		// Disable the intents we do not need.
		discordBuilder.disableIntents(
			GatewayIntent.GUILD_BANS,
			GatewayIntent.GUILD_EMOJIS,
			GatewayIntent.GUILD_WEBHOOKS,
			GatewayIntent.GUILD_INVITES,
			GatewayIntent.GUILD_VOICE_STATES,
			GatewayIntent.GUILD_PRESENCES,
			GatewayIntent.GUILD_MESSAGE_REACTIONS,
			GatewayIntent.GUILD_MESSAGE_TYPING,
			GatewayIntent.DIRECT_MESSAGE_REACTIONS,
			GatewayIntent.DIRECT_MESSAGE_TYPING
		)

		// Enable GUILD_MESSAGES, DIRECT_MESSAGES, and GUILD_MEMBERS
		discordBuilder.enableIntents(
			GatewayIntent.GUILD_MESSAGES,
			GatewayIntent.DIRECT_MESSAGES,
			GatewayIntent.GUILD_MEMBERS
		)

		discordBuilder.addEventListeners(DiscordListener())

		discord = discordBuilder.build()
	}

	override fun onDisable() {
		discord.shutdown()
	}

	override fun reloadConfig() {
		super.reloadConfig()

		token = config.getString("token")!!

		globalChannel = config.getString("globalChannel")!!
		consoleChannel = config.getString("consoleChannel")!!

		getPluginManager().callEvent(DiscordBridgeConfigReloadEvent(config))
	}
}