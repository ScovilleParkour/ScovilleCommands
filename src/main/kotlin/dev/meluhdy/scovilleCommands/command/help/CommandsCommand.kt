package dev.meluhdy.scovilleCommands.command.help

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.fromLegacyMessage
import dev.meluhdy.scovilleCommands.ScovilleCommands
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.event.ClickEvent
import org.bukkit.entity.Player

object CommandsCommand : MelodiaCommand("commands") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf(
        MelodiaArgument("page", IntegerArgumentType.integer(), this::sendPage)
    )

    fun sendPage(player: Player, page: Int): Boolean {

        val messages = TextUtils.translateList(ScovilleCommands.plugin, "command.help.commands.page.$page", player.locale())
            .map { it.fromLegacyMessage() }
            .toMutableList()

        messages[7] = messages[7].clickEvent(ClickEvent.runCommand("help commands ${if (page == 1) { 2 } else { 1 }}"))

        messages.forEach { player.sendMessage(it) }

        return true

    }

    fun sendPage(context: CommandContext<CommandSourceStack>): Int {

        return if (this.sendPage(context.source.sender as Player, context.getArgument("page", Int::class.java))) {
            Command.SINGLE_SUCCESS
        } else { 0 }

    }

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {

        return if (this.sendPage(context.source.sender as Player, 1)) {
            Command.SINGLE_SUCCESS
        } else { 0 }

    }

}