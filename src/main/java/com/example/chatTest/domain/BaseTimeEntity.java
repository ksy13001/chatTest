package com.example.chatTest.domain;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

/**
 *  생성시간 자동화
 * */

@Getter
@MappedSuperclass
@EntityListeners(AbstractMethodError.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime localDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
