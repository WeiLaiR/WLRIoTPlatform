package com.wei.iotplatformservice.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * DeviceData
 * 硬件数据实体类（double类型）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDataNumber {
    //    使用了自定义id生成器
    @TableId(type = IdType.ASSIGN_ID)
    private Long deviceDataId;
    private Long deviceCfgId;
    private Double data;
    //    注解优先级高于yml配置文件中的设置！
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
