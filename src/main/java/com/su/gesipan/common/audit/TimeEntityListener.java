package com.su.gesipan.common.audit;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public abstract class TimeEntityListener {

    @PrePersist
    public void prePersist(Object o){
        if (o instanceof BaseTimeEntity){
            ((BaseTimeEntity)o).setCreatedAt(LocalDateTime.now());
            ((BaseTimeEntity)o).setUpdatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object o){
        if(o instanceof BaseTimeEntity){
            ((BaseTimeEntity)o).setUpdatedAt(LocalDateTime.now());
        }
    }
}