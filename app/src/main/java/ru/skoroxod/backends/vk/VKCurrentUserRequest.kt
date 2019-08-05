package ru.skoroxod.backends.vk

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject
import ru.skoroxod.repo.UserInfo
import timber.log.Timber


data class VKUser(val id: String, val name: String, val photo: String) {
    companion object {
        fun fromJson(jsonObject: JSONObject): VKUser {

            return VKUser(
                jsonObject.getString("id"),
                jsonObject.getString("first_name") + jsonObject.getString("last_name"),
                jsonObject.getString("photo_200")
            )
        }

        fun toUserInfo(vkUser: VKUser): UserInfo {
            return UserInfo(vkUser.id, vkUser.name, "", vkUser.photo)
        }

    }
}

class VKCurrentUserRequest : VKRequest<VKUser>("users.get") {
    init {
        addParam("fields", "photo_200")
    }

    override fun parse(r: JSONObject): VKUser {
        Timber.tag("json").d("${r.toString(4)}")
        val response = r.getJSONArray("response")
        return VKUser.fromJson(response.getJSONObject(0))
    }
}

