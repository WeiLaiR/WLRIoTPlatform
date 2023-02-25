package com.wei.iotplatformservice.handler;

import com.wei.iotplatformservice.client.UserRemindClient;
import com.wei.iotplatformservice.mapper.*;
import com.wei.iotplatformservice.pojo.*;
import com.wei.iotplatformservice.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DeviceMessageHandler {

    private static final Long TIMEOUT = 30 * 10L;

    private DeviceInfoMapper deviceInfoMapper;

    @Autowired
    public void setDeviceInfoMapper(DeviceInfoMapper deviceInfoMapper) {
        this.deviceInfoMapper = deviceInfoMapper;
    }

    private DeviceCfgMapper deviceCfgMapper;

    @Autowired
    public void setDeviceCfgMapper(DeviceCfgMapper deviceCfgMapper) {
        this.deviceCfgMapper = deviceCfgMapper;
    }

    private DeviceDataNumberMapper deviceDataNumberMapper;

    @Autowired
    public void setDeviceDataNumberMapper(DeviceDataNumberMapper deviceDataNumberMapper) {
        this.deviceDataNumberMapper = deviceDataNumberMapper;
    }

    private DeviceDataMapper deviceDataMapper;

    @Autowired
    public void setDeviceDataMapper(DeviceDataMapper deviceDataMapper) {
        this.deviceDataMapper = deviceDataMapper;
    }

    private DeviceRuleMapper deviceRuleMapper;

    @Autowired
    public void setDeviceRuleMapper(DeviceRuleMapper deviceRuleMapper) {
        this.deviceRuleMapper = deviceRuleMapper;
    }

    private ThreadPoolTaskExecutor customThreadPool1;

    @Autowired
    public void setCustomThreadPool1(ThreadPoolTaskExecutor customThreadPool1) {
        this.customThreadPool1 = customThreadPool1;
    }

    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    private UserRemindClient remindClient;

    @Autowired
    public void setRemindClient(UserRemindClient remindClient) {
        this.remindClient = remindClient;
    }

    @Transactional
    public void handleMessage(Map<String, Object> map, String protocol) {
        String token = (String) map.get("token");

        Long did = deviceInfoMapper.queryDeviceIdByToken(token);

        if (did == null) {
            return;
        }

        customThreadPool1.execute(() -> infoTask(map, did, protocol));

        customThreadPool1.execute(() -> dataTask(map, did, protocol));

        redisUtil.set(did + "status", "Y", TIMEOUT);
    }

    private void infoTask(Map<String, Object> map, Long did, String protocol) {
        DeviceInfo deviceInfo = deviceInfoMapper.selectById(did);
        if (deviceInfo == null) {
            return;
        }
        boolean sing = false;
        if (deviceInfo.getProtocol() == null) {
            deviceInfo.setProtocol(protocol);
            sing = true;
        } else {
            if (!deviceInfo.getProtocol().equals(protocol)) {
                deviceInfo.setProtocol(protocol);
                sing = true;
            }
        }

        String temp = (String) map.getOrDefault("version", null);

        if (temp != null) {
            double version = Double.parseDouble(temp);

            if (deviceInfo.getVersion() != version) {
                deviceInfo.setVersion(version);
                sing = true;
            }
        }


        if (sing) {
            deviceInfoMapper.updateById(deviceInfo);
        }
    }

    private void dataTask(Map<String, Object> map, Long did, String protocol) {

        List<Map<String, Object>> rMap = (List<Map<String, Object>>) redisUtil.get(did + "cfg");

        List<DeviceCfg> deviceCfg;

        if (rMap == null) {
            deviceCfg = deviceCfgMapper.queryDeviceCfgListP(0, 100, did);

            List<Map<String, Object>> maps = new ArrayList<>();
            for (DeviceCfg cfg : deviceCfg) {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("id", cfg.getDeviceCfgId());
                map1.put("nickName", cfg.getTypeNickName());
                map1.put("name", cfg.getTypeName());
                map1.put("isNumber", cfg.getIsNumber());
                maps.add(map1);
            }
            redisUtil.set(did + "cfg", maps, 1200);
        } else {
            deviceCfg = new ArrayList<>();
            for (Map<String, Object> map1 : rMap) {
                DeviceCfg cfg = new DeviceCfg();
                cfg.setDeviceCfgId((Long) map1.get("id"));
                cfg.setTypeName((String) map1.get("name"));
                cfg.setTypeNickName((String) map1.get("nickName"));
                cfg.setIsNumber((Boolean) map1.get("isNumber"));
                deviceCfg.add(cfg);
            }
        }


        String equipmentNo = (String) map.getOrDefault("equipment_no", null);
        if (equipmentNo == null) {
            equipmentNo = "NO.1";
        }

        for (DeviceCfg cfg : deviceCfg) {
            String s = (String) map.getOrDefault(cfg.getTypeName(), null);

            if (s != null) {
                if (cfg.getIsNumber()) {
                    double val = Double.parseDouble(s);
                    DeviceDataNumber data = new DeviceDataNumber();

                    data.setEquipmentNo(equipmentNo);
                    data.setData(val);
                    data.setDeviceCfgId(cfg.getDeviceCfgId());
                    data.setProtocol(protocol);
                    deviceDataNumberMapper.insert(data);

                    List<Map<String, Object>> ruleMap = (List<Map<String, Object>>) redisUtil.get(cfg.getDeviceCfgId() + "rule");

                    List<DeviceRule> deviceRules;

                    if (ruleMap == null) {
                        deviceRules = deviceRuleMapper.queryDeviceRuleListP(0, 100, cfg.getDeviceCfgId());
                        List<Map<String, Object>> maps = new ArrayList<>();
                        for (DeviceRule rule : deviceRules) {
                            Map<String, Object> map1 = new HashMap<>();
                            map1.put("id", rule.getDeviceRuleId());
                            map1.put("symbol", rule.getSymbol());
                            map1.put("value", rule.getValue());
                            map1.put("isMeet", rule.getIsMeet());
                            maps.add(map1);
                        }
                        redisUtil.set(cfg.getDeviceCfgId() + "rule", maps, 1200);
                    } else {
                        deviceRules = new ArrayList<>();
                        for (Map<String, Object> map1 : ruleMap) {
                            DeviceRule rule = new DeviceRule();
                            rule.setDeviceRuleId((Long) map1.get("id"));
                            rule.setSymbol((String) map1.get("symbol"));
                            rule.setValue((Double) map1.get("value"));
                            rule.setIsMeet((Boolean) map1.get("isMeet"));
                            deviceRules.add(rule);
                        }
                    }

                    for (DeviceRule rule : deviceRules) {
                        if (rule.getIsMeet()) {
                            if (rule.getSymbol().equals(">")) {
                                if (val > rule.getValue()) {
                                    taskWarn(did, val, cfg);
                                    break;
                                }
                            } else if (rule.getSymbol().equals("<")) {
                                if (val < rule.getValue()) {
                                    taskWarn(did, val, cfg);
                                    break;
                                }
                            } else if (rule.getSymbol().equals("==")) {
                                if (val == rule.getValue()) {
                                    taskWarn(did, val, cfg);
                                    break;
                                }
                            } else if (rule.getSymbol().equals(">=")) {
                                if (val >= rule.getValue()) {
                                    taskWarn(did, val, cfg);
                                    break;
                                }
                            } else if (rule.getSymbol().equals("<=")) {
                                if (val <= rule.getValue()) {
                                    taskWarn(did, val, cfg);
                                    break;
                                }
                            } else if (rule.getSymbol().equals("!=")) {
                                if (val != rule.getValue()) {
                                    taskWarn(did, val, cfg);
                                    break;
                                }
                            }
                        } else {
                            if (rule.getSymbol().equals(">")) {
                                if (val <= rule.getValue()) {
                                    taskWarn(did, val, cfg);
                                    break;
                                }
                            } else if (rule.getSymbol().equals("<")) {
                                if (val >= rule.getValue()) {
                                    taskWarn(did, val, cfg);
                                    break;
                                }
                            } else if (rule.getSymbol().equals("==")) {
                                if (val != rule.getValue()) {
                                    taskWarn(did, val, cfg);
                                    break;
                                }
                            } else if (rule.getSymbol().equals(">=")) {
                                if (val < rule.getValue()) {
                                    taskWarn(did, val, cfg);
                                    break;
                                }
                            } else if (rule.getSymbol().equals("<=")) {
                                if (val > rule.getValue()) {
                                    taskWarn(did, val, cfg);
                                    break;
                                }
                            } else if (rule.getSymbol().equals("!=")) {
                                if (val == rule.getValue()) {
                                    taskWarn(did, val, cfg);
                                    break;
                                }
                            }
                        }
                    }

                } else {
                    DeviceData data = new DeviceData();

                    data.setEquipmentNo(equipmentNo);
                    data.setData(s);
                    data.setDeviceCfgId(cfg.getDeviceCfgId());
                    data.setProtocol(protocol);
                    deviceDataMapper.insert(data);
                }
            }
        }
    }


    private void taskWarn(Long did, double value, DeviceCfg deviceCfg) {
        HashMap<String, Object> map = new HashMap<>();
        DeviceInfo deviceInfo = deviceInfoMapper.selectById(did);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("您的设备(集群) ").append(deviceInfo.getDeviceName()).append(" (").append(deviceInfo.getDeviceId()).append(")的 ").append(deviceCfg.getTypeNickName()).append(" 数据于 ").append(simpleDateFormat.format(new Date())).append(" 超出预警值，当前值为：").append(value).append(" ，请及时查看设备状态。");
        map.put("uid", deviceInfo.getUid());
        map.put("did", did);
        map.put("message", sb.toString());
        remindClient.insertRemind(map);
    }


}
