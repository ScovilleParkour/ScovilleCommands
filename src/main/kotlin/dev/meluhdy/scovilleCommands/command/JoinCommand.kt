package dev.meluhdy.scovilleCommands.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.annotation.UserOnly
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.event.event.CourseJoinEvent
import dev.meluhdy.scovilleCommands.CommandUtil
import dev.meluhdy.scovilleCommands.ScovilleCommands
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.stream.Collectors

object JoinCommand : MelodiaCommand("join") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf(
        MelodiaArgument("course", StringArgumentType.greedyString(), this::joinCourse) { _, builder ->
            CourseManager.getAll().stream()
                .map { entry -> entry.name }
                .filter { entry -> entry != null && entry.lowercase().startsWith(builder.remainingLowerCase) }
                .forEach(builder::suggest)
            return@MelodiaArgument builder.buildFuture()
        }
    )

    @UserOnly
    fun joinCourse(context: CommandContext<CommandSourceStack>): Int {
        val player = context.source.sender as Player
        val course = CommandUtil.getCourse(context) ?: return 0
        Bukkit.getAsyncScheduler().runNow(ScovilleCommands.plugin) {
            CourseJoinEvent(player, course).callEvent()
        }
        return Command.SINGLE_SUCCESS
    }

    @UserOnly
    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendMessage("Usage: /join <COURSE NAME>")
        return 0
    }

}