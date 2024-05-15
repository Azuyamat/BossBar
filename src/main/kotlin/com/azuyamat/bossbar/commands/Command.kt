package com.azuyamat.bossbar.commands

import co.aikar.commands.BaseCommand

abstract class Command : BaseCommand() {
    open fun init() {}
}