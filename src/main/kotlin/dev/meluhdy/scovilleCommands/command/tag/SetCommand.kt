package dev.meluhdy.scovilleCommands.command.tag

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.scovilleCosmetics.core.tag.ChatTag
import dev.meluhdy.scovilleCosmetics.core.tag.TagManager
import dev.meluhdy.scovilleCommands.CommandUtil
import dev.meluhdy.scovilleCommands.ScovilleCommands
import io.papermc.paper.command.brigadier.CommandSourceStack
import java.util.UUID

object SetCommand : MelodiaCommand("set") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf(
        MelodiaArgument("id", StringArgumentType.word(), SetCommand::noArgs),
        MelodiaArgument("type", StringArgumentType.word(), SetCommand::noArgs),
        MelodiaArgument("tag", StringArgumentType.greedyString(), SetCommand::createTag)
    )

    fun createTag(context: CommandContext<CommandSourceStack>): Int {
        val typeId = context.getArgument("type", String::class.java) ?: return noArgs(context)
        val type = ChatTag.TagType.fromId(typeId) ?: return noArgs(context)
        val id = context.getArgument("id", String::class.java) ?: return noArgs(context)
        val tag = context.getArgument("tag", String::class.java) ?: return noArgs(context)

        val inst = TagManager.get { it.id == id }
        TagManager.add(ChatTag(id, tag, type, inst?.uuid ?: UUID.randomUUID()))
        return Command.SINGLE_SUCCESS
    }

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendMessage(TextUtils.translate(ScovilleCommands.plugin, "command.tag.set.usage", CommandUtil.getLocale(context)))
        return 0
    }
}