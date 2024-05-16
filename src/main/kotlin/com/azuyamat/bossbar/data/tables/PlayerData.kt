package com.azuyamat.bossbar.data.tables

import com.azuyamat.bossbar.data.enums.ChatColor
import me.outspending.munch.Column
import me.outspending.munch.PrimaryKey
import me.outspending.munch.Table
import java.util.*

@Table("Player")
data class PlayerData(
    @PrimaryKey val uuid: UUID,
    @Column val chatColor: ChatColor = ChatColor.RED,
    @Column var islandUUID: UUID? = null
) : Data<UUID> {
    override fun getId(): UUID {
        return this.uuid
    }
}
