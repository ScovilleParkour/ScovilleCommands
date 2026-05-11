package dev.meluhdy.scovilleCommands.command

import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.scovilleCommands.command.sign.CheckpointCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component

object SignCommand : MelodiaCommand("sign") {

    override val children: List<MelodiaCommand> = listOf(
        CheckpointCommand
    )
    override val arguments: List<MelodiaArgument<*>> = listOf()

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendMessage(Component.text("No"))
        return 0
    }

}