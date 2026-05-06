package dev.meluhdy.scovilleCommands

import dev.meluhdy.melodia.MelodiaPlugin
import dev.meluhdy.melodia.command.MelodiaCommand
import dev.meluhdy.melodia.manager.MelodiaSavingManager
import dev.meluhdy.melodia.utils.ConsoleLogger
import dev.meluhdy.melodia.utils.LoggingUtils
import dev.meluhdy.melodia.utils.TranslationFolder
import dev.meluhdy.scovilleCommands.command.HelpCommand
import dev.meluhdy.scovilleCommands.command.LobbyCommand
import dev.meluhdy.scovilleCommands.command.PKCommand
import dev.meluhdy.scovilleCommands.command.JoinCommand
import dev.meluhdy.scovilleCommands.command.PlayingCommand
import dev.meluhdy.scovilleCommands.command.RulesCommand
import dev.meluhdy.scovilleCommands.command.TagCommand
import org.bukkit.event.Listener
import java.util.Locale

class ScovilleCommands : MelodiaPlugin() {

    companion object {
        lateinit var plugin: MelodiaPlugin
    }

    init {
        plugin = this
    }

    override val melodiaCommands: Array<MelodiaCommand> = arrayOf(
        PKCommand,
        LobbyCommand,
        JoinCommand,
        RulesCommand,
        HelpCommand,
        PlayingCommand,
        TagCommand
    )
    override val resourceFiles: Array<String> = arrayOf(
        "lang/en.properties",
        "lang/de.properties",
        "lang/ja.properties",
        "lang/pl.properties"
    )
    override val listeners: Array<Listener> = arrayOf()
    override val translationFolder: TranslationFolder = TranslationFolder("lang", Locale.of("en"))
    override val logger: ConsoleLogger = ConsoleLogger("ScovilleCommands", LoggingUtils.ConsoleLevel.DEBUG)
    override val savingManagers: Array<MelodiaSavingManager<*>> = arrayOf()


}
