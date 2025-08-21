import { resetAllStores } from '../utils/storeUtils';
import { apiRequest } from '../utils/apiUtils';
import Cookies from "js-cookie";

export const signUp = async(
    email: string,
    username: string,
    password: string,
    address: string[],
    dateOfBirth: string,
    rememberMe: boolean = false
) => {
    const response = await apiRequest(`/api/auth/sign-up`, {
        method: "post",
        body: {
            email: email?.trim(),
            username: username?.trim(),
            password: password,
            nation: address[0]?.trim(),
            city: address[1]?.trim(),
            dateOfBirth: dateOfBirth,
            rememberMe: rememberMe,
        },
    });
    return response;
};

export const signIn = async(emailOrUsername: string, password: string, rememberMe: boolean = false) => {
    const response = await apiRequest(`/api/auth/sign-in`, {
        method: "post",
        body: {
            emailOrUsername: emailOrUsername?.trim(),
            password: password,
            rememberMe: rememberMe
        },
    });
    return response;
};

export const signOut = async() => {
    const response = await apiRequest(`/api/auth/sign-out`, { method: "get" });
    
    localStorage.clear();
    sessionStorage.clear();
    Object.keys(Cookies.get()).forEach((cookieName) => {
        Cookies.remove(cookieName);
    });
    resetAllStores();

    return response;
}

export const forgotPassword = async(email: string) => {
    const response = await apiRequest(`/api/auth/forgot-password?email=${email?.trim()}`, { method: "get" });
    return response;
};

export const checkResetPasswordToken = async(token: string) => {
    const response = await apiRequest(`/api/auth/check-reset-password-token?token=${token?.trim()}`, { method: "get" });
    return response;
};

export const resetPassword = async(token: string, newPassword: string) => {
    const response = await apiRequest(`/api/auth/reset-password`, {
        method: "post",
        body: {
            token: token?.trim(),
            newPassword: newPassword?.trim(),
        },
    });
    return response;
};