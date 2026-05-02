package dev.meluhdy.scovilleCommands.command.help

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.fromLegacyMessage
import dev.meluhdy.scovilleCommands.ScovilleCommands
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.event.ClickEvent
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object StaffCommand : MelodiaCommand("staff") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf(
        MelodiaArgument("green", BoolArgumentType.bool(), this::sendMessage)
    )

    fun sendMessage(player: Player, green: Boolean): Boolean {

        val messages = TextUtils.translateList(ScovilleCommands.plugin, "command.help.staff", player.locale(), if (green) { "2" } else { "c" })
            .map { it.fromLegacyMessage() }.toMutableList()

        messages[1] = messages[1].clickEvent(ClickEvent.suggestCommand("/helpop "))
        messages[2] = messages[2].clickEvent(ClickEvent.openUrl("https://discord.gg/KMjNCHExYC"))

        messages.forEach { player.sendMessage(it) }

        return true

    }

    fun sendMessage(context: CommandContext<CommandSourceStack>): Int {

        return if (sendMessage(context.source.sender as Player, context.getArgument("green", Boolean::class.java))) {
            Command.SINGLE_SUCCESS
        } else { 0 }

    }

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {

        return if (sendMessage(context.source.sender as Player, false)) {
            Command.SINGLE_SUCCESS
        } else { 0 }

    }

}