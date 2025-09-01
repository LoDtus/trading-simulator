db = db.getSiblingDB("trading-simulator-db");
db.dropDatabase();

db.api_permission.insertMany([
    { "pattern": "/test/**", "method": "ALL", "roleIds": [], "description": "Api Test", "enabled": true },
    { "pattern": "/ws/**", "method": "GET", "roleIds": [], "description": "Api kết nối WebSocket", "enabled": true },

    { "pattern": "/api/auth/sign-up", "method": "POST", "roleIds": [], "description": "Api đăng nhập", "enabled": true },
    { "pattern": "/api/auth/sign-in", "method": "POST", "roleIds": [], "description": "Api đăng ký", "enabled": true },
    { "pattern": "/api/auth/sign-out", "method": "GET", "roleIds": [], "description": "Api đăng xuất", "enabled": true },
    { "pattern": "/api/auth/forgot-password", "method": "GET", "roleIds": [], "description": "Api quên mật khẩu", "enabled": true },
    { "pattern": "/api/auth/check-reset-password-token", "method": "GET", "roleIds": [], "description": "Api kiểm tra token đặt lại mật khẩu", "enabled": true },
    { "pattern": "/api/auth/reset-password", "method": "POST", "roleIds": [], "description": "Api đặt lại mật khẩu", "enabled": true },
    { "pattern": "/api/auth/check-email", "method": "GET", "roleIds": [], "description": "Api kiểm tra tồn tại của email", "enabled": true },
    { "pattern": "/api/auth/check-username", "method": "GET", "roleIds": [], "description": "Api kiểm tra tồn tại của username", "enabled": true },

    { "pattern": "/api/api-permission/**", "method": "GET", "roleIds": ["ADMIN"], "description": "Api xem các endpoint đang có trong hệ thống", "enabled": true },
    { "pattern": "/api/role/**", "method": "ALL", "roleIds": ["ADMIN"], "description": "Api tương tác với các vai trò trong hệ thống", "enabled": true },

    { "pattern": "/api/user/get", "method": "POST", "roleIds": [], "description": "Api lấy, tìm kiếm và lọc người dùng", "enabled": true },
    { "pattern": "/api/user/update", "method": "PUT", "roleIds": ["USER", "ADMIN"], "description": "Api cập nhật thông tin người dùng", "enabled": true },
    { "pattern": "/api/user/delete", "method": "DELETE", "roleIds": ["USER", "ADMIN"], "description": "Api xóa người dùng", "enabled": true },

    { "pattern": "/api/rank/get", "method": "POST", "roleIds": ["USER", "ADMIN"], "description": "Api xem thông tin về bảng xếp hạng", "enabled": true },

    { "pattern": "/api/scenario/get", "method": "POST", "roleIds": ["USER", "ADMIN"], "description": "Api lấy, tìm và lọc các kịch bản giao dịch", "enabled": true },
    { "pattern": "/api/scenario/add", "method": "POST", "roleIds": ["USER", "ADMIN"], "description": "Api thêm kịch bản giao dịch", "enabled": true },
    { "pattern": "/api/scenario/update", "method": "PUT", "roleIds": ["USER", "ADMIN"], "description": "Api cập nhật thông tin kịch bản", "enabled": true },
    { "pattern": "/api/scenario/delete", "method": "DELETE", "roleIds": ["USER", "ADMIN"], "description": "Api xóa kịch bản", "enabled": true },

    { "pattern": "/api/feedback/get", "method": "POST", "roleIds": [], "description": "Api lấy, tìm và lọc các góp ý", "enabled": true },
    { "pattern": "/api/feedback/add", "method": "POST", "roleIds": ["USER", "ADMIN"], "description": "Api thêm góp ý", "enabled": true },
    { "pattern": "/api/feedback/update", "method": "PUT", "roleIds": ["USER", "ADMIN"], "description": "Api cập nhật góp ý", "enabled": true },
    { "pattern": "/api/feedback/delete", "method": "DELETE", "roleIds": ["USER", "ADMIN"], "description": "Api xóa góp ý", "enabled": true },

    { "pattern": "/api/support/get-topics", "method": "POST", "roleIds": ["USER", "ADMIN"], "description": "Api lấy, tìm và lọc các chủ đề đã đặt ra trong hệ thống", "enabled": true },
    { "pattern": "/api/support/get-qa", "method": "POST", "roleIds": ["USER", "ADMIN"], "description": "Api lấy và lọc ra các câu hỏi và trả lời của chủ đề nào đó", "enabled": true },
    { "pattern": "/api/support/add-topic", "method": "POST", "roleIds": ["USER", "ADMIN"], "description": "Api thêm một chủ đề mới", "enabled": true },
    { "pattern": "/api/support/ask", "method": "POST", "roleIds": ["USER", "ADMIN"], "description": "Api đặt câu hỏi trong chủ đề", "enabled": true },
    { "pattern": "/api/support/update-topic", "method": "PUT", "roleIds": ["USER", "ADMIN"], "description": "Api cập nhật thông tin của chủ đề", "enabled": true },
    { "pattern": "/api/support/update-qa", "method": "PUT", "roleIds": ["USER", "ADMIN"], "description": "Api cập nhật nội dung câu hỏi", "enabled": true },
    { "pattern": "/api/support/delete-topic", "method": "DELETE", "roleIds": ["USER", "ADMIN"], "description": "Api xóa chủ đề", "enabled": true },
    { "pattern": "/api/support/delete-qa", "method": "DELETE", "roleIds": ["USER", "ADMIN"], "description": "Api xóa câu hỏi", "enabled": true }
]);

