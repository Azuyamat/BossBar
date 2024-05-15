package com.azuyamat.bossbar.data.tables

import me.outspending.munch.Column
import me.outspending.munch.PrimaryKey
import me.outspending.munch.Table
import java.util.*

@Table("Island")
data class IslandData(
    @PrimaryKey var uuid: UUID = UUID.randomUUID(),
    @Column var name: String,
    @Column var displayName: String = name,
) : Data<UUID> {
    override fun getId(): UUID {
        return this.uuid
    }
}
