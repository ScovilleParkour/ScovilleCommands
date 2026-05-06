package dev.meluhdy.scovilleCommands

import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.CourseManager
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player
import java.util.Locale

object CommandUtil {

    fun getCourse(context: CommandContext<CommandSourceStack>, courseArg: String = "course"): AbstractCourse? {
        val courseName = context.getArgument(courseArg, String::class.java)
        val course = CourseManager.get(courseName)
        if (course == null) {
            context.source.sender.sendMessage("Invalid course: $courseName")
        }
        return course
    }

    fun getLocale(context: CommandContext<CommandSourceStack>): Locale {
        val sender = context.source.sender
        if (sender is Player) {
            return sender.locale()
        }
        return Locale.of("en")
    }

}