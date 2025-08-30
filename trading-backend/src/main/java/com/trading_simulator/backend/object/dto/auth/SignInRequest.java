package com.trading_simulator.backend.object.dto.auth;

import com.trading_simulator.backend.config.validation.InputType;
import com.trading_simulator.backend.config.validation.ValidInput;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "Thông tin đăng nhập tài khoản")
public class SignInRequest {
    @Schema(description = "Email hặc username người dùng")
    @NotBlank(message = "Email or username is required")
    private String emailOrUsername;

    @Schema(description = "Mật khẩu")
    @NotBlank(message = "Password is required")
//    @ValidInput(
//            type = InputType.PASSWORD,
//            message = "Mật khẩu tối thiểu 3 ký tự, với 1 ký tự viết hoa, 1 ký tự viết thường, 1 chữ số"
//    )
    private String password;

    @Schema(description = "Tùy chọn duy trì đăng nhập")
    @NotNull(message = "Remember me is required")
    private Boolean rememberMe;
}
