package net.horizonsend.discordbridge

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import net.dv8tion.jda.api.utils.cache.CacheFlag
import org.bukkit.Bukkit.getPluginManager
import org.bukkit.plugin.java.JavaPlugin

class DiscordBridge: JavaPlugin() {
	companion object {
		lateinit var plugin: DiscordBridge
			private set

		private lateinit var token: String
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
		// Must keep ROLE_TAGS enabled
		discordBuilder.disableCache(
			CacheFlag.ACTIVITY,
			CacheFlag.CLIENT_STATUS,
			CacheFlag.EMOTE,
			CacheFlag.MEMBER_OVERRIDES,
			CacheFlag.ONLINE_STATUS,
			CacheFlag.VOICE_STATE
		)

		// Disable the intents we do not need.
		// Must keep GUILD_MESSAGES, DIRECT_MESSAGES, and GUILD_MEMBERS enabled
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

		discordBuilder.addEventListeners(DiscordListener())

		val discord = discordBuilder.build()
	}

	override fun reloadConfig() {
		super.reloadConfig()

		token = config.getString("token")!!

		getPluginManager().callEvent(DiscordBridgeConfigReloadEvent(config))
	}
}