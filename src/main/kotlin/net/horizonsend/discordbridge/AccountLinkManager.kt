package net.horizonsend.discordbridge

import net.horizonsend.discordbridge.DiscordBridge.Companion.plugin
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.File

@ExperimentalSerializationApi
object AccountLinkManager {
	private val linkedAccounts = Json.decodeFromStream<HashMap<String, Int>>(File(plugin.dataFolder, "accounts.json").inputStream())

	private fun saveLinkedAccounts() {
		Json.encodeToStream(linkedAccounts, File(plugin.dataFolder, "accounts.json").outputStream())
	}

	fun linkAccount(minecraft: String, discord: Int) {
		linkedAccounts[minecraft] = discord
		saveLinkedAccounts()
	}

	fun unlinkAccount(minecraft: String) {
		linkedAccounts.remove(minecraft)
		saveLinkedAccounts()
	}

	fun unlinkAccount(discord: Int) {
		linkedAccounts.filter { it.value == discord }.keys.forEach { linkedAccounts.remove(it) }
		saveLinkedAccounts()
	}

	fun isLinked(discord: Int): Boolean {
		return linkedAccounts.values.contains(discord)
	}

	fun isLinked(minecraft: String): Boolean {
		return linkedAccounts.containsKey(minecraft)
	}

	fun getLinkedAccount(minecraft: String): Int? {
		return linkedAccounts[minecraft]
	}
}