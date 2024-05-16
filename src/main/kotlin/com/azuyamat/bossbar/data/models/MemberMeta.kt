package com.azuyamat.bossbar.data.models

import com.azuyamat.bossbar.data.enums.IslandRole
import java.io.Serializable
import java.util.UUID

data class MemberMeta(
    val uuid: UUID,
    val role: IslandRole
) : Serializable
