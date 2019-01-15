package com.dmr.dto;

import com.dmr.model.Gender;
import com.dmr.model.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String login;
    private Role role;
    private Gender gender;
}
