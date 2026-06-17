package com.workspace.features.auth.infrastructure.config;

import com.workspace.features.auth.application.dto.*;
import com.workspace.features.auth.application.service.*;
import com.workspace.features.auth.infrastructure.config.*;
import com.workspace.features.auth.infrastructure.persistence.entity.*;
import com.workspace.features.auth.infrastructure.persistence.jpaRepo.*;
import com.workspace.features.auth.infrastructure.web.dto.*;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.auth")
public record AuthProperties(
        String jwtSecret,
        long accessTokenExpirationMs,
        long refreshTokenExpirationMs
) {
}
