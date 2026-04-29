package dev.meluhdy.scovilleCommands

import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.scoville.core.course.AbstractCourse
import dev.meluhdy.scoville.core.course.CourseManager
import io.papermc.paper.command.brigadier.CommandSourceStack

object CommandUtil {

    fun getCourse(context: CommandContext<CommandSourceStack>, courseArg: String = "course"): AbstractCourse? {
        val courseName = context.getArgument(courseArg, String::class.java)
        val course = CourseManager.get(courseName)
        if (course == null) {
            context.source.sender.sendMessage("Invalid course: $courseName")
        }
        return course
    }

}