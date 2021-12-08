package net.horizonsend.discordbridge

import org.bukkit.Bukkit.getPluginManager
import org.bukkit.plugin.java.JavaPlugin

class DiscordBridge: JavaPlugin() {
	companion object {
		lateinit var plugin: DiscordBridge
			private set
	}

	override fun onEnable() {
		plugin = this

		getPluginManager().registerEvents(MinecraftListener(), this)
	}

	override fun reloadConfig() {
		super.reloadConfig()

		getPluginManager().callEvent(DiscordBridgeConfigReloadEvent(config))
	}
}