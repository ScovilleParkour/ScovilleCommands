package dev.meluhdy.scovilleCommands.command

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

object RulesCommand : MelodiaCommand("rules") {

    private const val ARG: String = "page"

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf(
        MelodiaArgument(ARG, IntegerArgumentType.integer(), this::getRules)
    )

    fun sendRules(player: Player, page: Int): Boolean {

        val messages = TextUtils.translateList(ScovilleCommands.plugin, "command.rules.page.$page", player.locale())
            .map { it.fromLegacyMessage() }
            .toMutableList()

        messages[9] = messages[9].clickEvent(ClickEvent.runCommand("rules ${if (page == 1) { 2 } else { 1 }}"))

        messages.forEach { player.sendMessage(it) }

        return true

    }

    fun getRules(context: CommandContext<CommandSourceStack>): Int {

        return if (sendRules(context.source.sender as Player, context.getArgument(ARG, Int::class.java))) {
            Command.SINGLE_SUCCESS
        } else { 0 }

    }

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {

        return if (sendRules(context.source.sender as Player, 1)) {
            Command.SINGLE_SUCCESS
        } else { 0 }

    }
}