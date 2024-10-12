import { defineStore } from "pinia";
import { computed, reactive, ref } from "vue";
import { resetRouter } from "@/router";
import {
    type AccountLoginReq,
    type EmailLoginReq,
    type PhoneLoginReq,
    type UserInfo,
    accountLogin as accountLoginApi,
    emailLogin as emailLoginApi,
    getUserInfo as getUserInfoApi,
    logout as logoutApi,
    phoneLogin as phoneLoginApi,
} from "@/apis";
import { clearToken, getToken, setToken } from "@/utils/auth";
import { resetHasRouteFlag } from "@/router/permission";
import getAvatar from "@/utils/avatar";

const storeSetup = () => {
    const userInfo = reactive<UserInfo>({
        id: "",
        username: "",
        nickname: "",
        gender: 0,
        email: "",
        phone: "",
        avatar: "",
        pwdResetTime: "",
        pwdExpired: false,
        registrationDate: "",
        deptName: "",
        roles: [],
        permissions: [],
    });
    const name = computed(() => userInfo.nickname);
    const avatar = computed(() => userInfo.avatar);

    const token = ref(getToken() || "");
    const pwdExpiredShow = ref<boolean>(true);
    const roles = ref<string[]>([]); // 当前用户角色
    const permissions = ref<string[]>([]); // 当前角色权限标识集合

    // 重置token
    const resetToken = () => {
        token.value = "";
        clearToken();
        resetHasRouteFlag();
    };

    // 登录
    const accountLogin = async (req: AccountLoginReq) => {
        const res = await accountLoginApi(req);
        setToken(res.data.token);
        token.value = res.data.token;
    };

    // 邮箱登录
    const emailLogin = async (req: EmailLoginReq) => {
        const res = await emailLoginApi(req);
        setToken(res.data.token);
        token.value = res.data.token;
    };

    // 手机号登录
    const phoneLogin = async (req: PhoneLoginReq) => {
        const res = await phoneLoginApi(req);
        setToken(res.data.token);
        token.value = res.data.token;
    };

    // 三方账号登录
    // const socialLogin = async (source: string, req: any) => {
    //   const res = await socialLoginApi(source, req)
    //   setToken(res.data.token)
    //   token.value = res.data.token
    // }

    // 退出登录回调
    const logoutCallBack = async () => {
        roles.value = [];
        permissions.value = [];
        pwdExpiredShow.value = true;
        resetToken();
        resetRouter();
    };

    // 退出登录
    const logout = async () => {
        try {
            await logoutApi();
            await logoutCallBack();
            return true;
        } catch (error) {
            return false;
        }
    };

    // 获取用户信息
    const getInfo = async () => {
        const res = await getUserInfoApi();
        Object.assign(userInfo, res.data);
        userInfo.avatar = getAvatar(res.data.avatar, res.data.gender);
        if (res.data.roles && res.data.roles.length) {
            roles.value = res.data.roles;
            permissions.value = res.data.permissions;
        }
    };

    return {
        userInfo,
        name,
        avatar,
        token,
        roles,
        permissions,
        pwdExpiredShow,
        accountLogin,
        emailLogin,
        phoneLogin,
        logout,
        logoutCallBack,
        getInfo,
        resetToken,
    };
};

interface PersistenceOptions {
    paths?: string[];
    storage?: Storage;
}

export const useUserStore = defineStore("user", storeSetup, {
    persist: {
        paths: ["token", "roles", "permissions", "pwdExpiredShow"] as string[],
        storage: localStorage,
    } as PersistenceOptions,
});
