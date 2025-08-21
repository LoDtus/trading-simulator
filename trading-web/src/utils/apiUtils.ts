import axios from 'axios';
import { API_URL } from '../configs/constant';
import { pushNotify } from './commonUtils';

const setupRequestInterceptor = async(instance: any) => {
    instance.interceptors.request.use(
        async function (config: any) {
            return config;
        },
        function (error: any) {
            return Promise.reject(error);
        }
    );
}

const setupResponseInterceptor = (instance: any) => {
    instance.interceptors.response.use(
        function (response: any) {
            return response;
        },
        function (error: any) {
            if (axios.isAxiosError(error)) {
                if (error.code === "ECONNABORTED" && error.message.includes("timeout")) {
                    // notify("warning", "Timeout: Kết nối quá thời gian!");
                    return Promise.reject({
                        message: "Timeout: Kết nối quá thời gian!",
                        status: "TIMEOUT",
                        raw: error,
                    });
                }
                if (error.response) {
                    switch (error.response.status) {
                        case 401:
                            pushNotify("error", "401: Xác thực người dùng không hợp lệ!");
                            break;
                        case 403:
                            pushNotify("error", "403: Người dùng không có quyền truy cập!");
                            break;
                        case 404:
                            pushNotify("error", "404: Không thể xử lý yêu cầu!");
                            break;
                        case 500:
                            pushNotify("error", "500: Lỗi máy chủ!");
                            break;
                        default:
                            pushNotify("error", "Lỗi không xác định!");
                    }
                }
            } else {
                pushNotify("error", "Không thể kết nối đến máy chủ!");
            }
            // return { data: error.response.data };
            console.log("apiUtils - Error: ", error);
            return Promise.reject(error);
        }
    );
}

const instance = axios.create({
    baseURL: API_URL,
    withCredentials: true,
});

export const apiRequest = async(url: string, options: any) => {
    const { method = "get", body, params, headers = {} } = options;

    const isFormData = typeof FormData !== "undefined" && body instanceof FormData;
    const finalHeaders = { ...headers };

    // Nếu là POST/PUT/PATCH có body, tự set Content-Type nếu chưa có
    if (body && !isFormData && !finalHeaders["Content-Type"]) {
        finalHeaders["Content-Type"] = "application/json";
    }

    if (isFormData) { // Để Axios tự set boundary cho multipart
        delete finalHeaders["Content-Type"];
    }

    const config = {
        method,
        url,
        params,
        headers: finalHeaders,
        ...(method !== "get" && method !== "delete"
            ? { data: body } : body
            ? { data: body } : {}
        ),
    };

    try {
        const response = await instance(config);
        const apiRes = response.data;

        if (apiRes.statusCode && apiRes.statusCode !== 200) {
            // Backend trả lỗi nhưng vẫn trả về 200 HTTP
            throw {
                errorCode: apiRes.errorCode,
                statusCode: apiRes.statusCode,
                message: apiRes.message || "Lỗi không xác định",
                data: apiRes.data,
                timestamp: apiRes.timestamp,
            };
        }

        return apiRes.data;
    } catch (error: any) {
        // Lỗi do network hoặc backend crash (không trả ApiResponse)
        throw {
            message: error.response?.data?.message || error.message || "Không thể kết nối máy chủ",
            status: error.response?.status,
            data: error.response?.data,
        };
    }
};

setupRequestInterceptor(instance);
setupResponseInterceptor(instance);
export default instance;