package dev.meluhdy.scovilleCommands.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.fromLegacyMessage
import dev.meluhdy.scovilleCommands.ScovilleCommands
import dev.meluhdy.scovilleCommands.command.help.CommandsCommand
import dev.meluhdy.scovilleCommands.command.help.InfoCommand
import dev.meluhdy.scovilleCommands.command.help.ListCommand
import dev.meluhdy.scovilleCommands.command.help.SocialsCommand
import dev.meluhdy.scovilleCommands.command.help.StaffCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.event.ClickEvent
import org.bukkit.entity.Player

object HelpCommand : MelodiaCommand("help") {

    override val children: List<MelodiaCommand> = listOf(
        StaffCommand,
        SocialsCommand,
        InfoCommand,
        ListCommand,
        CommandsCommand
    )
    override val arguments: List<MelodiaArgument<*>> = listOf()

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        val player: Player = context.source.sender as Player

        val help = TextUtils.translateList(ScovilleCommands.plugin, "command.help", player.locale()).map { it.fromLegacyMessage() }
            .toMutableList()

        mapOf(
            2 to "rules",
            3 to "help staff",
            4 to "help socials",
            5 to "help commands",
            6 to "help info",
            7 to "help staff true"
        ).forEach { (i, string) -> help[i] = help[i].clickEvent(ClickEvent.runCommand(string)) }

        help.forEach { player.sendMessage(it) }

        return Command.SINGLE_SUCCESS
    }

}