package com.aienuo.tea.job;

import com.aienuo.tea.common.handlers.JobHandler;
import com.aienuo.tea.model.dto.LogCleanDTO;
import com.aienuo.tea.service.ILogService;
import com.alibaba.druid.support.json.JSONParser;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 物理删除 N 天前日志 Job
 */
@Slf4j
@Component(value = "logClean")
@RequiredArgsConstructor
public class LogCleanJob implements JobHandler {

    /**
     * 系统日志 服务类
     */
    private final ILogService logService;

    /**
     * 清理超过（14）天的日志
     */
    private static final Integer RETAIN_DAY = 14;

    @Override
    public String execute(final String param) {
        LogCleanDTO clean = new LogCleanDTO();
        Integer code = 200;
        LocalDateTime createTime = LocalDateTime.now().minusDays(RETAIN_DAY);
        if (StringUtils.isNotEmpty(param)) {
            Map<String, Object> map = new JSONParser(param).parseMap();
            if (map.containsKey("id")) {
                clean.setId((String) map.get("id"));
            }
            if (map.containsKey("code")) {
                code = (Integer) map.get("code");
            }
            if (map.containsKey("creatorBy")) {
                clean.setCreatorBy((String) map.get("creatorBy"));
            }
            if (map.containsKey("createTime")) {
                createTime = (LocalDateTime) map.get("createTime");
            }
            if (map.containsKey("limit")) {
                clean.setLimit(Long.valueOf((Integer) map.get("limit")));
            }
        }
        clean.setCode(code);
        clean.setCreateTime(createTime);
        // 执行清理
        long count = this.logService.clean(clean);
        log.info("[execute][定时执行清理定时任务日志数量 ({}) 个]", count);
        return String.format("定时执行清理定时任务日志数量 %s 个", count);
    }

}
