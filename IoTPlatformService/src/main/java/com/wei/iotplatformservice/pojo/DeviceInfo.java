package com.wei.iotplatformservice.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DeviceInfo
 * 设备信息实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo {
    //    使用了自定义id生成器
    @TableId(type = IdType.ASSIGN_ID)
    private Long deviceId;
    private Long uid;
    private String deviceName;
    private String deviceToken;

    private String protocol;

    private Double version;

    private String description;

    //    注解优先级高于yml配置文件中的设置！
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    //    逻辑删除
    @TableLogic
    //    不展示逻辑删除位
    @JsonIgnore
    private Boolean isDelete;

}
