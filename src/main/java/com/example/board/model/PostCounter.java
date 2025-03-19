package com.example.board.model;

import com.example.board.exception.BaseException;
import com.example.board.exception.ErrorMessage;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCounter {
    private Long userId;
    private int totalWriteInDay;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastPostedAt;
    private int totalWritePerMit;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastPostedInMin;

    private PostCounter(Long userId) {
        this.userId = userId;
        this.totalWriteInDay = 1;
        this.lastPostedAt = LocalDateTime.now();
        this.totalWritePerMit = 1;
        this.lastPostedInMin = LocalDateTime.now();
    }

    public static PostCounter firstPostInDay(Long userId) {
        return new PostCounter(userId);
    }

    public void update() {
        if (totalWriteInDay >= 9) {
            throw new BaseException(ErrorMessage.OVER_MAX_POST_PER_DAY);
        }
        if (checkWritesInMinuteOverCountFour()) return;
        if (lastPostedAt.plusMinutes(1).isAfter(LocalDateTime.now())) {
            totalWritePerMit++;
            totalWriteInDay++;
            lastPostedAt = LocalDateTime.now();
            return;
        }
        writeTimeOverMinute();

    }

    private boolean checkWritesInMinuteOverCountFour() {
        if (totalWritePerMit >= 4) {
            if (writeTimeOverMinute()) return true;
            throw new BaseException(ErrorMessage.OVER_MAX_POST_PER_MINUTE);
        }
        return false;
    }

    private boolean writeTimeOverMinute() {
        if (lastPostedAt.plusMinutes(1).isBefore(LocalDateTime.now())) {
            totalWritePerMit = 1;
            totalWriteInDay++;
            lastPostedAt = LocalDateTime.now();
            lastPostedInMin = lastPostedAt;
            return true;
        }
        ;
        return false;
    }
}
