package com.maenguin.kdtbbs.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Component
public class OptimisticLockTryer {

    public <T> T attempt(Supplier<T> supplier, int tryCount) {
        T result = null;
        for (int i = 0; i < tryCount; i++) {
            try {
                result = supplier.get();
            } catch (ObjectOptimisticLockingFailureException e) {
                log.error("error occurred when try {}", i, e);
                continue;
            }
            break;
        }
        return result;
    }
}
