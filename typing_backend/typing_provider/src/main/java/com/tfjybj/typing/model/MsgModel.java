package com.tfjybj.typing.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description:
 * @Author: swx
 * @Date: 2021/11/8
 * @Modified By:
 * @Modify Date:
 */
@ApiModel(value = "MsgModel:msgModel")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class MsgModel {
    /**
     * 接收者的dingId
     */
    private List<String> dingIds;

    /**
     * 钉钉群名 ：打字消息通知
     */
    private String groupName;

    /**
     * 发送消息的内容
     */
    private String messageContent;

    /**
     * 发送消息的标题
     */
    private String messageTitle;

    /**
     * 发送
     */
    private String sender;
}