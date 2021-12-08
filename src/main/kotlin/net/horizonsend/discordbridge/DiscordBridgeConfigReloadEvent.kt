package net.horizonsend.discordbridge

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class DiscordBridgeConfigReloadEvent(val config: ConfigurationSection): Event() {
	override fun getHandlers(): HandlerList {
		return handlerList
	}

	companion object {
		@JvmStatic
		val handlerList = HandlerList()
	}
}