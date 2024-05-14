package com.azuyamat.bossbar.data.tables

import me.outspending.munch.Column
import me.outspending.munch.PrimaryKey
import me.outspending.munch.Table
import java.util.UUID

@Table("Island")
data class IslandData(
    @PrimaryKey var uuid: UUID,
    @Column var name: String,
    @Column var displayName: String
): Data<UUID> {
    override fun getId(): UUID {
        return this.uuid
    }
}
