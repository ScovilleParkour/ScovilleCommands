package dev.meluhdy.scovilleCommands.command.help

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.fromLegacyMessage
import dev.meluhdy.scovilleCommands.ScovilleCommands
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.event.ClickEvent
import org.bukkit.entity.Player

object InfoCommand : MelodiaCommand("info") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf()

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {

        val player = context.source.sender as Player

        val messages = TextUtils.translateList(ScovilleCommands.plugin, "command.help.info", player.locale())
            .map { it.fromLegacyMessage() }
            .toMutableList()

        messages[1] = messages[1].clickEvent(ClickEvent.runCommand("/tutorial"))
        messages[2] = messages[2].clickEvent(ClickEvent.runCommand("/help list"))

        messages.forEach { player.sendMessage(it) }

        return Command.SINGLE_SUCCESS

    }

}