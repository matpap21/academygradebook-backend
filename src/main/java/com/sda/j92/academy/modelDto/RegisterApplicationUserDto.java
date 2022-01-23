package com.sda.j92.academy.modelDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterApplicationUserDto {
    private String username;
    private String password;
    private String role;
    private String name;
    private String surname;
    private String pesel;
}
