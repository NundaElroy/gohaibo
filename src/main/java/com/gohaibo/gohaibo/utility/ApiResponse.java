package com.gohaibo.gohaibo.utility;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {}

