import { notification } from "ant-design-vue";

// -------------------- SYSTEM UTILS: Các hàm sử dụng chung cho toàn hệ thống --------------------
/* Thông báo toàn cục */
type NotificationType = 'success' | 'error' | 'warning' | 'info';
type NotificationPlacement = "top" | "topLeft" | "topRight" | "bottom" | "bottomLeft" | "bottomRight";
export const pushNotify = (
    type: NotificationType = "info",
    description: string,
    placement: NotificationPlacement = 'topRight',
) => {

    const message = "Thông báo";
    notification[type]({
        message: message,
        description,
        placement: placement,
        duration: 3,
    });
};

// -------------------- DATE UTILS: Các hàm xử lý thời gian --------------------
export const convertFromISO2String = () => {

};

/** Chuyển từ backend sang:
 * ngày/tháng/năm
 * ngày-tháng-năm
 * ngày/tháng/năm giờ:phút:giây
 * vừa xong, 1 phút/tiếng/ngày trước ... max = 3 ngày trước
 */

/** Chuyển từ fe sang ISO
 * yyyy-MM-ddThh:mm:sssZ
 */

// Chuyển

// -------------------- VALIDATION UTILS: Các hàm kiểm tra hợp lệ --------------------
export const isValidEmail = (email: string) => {
    
};

const invalidCharacters = {
    username: [],
    password: [],
    address: [],
    bio: [],
};
export const hasSpecialCharactersInText = (type: string, text: string) => {
    switch (type) {
        case "username":

        case "password":

        case "address":

        case "bio":

    }
};

// -------------------- CONVERT UTILS: Các hàm chuyển đổi --------------------
export const convertGender = (genderDb: string) => {
    return genderDb === "MALE" ? "Nam"
        : genderDb === "FEMALE" ? "Nữ"
        : "Khác";
};

export const convertRole = (roleDb: string) => {
    return roleDb === "ROLE_ADMIN" ? "Quản trị viên"
        : roleDb === "ROLE_USER" ? "Người dùng cơ bản"
        : "Người dùng cơ bản";
};