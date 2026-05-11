package dev.meluhdy.scovilleCommands.command

import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import io.papermc.paper.command.brigadier.CommandSourceStack

object HotbarCommand : MelodiaCommand("hotbar") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf()

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        return 0
    }

}