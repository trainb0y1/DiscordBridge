package net.horizonsend.discordbridge

import kotlinx.serialization.ExperimentalSerializationApi
import net.horizonsend.discordbridge.DiscordBridge.Companion.plugin
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.File

@ExperimentalSerializationApi
object AccountLinkManager {
	private val accountsWaitingLink = mutableSetOf<AccountLink>()

	fun addAccountWaitingLink(discord: Long, minecraft: String) {
		accountsWaitingLink.add(AccountLink(discord, minecraft))
	}

	private val linkedAccounts = Json.decodeFromStream<AccountLink>(File(plugin.dataFolder, "accounts.json").inputStream())

	private fun saveLinkedAccounts() {
		Json.encodeToStream(linkedAccounts, File(plugin.dataFolder, "accounts.json").outputStream())
	}
}