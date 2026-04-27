package dev.meluhdy.scovilleCommands.command.pk

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.annotation.RequirePerm
import dev.meluhdy.melodia.annotation.UserOnly
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.scoville.gui.admin.course.CourseCreateTypeGUI
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player

object CreateCommand : MelodiaCommand("create") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf(
        MelodiaArgument("course", StringArgumentType.greedyString(), this::createCourse)
    )

    @UserOnly
    @RequirePerm("scoville.course.create")
    fun createCourse(context: CommandContext<CommandSourceStack>): Int {
        val courseName = context.getArgument("course", String::class.java)
        CourseCreateTypeGUI(context.source.sender as Player, null, courseName).open()
        return Command.SINGLE_SUCCESS
    }

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendMessage("Usage: /pk create <COURSE NAME>")
        return 0
    }

}