db.role.insertMany([
    {
        "_id": "ROLE_ADMIN",
        "role": "Administrator",
        "description": "Quản trị viên hệ thống: Là người có vai trò cao nhất của hệ thống",
        "createdAt": ISODate(),
        "updatedAt": ISODate()
    },
    {
        "_id": "ROLE_USER",
        "role": "User",
        "description": "Người dùng cơ bản: Là người dùng chính của hệ thống",
        "createdAt": ISODate(),
        "updatedAt": ISODate()
    }
]);

db.user.insertMany([
    {
        "_id": ObjectId("000000000000000000000001"),
        "email": "nguyentrunglong.work@gmail.com",
        "username": "admin-no1",
        "password": "{noop}123",
        "role": DBRef("role", "ROLE_ADMIN"),
        "active": true
    },
    {
        "_id": ObjectId("000000000000000000000002"),
        "email": "nguyentrunglong.150903@gmail.com",
        "username": "user-no1",
        "password": "{noop}123",
        "role": DBRef("role", "ROLE_USER"),
        "active": true
    },
    {
        "_id": ObjectId("000000000000000000000003"),
        "email": "nguyentrunglong.15092003@gmail.com",
        "username": "user-no2",
        "password": "{noop}123",
        "role": DBRef("role", "ROLE_USER"),
        "active": true
    }
]);

db.profile.insertMany([
    {
        "_id": ObjectId("000000000000000000000001"),
        "image": "/default-user-image.png",
        "status": "ONLINE",
        "bio": "System administrator - Quản trị viên hệ thống. Yêu thích công nghệ, truyện tranh, nghe nhạc.",
        "dateOfBirth": ISODate("1995-01-01T00:00:00Z"),
        "address": ["Vietnam", "Hanoi"],
        "createdAt": ISODate(),
        "updatedAt": ISODate()
    },
    {
        "_id": ObjectId("000000000000000000000002"),
        "image": "/default-user-image.png",
        "status": "OFFLINE_" + ISODate().toISOString(),
        "bio": "User cơ bản - Sinh viên trường đại học Thăng Long. Mong muốn được đi du lịch nhiều nơi.",
        "dateOfBirth": ISODate("2003-09-15T00:00:00Z"),
        "address": ["Vietnam", "Da Nang"],
        "createdAt": ISODate(),
        "updatedAt": ISODate()
    },
    {
        "_id": ObjectId("000000000000000000000003"),
        "image": "/default-user-image.png",
        "status": "OFFLINE_" + ISODate().toISOString(),
        "bio": "User cơ bản - Quan tâm tới công nghệ phần mềm và mong muốn được tiến tới các vị trí cao hơn trong ngành.",
        "dateOfBirth": ISODate("2003-09-20T00:00:00Z"),
        "address": ["Vietnam", "Ho Chi Minh City"],
        "createdAt": ISODate(),
        "updatedAt": ISODate()
    }
]);