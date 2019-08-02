package ru.skoroxod.backends.vk

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject
import ru.skoroxod.UserInfo
import timber.log.Timber


data class VKUser(val name: String) {
    companion object {
        fun parse(jsonObject: JSONObject): VKUser {
            return VKUser("xz")
        }

        fun toUserInfo(vkUser: VKUser): UserInfo {
            return UserInfo(vkUser.name, "", "")
        }

    }
}

class VKUserRequest(uid: Int) : VKRequest<List<VKUser>>("users.get") {
    init {
        addParam("user_ids", uid)
        addParam("fields", "photo_200")
    }

    override fun parse(r: JSONObject): List<VKUser> {
        Timber.tag("json").d("${r.toString(4)}")
        val users = r.getJSONArray("response")
        val result = ArrayList<VKUser>()
        for (i in 0 until users.length()) {
            result.add(VKUser.parse(users.getJSONObject(i)))
        }
        return result
    }
}

class VKProfileRequest(uids: IntArray = intArrayOf()) : VKRequest<VKUser>("users.get") {

    override fun parse(r: JSONObject): VKUser {
        Timber.tag("json").d("${r.toString(4)}")
//        val users = r.getJSONArray("response")
//        val result = ArrayList<VKUser>()
//        for (i in 0 until users.length()) {
//            result.add(VKUser.parse(users.getJSONObject(i)))
//        }
        return VKUser("xxxx")
    }
}