package com.tfjybj.typing.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Yara
 * @Date 2020/11/1 21:08
 * @Version 1.0
 */
@Data
public class ImproveReportModel implements Serializable {
    private String id;
    private String userName;
    private String level;
    private String userId;
    private String className;
    private LocalDateTime createTime;
}
