package com.tfjybj.typing.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dmsdbj.itoo.tool.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;

@ApiModel(value = "NoticeEntity:ty_notice")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ty_notice")
public class NoticeEntity extends BaseEntity implements Serializable {



    /**
     * 消息Id
     */
    @ApiModelProperty(value = "消息Id")
    @Column(name = "notice_id")
    private String noticeId;

    /**
     * 消息名称
     */
    @ApiModelProperty(value = "消息名称")
    @Column(name = "notice_name")
    private String noticeName;
    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容")
    @Column(name = "notice")
    private String notice;
    /**
     * 播放时间
     */

    @ApiModelProperty(value = "播放时间")
    @Column(name = "set_time")
    private String setTime;
    /**
     * 开始时间
     */

    @ApiModelProperty(value = "开始时间")
    @Column(name = "start_time")
    private String  startTime;
    /**
     * 结束时间
     */

    @ApiModelProperty(value = "结束时间")
    @Column(name = "end_time")
    private String  endTime;



}
