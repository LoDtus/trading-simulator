package com.trading_simulator.backend.controller;

import com.trading_simulator.backend.service.UserService;
import com.trading_simulator.backend.object.dto.user.UpdateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "Các API tương tác với thông tin cá nhân của người dùng")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Lấy thông tin người dùng",
            description = "Tìm kiếm, lọc thông tin của người dùng dựa trên filter"
    )
    @PostMapping("/get")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok("");
    }

    @Operation(
            summary = "Cập nhật thông tin người dùng",
            description = "Cập nhật thông tin của 1 người dùng nào đó",
            security = {
                    @SecurityRequirement(name = "bearerAuth"),
                    @SecurityRequirement(name = "oauth2", scopes = {"read", "write"})
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Thành công",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "John",
                                    value = "{ \"id\": \"123\", \"name\": \"john\" }"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Email đã tồn tại"
            )
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            HttpServletRequest request,
            @ModelAttribute UpdateUserRequest updateUserRequest
    ) {
//        String id = ""; // get from request
//        if (!id.equals(updateUserRequest.getId())) {
//            Auth auth = userService.findById(id);
//
//            // nếu id người gửi khác id trong update thì chỉ có admin được phép cập nhật thôi
////            if (!auth.getRole().equals("ROLE_ADMIN")) {
////                return ResponseEntity.ok("");
////            }
//        }

//        Auth auth = userService.findById(updateUserRequest.getId());
//        if (auth == null) {
//            return ResponseEntity.ok("");
//        }
//        if (!auth.getEmail().trim().equals(updateUserRequest.getEmail())
//                && userService.existsByEmail(updateUserRequest.getEmail())
//        ) {
//            return ResponseEntity.ok("");
//        }
//        if (!auth.getUsername().trim().equals(updateUserRequest.getUsername())
//                && userService.existsByUsername(updateUserRequest.getUsername())
//        ) {
//            return ResponseEntity.ok("");
//        }


        //
        // lọc ký tự khỏi bio, address, password, username
        // date of birth không được quá thời điểm hiện tại



        return ResponseEntity.ok("");
    }

    @Operation(
            summary = "Xóa người dùng",
            description = "Xóa thông tin của 1 hoặc nhiều người dùng thông qua mảng các id người dùng cần xóa"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUsers(
            @Parameter(
                    name = "ids",
                    description = "Danh sách ID của User cần xóa",
                    in = ParameterIn.QUERY,
                    array = @ArraySchema(schema = @Schema(type = "string")),
                    style = ParameterStyle.FORM,
                    example = "[\"user-1\",\"user-2\"]"
            )
            // Chỉ khả thi với người dùng ở khoảng dưới 100 người, nếu quá lớn thì phải chuyển sang dạng Post
            @RequestParam List<String> ids
    ) {
        for (String id : ids) {

        }
        return ResponseEntity.ok("");
    }
}
