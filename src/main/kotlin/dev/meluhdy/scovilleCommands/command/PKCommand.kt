package dev.meluhdy.scovilleCommands.command

import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.scovilleCommands.command.pk.MenuCommand
import dev.meluhdy.scovilleCommands.command.pk.PlateCommand
import dev.meluhdy.scovilleCommands.command.pk.CreateCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component

object PKCommand: MelodiaCommand("pk") {

    override val children: List<MelodiaCommand> = listOf(
        MenuCommand,
        PlateCommand,
        CreateCommand
    )
    override val arguments: List<MelodiaArgument<*>> = listOf()

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendMessage(Component.text("No"))
        return 0
    }

}