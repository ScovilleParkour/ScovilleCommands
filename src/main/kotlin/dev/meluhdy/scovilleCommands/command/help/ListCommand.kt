package dev.meluhdy.scovilleCommands.command.help

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.utils.TextUtils
import dev.meluhdy.melodia.utils.fromLegacyMessage
import dev.meluhdy.melodia.utils.uuid.UUIDManager
import dev.meluhdy.scoville.misc.track.StaffTrack
import dev.meluhdy.scovilleCommands.ScovilleCommands
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player

object ListCommand : MelodiaCommand("list") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf()

    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {

        val player = context.source.sender as Player
        val users = StaffTrack.getPlayers()

        fun getArg(rank: StaffTrack.StaffRank): String =
            (users.getOrElse(StaffTrack.asGroup(rank).name) { arrayListOf() }).joinToString(", ") {
                UUIDManager.getName(it)
            }

        println(getArg(StaffTrack.StaffRank.MODERATOR))

        TextUtils.translateList(ScovilleCommands.plugin, "command.help.list", player.locale(),
            getArg(StaffTrack.StaffRank.OWNER),
            getArg(StaffTrack.StaffRank.ADMIN),
            getArg(StaffTrack.StaffRank.MODERATOR),
            getArg(StaffTrack.StaffRank.HELPER)
        )
            .map { it.fromLegacyMessage() }
            .forEach { player.sendMessage(it) }

        return Command.SINGLE_SUCCESS

    }

}