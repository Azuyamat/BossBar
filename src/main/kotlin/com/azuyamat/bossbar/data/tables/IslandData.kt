package com.azuyamat.bossbar.data.tables

import com.azuyamat.bossbar.serialization.SerializableList
import com.azuyamat.bossbar.data.enums.IslandRole
import com.azuyamat.bossbar.data.models.MemberMeta
import com.azuyamat.bossbar.registries.DatabaseRegistry
import com.azuyamat.bossbar.serialization.SerializableLocation
import com.azuyamat.bossbar.utils.fromConfig
import me.outspending.munch.Column
import me.outspending.munch.PrimaryKey
import me.outspending.munch.Table
import org.bukkit.Bukkit
import java.util.*

@Table("Island")
data class IslandData(
    @PrimaryKey var uuid: UUID = UUID.randomUUID(),
    @Column var name: String,
    @Column var displayName: String = name,
    @Column var members: SerializableList<MemberMeta> = SerializableList(),
    @Column val centerLocation: SerializableLocation,
    @Column var homeLocation: SerializableLocation = centerLocation.clone()
) : Data<UUID> {
    override fun getId(): UUID {
        return this.uuid
    }

    fun addMember(uuid: UUID, role: IslandRole) {
        members += MemberMeta(uuid, role)
    }

    fun removeMember(uuid: UUID) {
        members -= members.first { it.uuid == uuid }
    }

    fun isOwner(uuid: UUID): Boolean {
        return members.any { it.uuid == uuid && it.role == IslandRole.OWNER }
    }

    fun delete() {
        for (member in members) {
            Bukkit.getPlayer(member.uuid)?.sendMessage("island.delete.message".fromConfig())
            DatabaseRegistry.players.update(member.uuid) {
                it.islandUUID = null
            }
        }
        DatabaseRegistry.islands.delete(this)
    }
}
