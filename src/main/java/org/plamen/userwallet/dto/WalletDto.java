package org.plamen.userwallet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WalletDto {
    private Long userId;
    @NotBlank
    private String name;
}
