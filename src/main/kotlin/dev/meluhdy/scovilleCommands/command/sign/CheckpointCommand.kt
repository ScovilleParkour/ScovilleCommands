package dev.meluhdy.scovilleCommands.command.sign

import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import dev.meluhdy.melodia.annotation.UserOnly
import dev.meluhdy.melodia.command.MelodiaArgument
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.utils.ItemUtils
import dev.meluhdy.melodia.utils.fromLegacyMessage
import dev.meluhdy.scovilleCommands.CommandUtil
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.Material
import org.bukkit.block.Sign
import org.bukkit.block.sign.Side
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta

object CheckpointCommand : MelodiaCommand("cp") {

    override val children: List<MelodiaCommand> = listOf()
    override val arguments: List<MelodiaArgument<*>> = listOf(
        MelodiaArgument("course", StringArgumentType.greedyString(), ::getSign)
    )

    @UserOnly
    fun getSign(context: CommandContext<CommandSourceStack>): Int {

        val course = CommandUtil.getCourse(context) ?: return 0
        val player = context.source.sender as Player

        val sign = ItemUtils.createItem(Material.OAK_SIGN, 1, "${course.coloredName ?: return 0} &7CP Sign".fromLegacyMessage())
        val meta = sign.itemMeta
        val state = (meta as BlockStateMeta).blockState as Sign

        state.getSide(Side.FRONT).line(0, "&8[&4Scoville&8]".fromLegacyMessage())
        state.getSide(Side.FRONT).line(1, (course.coloredName ?: return 0).fromLegacyMessage())
        state.getSide(Side.FRONT).line(2, "&2✔ &aCheckpoint &2✔".fromLegacyMessage())
        state.getSide(Side.FRONT).line(3, "&7(right click)".fromLegacyMessage())

        meta.blockState = state
        sign.itemMeta = meta

        player.give(sign)

        return Command.SINGLE_SUCCESS
    }

    @UserOnly
    override fun noArgs(context: CommandContext<CommandSourceStack>): Int {
        context.source.sender.sendMessage("No")
        return 0
    }

}