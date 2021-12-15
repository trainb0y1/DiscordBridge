package net.horizonsend.discordbridge

import kotlinx.serialization.ExperimentalSerializationApi
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import net.dv8tion.jda.api.utils.cache.CacheFlag
import net.horizonsend.discordbridge.commands.Link
import org.bukkit.Bukkit.getOnlinePlayers
import org.bukkit.Bukkit.getPluginManager
import org.bukkit.plugin.java.JavaPlugin

@ExperimentalSerializationApi
class DiscordBridge: JavaPlugin() {
	companion object {
		lateinit var plugin: DiscordBridge
			private set

		lateinit var globalChannel: TextChannel
			private set

		lateinit var consoleChannel: TextChannel
			private set

		lateinit var discord: JDA
			private set
	}

	override fun onEnable() {
		plugin = this

		saveResource("accounts.json", false)
		saveDefaultConfig()
		reloadConfig()

		val discordBuilder = JDABuilder.createDefault(config.getString("token")!!)

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

		// Enable GUILD_MESSAGES, DIRECT_MESSAGES, and GUILD_MEMBERS
		// Disable everything else.
		discordBuilder.setEnabledIntents(
			GatewayIntent.GUILD_MESSAGES,
			GatewayIntent.DIRECT_MESSAGES,
			GatewayIntent.GUILD_MEMBERS
		)

		discordBuilder.addEventListeners(DiscordListener())

		discordBuilder.setActivity(Activity.playing(" with ${getOnlinePlayers().size} player${if (getOnlinePlayers().size == 1) "" else "s"}"))

		discord = discordBuilder.build()

		discord.awaitReady()

		globalChannel = discord.getTextChannelById( config.getString("globalChannel")!! )!!

		globalChannel.sendMessage("**:arrow_up_small: Server is up!**").queue()

		consoleChannel = discord.getTextChannelById( config.getString("consoleChannel")!! )!!

		// Could use discord.upsertCommand() but it can take an hour to update.
		globalChannel.guild.upsertCommand("playerlist", "Show online players").queue()

		getPluginManager().registerEvents(MinecraftListener(), this)

		this.getCommand("link")!!.setExecutor(Link())
	}

	override fun onDisable() {
		globalChannel.sendMessage("**:arrow_down_small: Server is down!**").queue()

		discord.shutdown()
	}
}