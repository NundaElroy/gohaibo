package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.dto.LoginDTO;
import com.gohaibo.gohaibo.dto.RegisterDTO;

public interface AuthService {
    boolean registerUser(RegisterDTO registerDTO);
    String login(LoginDTO loginDTO);

}
