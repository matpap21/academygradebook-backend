package com.sda.j92.academy.modelDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentDto {

    private String name;
    private String surname;
    private String pesel;
    private Long phoneNumber;
    private Long userId;
    private String email;


}
