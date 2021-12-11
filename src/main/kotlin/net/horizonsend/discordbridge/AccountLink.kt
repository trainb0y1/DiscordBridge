package net.horizonsend.discordbridge

import kotlinx.serialization.Serializable

@Serializable
data class AccountLink(val discord: Long, val minecraft: String)