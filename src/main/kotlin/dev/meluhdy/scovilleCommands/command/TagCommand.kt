package dev.meluhdy.scovilleCommands.command

import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.scovilleCommands.CommandUtil
import dev.meluhdy.scovilleCommands.ScovilleCommands
import dev.meluhdy.scovilleCommands.command.tag.SetCommand
import io.papermc.paper.command.brigadier.CommandSourceStack

object TagCommand : MelodiaCommand("tag") {

    override val children: List<MelodiaCommand> = listOf(
        SetCommand
    )
    override val arguments: List<MelodiaArgument<*>> = listOf()

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendMessage(TextUtils.translate(ScovilleCommands.plugin, "command.tag.usage", CommandUtil.getLocale(context)))
        return 0
    }

}