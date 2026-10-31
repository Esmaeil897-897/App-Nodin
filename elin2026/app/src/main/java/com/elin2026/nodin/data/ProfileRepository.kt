package com.elin2026.nodin.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

data class ChildProfile(
    val id: String,
    val name: String,
    val avatar: String,
    val createdAtEpochMillis: Long
)

/** Local-only profile catalogue stored in the app's private SharedPreferences. */
class ProfileRepository(context: Context) {
    private val preferences = context.getSharedPreferences("elin_profiles_v1", Context.MODE_PRIVATE)

    @Synchronized
    fun all(): List<ChildProfile> = readProfiles()

    @Synchronized
    fun active(): ChildProfile? {
        val id = preferences.getString(KEY_ACTIVE, null) ?: return null
        return readProfiles().firstOrNull { it.id == id }
    }

    @Synchronized
    fun create(name: String, avatar: String = "default"): ChildProfile {
        val cleanName = name.trim()
        require(cleanName.isNotEmpty() && cleanName.length <= 50) { "Name must contain 1–50 characters" }
        require(avatar.length <= 80) { "Avatar identifier is too long" }
        val profile = ChildProfile(UUID.randomUUID().toString(), cleanName, avatar, System.currentTimeMillis())
        val profiles = readProfiles().toMutableList().apply { add(profile) }
        writeProfiles(profiles)
        if (preferences.getString(KEY_ACTIVE, null) == null) {
            check(preferences.edit().putString(KEY_ACTIVE, profile.id).commit())
        }
        return profile
    }

    @Synchronized
    fun switchTo(profileId: String): ChildProfile {
        val profile = readProfiles().firstOrNull { it.id == profileId }
            ?: throw IllegalArgumentException("Profile does not exist")
        check(preferences.edit().putString(KEY_ACTIVE, profile.id).commit())
        return profile
    }

    @Synchronized
    fun rename(profileId: String, name: String): ChildProfile {
        val cleanName = name.trim()
        require(cleanName.isNotEmpty() && cleanName.length <= 50) { "Name must contain 1–50 characters" }
        val profiles = readProfiles().toMutableList()
        val index = profiles.indexOfFirst { it.id == profileId }
        require(index >= 0) { "Profile does not exist" }
        val updated = profiles[index].copy(name = cleanName)
        profiles[index] = updated
        writeProfiles(profiles)
        return updated
    }

    @Synchronized
    fun delete(profileId: String) {
        val remaining = readProfiles().filterNot { it.id == profileId }
        require(remaining.size != readProfiles().size) { "Profile does not exist" }
        writeProfiles(remaining)
        if (preferences.getString(KEY_ACTIVE, null) == profileId) {
            val editor = preferences.edit()
            if (remaining.isEmpty()) editor.remove(KEY_ACTIVE) else editor.putString(KEY_ACTIVE, remaining.first().id)
            check(editor.commit())
        }
    }

    private fun readProfiles(): List<ChildProfile> {
        val raw = preferences.getString(KEY_PROFILES, null) ?: return emptyList()
        return try {
            val array = JSONArray(raw)
            (0 until array.length()).map { i ->
                val item = array.getJSONObject(i)
                ChildProfile(
                    id = item.getString("id"),
                    name = item.getString("name"),
                    avatar = item.optString("avatar", "default"),
                    createdAtEpochMillis = item.getLong("createdAt")
                )
            }
        } catch (error: Exception) {
            throw IllegalStateException("Local profile data is unreadable; original data was not overwritten", error)
        }
    }

    private fun writeProfiles(profiles: List<ChildProfile>) {
        val array = JSONArray()
        profiles.forEach { profile ->
            array.put(
                JSONObject()
                    .put("id", profile.id)
                    .put("name", profile.name)
                    .put("avatar", profile.avatar)
                    .put("createdAt", profile.createdAtEpochMillis)
            )
        }
        check(preferences.edit().putString(KEY_PROFILES, array.toString()).commit()) {
            "Could not save local profiles"
        }
    }

    private companion object {
        const val KEY_PROFILES = "profiles"
        const val KEY_ACTIVE = "active_profile"
    }
}
