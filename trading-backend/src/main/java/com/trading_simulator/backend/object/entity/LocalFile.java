package com.trading_simulator.backend.object.entity;

import com.trading_simulator.backend.common.enums.InputType;
import com.trading_simulator.backend.config.validation.ValidInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "local_file")
public class LocalFile {
    @Id
    private String id;

    @NotBlank
    private String eventId;

    @NotBlank
    private String owner;

    @ValidInput(
            type = InputType.COMMON_NAME,
            message = "Tên file chi chấp nhận chữ latin, tượng hình, chữ số, emoji và các ký tự [] () - _ : , . |"
    )
    @NotBlank
    private String name;

    @NotBlank
    private String url;
    private Long size;

    @NotNull
    @PastOrPresent(message = "The creation time cannot be in the future")
    private Instant createdAt;
}
