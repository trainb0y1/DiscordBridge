package net.horizonsend.discordbridge.commands

import kotlinx.serialization.ExperimentalSerializationApi
import net.horizonsend.discordbridge.AccountLinkManager.addAccountWaitingLink
import net.horizonsend.discordbridge.DiscordBridge.Companion.discord
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@ExperimentalSerializationApi
class Link : CommandExecutor {
	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
		if (args.size != 1) {
			sender.sendMessage("Invalid number of arguments.")
			return false
		}
		if (sender !is Player) {
			sender.sendMessage("Only a player can link an account.")
			return false
		}

		val user = when (args[0].matches(Regex("\\d{17,18}"))) {
			true -> discord.getUserById(args[0])
			false -> discord.getUserByTag(args[0])
		}

		if (user == null) {
			sender.sendMessage("User not found")
			return false
		}

		addAccountWaitingLink(user.idLong, sender.uniqueId.toString())

		return true
	}
}