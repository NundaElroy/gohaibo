package com.gohaibo.gohaibo.serviceint;

import com.gohaibo.gohaibo.dto.LoginDTO;
import com.gohaibo.gohaibo.dto.RegisterDTO;
import com.gohaibo.gohaibo.entity.User;

public interface AuthService {
    boolean registerUser(RegisterDTO registerDTO);
    String login(LoginDTO loginDTO);

}
