package com.sda.j92.academy.modelDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserDto {
    private Long id;
    private String username;
    private boolean admin;
    private boolean lecturer;

}
