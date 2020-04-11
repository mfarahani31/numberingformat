package com.gam.phoenix.numberingformat.config;

import org.slf4j.MDC;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    public Optional<String> getCurrentAuditor() {
        String username = MDC.get("USERNAME");
        username = StringUtils.isEmpty(username) ? "USERNAME" : username;
        return Optional.of(username);
    }
}
