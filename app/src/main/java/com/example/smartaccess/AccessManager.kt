package com.example.smartaccess

import com.example.smartaccess.models.UserModel
import java.time.Duration
import java.time.Instant

class AccessManager(private val user: UserModel) {

    fun isInCoolingPeriod(now: Instant = Instant.now()): Boolean {
        val start = Instant.parse(user.coolingStartTime)
        val end = Instant.parse(user.coolingEndTime)
        return now.isAfter(start) && now.isBefore(end)
    }

    fun getCoolingCountdown(now: Instant = Instant.now()): String {
        val end = Instant.parse(user.coolingEndTime)
        val diff = Duration.between(now, end)
        val minutes = diff.toMinutes()
        val seconds = diff.seconds % 60
        if (diff.isNegative) return "Cooling ended"
        return String.format("Cooling ends in %02d:%02d", minutes, seconds)
    }

    fun hasAccess(moduleId: String): Boolean = user.accessibleModules.contains(moduleId)
}
