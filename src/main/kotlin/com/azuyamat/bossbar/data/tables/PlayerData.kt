package com.azuyamat.bossbar.data.tables

import me.outspending.munch.Column
import me.outspending.munch.ColumnType
import me.outspending.munch.PrimaryKey
import me.outspending.munch.Table
import java.util.*

@Table("Player")
data class PlayerData(
    @PrimaryKey val uuid: UUID,
    @Column(ColumnType.INT) val text: Int,
)
