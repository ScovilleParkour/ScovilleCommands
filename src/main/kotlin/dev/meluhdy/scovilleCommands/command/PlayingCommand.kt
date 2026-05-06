package dev.meluhdy.scovilleCommands.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.uuid.UUIDManager
import dev.meluhdy.scoville.core.course.CourseManager
import dev.meluhdy.scoville.core.parkourer.ParkourerManager
import dev.meluhdy.scovilleCommands.CommandUtil
import dev.meluhdy.scovilleCommands.ScovilleCommands
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.Locale
import java.util.UUID

object PlayingCommand : MelodiaCommand("playing") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf(
        MelodiaArgument("player", StringArgumentType.string(), this::getPlaying) { ctx, builder ->
            Bukkit.getOnlinePlayers().forEach { player -> builder.suggest(player.name) }
            return@MelodiaArgument builder.buildFuture()
        }
    )

    fun getPlaying(context: CommandContext<CommandSourceStack>): Int {

        val sender = context.source.sender

        val username = context.getArgument("player", String::class.java)
        val uuid: UUID
        try {
             uuid = UUIDManager.getUUID(username)
        } catch (_: NullPointerException) {
            sender.sendMessage("That player does not exist!")
            return 0
        }

        val parkourer = ParkourerManager.get(uuid) ?: return 0
        val courseID = parkourer.currentlyPlaying
        if (courseID == null) {
            sender.sendMessage(TextUtils.translate(ScovilleCommands.plugin, "command.playing.nothing", CommandUtil.getLocale(context)))
            return 0
        }

        val course = CourseManager.get(courseID) ?: return 0
        sender.sendMessage(TextUtils.translate(ScovilleCommands.plugin, "command.playing.course", CommandUtil.getLocale(context), course.name ?: "UNKNOWN COURSE"))
        return Command.SINGLE_SUCCESS

    }

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendMessage(TextUtils.translate(
            ScovilleCommands.plugin,
            "command.playing.usage",
            CommandUtil.getLocale(context)
        ))
        return 0
    }

}