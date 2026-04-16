/*
 Navicat Premium Dump SQL

 Source Server         : 100.110.111.106_2345
 Source Server Type    : PostgreSQL
 Source Server Version : 180003 (180003)
 Source Host           : 100.110.111.106:2345
 Source Catalog        : TYPICAL_APPLICATION_RESOURCE_GUARANTEE
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 180003 (180003)
 File Encoding         : 65001

 Date: 16/04/2026 11:30:49
*/


-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_blob_triggers";
CREATE TABLE "public"."qrtz_blob_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "blob_data" bytea
)
;
COMMENT ON COLUMN "public"."qrtz_blob_triggers"."sched_name" IS '调度名称';
COMMENT ON COLUMN "public"."qrtz_blob_triggers"."trigger_name" IS '触发器名称（QRTZ_TRIGGERS）';
COMMENT ON COLUMN "public"."qrtz_blob_triggers"."trigger_group" IS '触发器所属组名称（QRTZ_TRIGGERS）';
COMMENT ON COLUMN "public"."qrtz_blob_triggers"."blob_data" IS '持久化Trigger对象';
COMMENT ON TABLE "public"."qrtz_blob_triggers" IS '触发器';

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_calendars";
CREATE TABLE "public"."qrtz_calendars" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "calendar_name" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "calendar" bytea NOT NULL
)
;
COMMENT ON COLUMN "public"."qrtz_calendars"."sched_name" IS '调度名称';
COMMENT ON COLUMN "public"."qrtz_calendars"."calendar_name" IS '日历名称';
COMMENT ON COLUMN "public"."qrtz_calendars"."calendar" IS '持久化Calendar对象';
COMMENT ON TABLE "public"."qrtz_calendars" IS '日历信息';

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_cron_triggers";
CREATE TABLE "public"."qrtz_cron_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "cron_expression" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "time_zone_id" varchar(80) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."qrtz_cron_triggers"."sched_name" IS '调度名称';
COMMENT ON COLUMN "public"."qrtz_cron_triggers"."trigger_name" IS '触发器名称（QRTZ_TRIGGERS）';
COMMENT ON COLUMN "public"."qrtz_cron_triggers"."trigger_group" IS '触发器所属组名称（QRTZ_TRIGGERS）';
COMMENT ON COLUMN "public"."qrtz_cron_triggers"."cron_expression" IS 'CRON 表达式';
COMMENT ON COLUMN "public"."qrtz_cron_triggers"."time_zone_id" IS '时区';
COMMENT ON TABLE "public"."qrtz_cron_triggers" IS 'CRON 类型的触发器';

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_fired_triggers";
CREATE TABLE "public"."qrtz_fired_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "entry_id" varchar(95) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "instance_name" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "fired_time" int8 NOT NULL,
  "sched_time" int8 NOT NULL,
  "priority" int4 NOT NULL,
  "state" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(190) COLLATE "pg_catalog"."default",
  "job_group" varchar(190) COLLATE "pg_catalog"."default",
  "is_nonconcurrent" varchar(1) COLLATE "pg_catalog"."default",
  "requests_recovery" varchar(1) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."qrtz_fired_triggers"."sched_name" IS '调度名称';
COMMENT ON COLUMN "public"."qrtz_fired_triggers"."entry_id" IS '调度器实例ID';
COMMENT ON COLUMN "public"."qrtz_fired_triggers"."trigger_name" IS '触发器名称（QRTZ_TRIGGERS）';
COMMENT ON COLUMN "public"."qrtz_fired_triggers"."trigger_group" IS '触发器所属组名称（QRTZ_TRIGGERS）';
COMMENT ON COLUMN "public"."qrtz_fired_triggers"."instance_name" IS '调度器实例名';
COMMENT ON COLUMN "public"."qrtz_fired_triggers"."fired_time" IS '触发时间';
COMMENT ON COLUMN "public"."qrtz_fired_triggers"."sched_time" IS '定时器制定时间';
COMMENT ON COLUMN "public"."qrtz_fired_triggers"."priority" IS '优先级';
COMMENT ON COLUMN "public"."qrtz_fired_triggers"."state" IS '状态';
COMMENT ON COLUMN "public"."qrtz_fired_triggers"."job_name" IS '集群中 Job 名称';
COMMENT ON COLUMN "public"."qrtz_fired_triggers"."job_group" IS '集群中 Job 所属组名称';
COMMENT ON COLUMN "public"."qrtz_fired_triggers"."is_nonconcurrent" IS '是否并发';
COMMENT ON COLUMN "public"."qrtz_fired_triggers"."requests_recovery" IS '是否接受恢复执行（默认为 false，设置了 RequestsRecovery 为 true，则会被重新执行）';
COMMENT ON TABLE "public"."qrtz_fired_triggers" IS '已触发的触发器';

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_job_details";
CREATE TABLE "public"."qrtz_job_details" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "job_group" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(250) COLLATE "pg_catalog"."default",
  "job_class_name" varchar(250) COLLATE "pg_catalog"."default" NOT NULL,
  "is_durable" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "is_nonconcurrent" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "is_update_data" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "requests_recovery" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "job_data" bytea
)
;
COMMENT ON COLUMN "public"."qrtz_job_details"."sched_name" IS '调度名称';
COMMENT ON COLUMN "public"."qrtz_job_details"."job_name" IS '集群中 Job 名称';
COMMENT ON COLUMN "public"."qrtz_job_details"."job_group" IS '集群中 Job 所属组名称';
COMMENT ON COLUMN "public"."qrtz_job_details"."description" IS '详细描述信息';
COMMENT ON COLUMN "public"."qrtz_job_details"."job_class_name" IS '集群中 Job 实现类全名';
COMMENT ON COLUMN "public"."qrtz_job_details"."is_durable" IS '是否持久化';
COMMENT ON COLUMN "public"."qrtz_job_details"."is_nonconcurrent" IS '是否并发执行';
COMMENT ON COLUMN "public"."qrtz_job_details"."is_update_data" IS '是否更新数据';
COMMENT ON COLUMN "public"."qrtz_job_details"."requests_recovery" IS '是否接受恢复执行（默认为 false，设置了 RequestsRecovery 为 true，则会被重新执行）';
COMMENT ON COLUMN "public"."qrtz_job_details"."job_data" IS '持久化 Job 对象';
COMMENT ON TABLE "public"."qrtz_job_details" IS 'JobDetail 信息';

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_locks";
CREATE TABLE "public"."qrtz_locks" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "lock_name" varchar(40) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."qrtz_locks"."sched_name" IS '调度名称';
COMMENT ON COLUMN "public"."qrtz_locks"."lock_name" IS '悲观锁名称';
COMMENT ON TABLE "public"."qrtz_locks" IS '悲观锁信息';

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO "public"."qrtz_locks" VALUES ('schedulerName', 'STATE_ACCESS');
INSERT INTO "public"."qrtz_locks" VALUES ('schedulerName', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_paused_trigger_grps";
CREATE TABLE "public"."qrtz_paused_trigger_grps" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(190) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."qrtz_paused_trigger_grps"."sched_name" IS '调度名称';
COMMENT ON COLUMN "public"."qrtz_paused_trigger_grps"."trigger_group" IS '触发器所属组名称（QRTZ_TRIGGERS）';
COMMENT ON TABLE "public"."qrtz_paused_trigger_grps" IS '已暂停的触发器';

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_scheduler_state";
CREATE TABLE "public"."qrtz_scheduler_state" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "instance_name" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "last_checkin_time" int8 NOT NULL,
  "checkin_interval" int8 NOT NULL
)
;
COMMENT ON COLUMN "public"."qrtz_scheduler_state"."sched_name" IS '调度名称';
COMMENT ON COLUMN "public"."qrtz_scheduler_state"."instance_name" IS '调度实例ID';
COMMENT ON COLUMN "public"."qrtz_scheduler_state"."last_checkin_time" IS '上次检查时间';
COMMENT ON COLUMN "public"."qrtz_scheduler_state"."checkin_interval" IS '检查间隔时间';
COMMENT ON TABLE "public"."qrtz_scheduler_state" IS '调度器状态';

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO "public"."qrtz_scheduler_state" VALUES ('schedulerName', 'SanJin1776309523272', 1776309636677, 15000);

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_simple_triggers";
CREATE TABLE "public"."qrtz_simple_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "repeat_count" int8 NOT NULL,
  "repeat_interval" int8 NOT NULL,
  "times_triggered" int8 NOT NULL
)
;
COMMENT ON COLUMN "public"."qrtz_simple_triggers"."sched_name" IS '调度名称';
COMMENT ON COLUMN "public"."qrtz_simple_triggers"."trigger_name" IS '触发器名称（QRTZ_TRIGGERS）';
COMMENT ON COLUMN "public"."qrtz_simple_triggers"."trigger_group" IS '触发器所属组名称（QRTZ_TRIGGERS）';
COMMENT ON COLUMN "public"."qrtz_simple_triggers"."repeat_count" IS '重复的次数统计';
COMMENT ON COLUMN "public"."qrtz_simple_triggers"."repeat_interval" IS '重复的间隔时间';
COMMENT ON COLUMN "public"."qrtz_simple_triggers"."times_triggered" IS '已经触发的次数';
COMMENT ON TABLE "public"."qrtz_simple_triggers" IS '简单的触发器信息';

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_simprop_triggers";
CREATE TABLE "public"."qrtz_simprop_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "str_prop_1" varchar(512) COLLATE "pg_catalog"."default",
  "str_prop_2" varchar(512) COLLATE "pg_catalog"."default",
  "str_prop_3" varchar(512) COLLATE "pg_catalog"."default",
  "int_prop_1" int4,
  "int_prop_2" int4,
  "long_prop_1" int8,
  "long_prop_2" int8,
  "dec_prop_1" numeric(13,4),
  "dec_prop_2" numeric(13,4),
  "bool_prop_1" varchar(1) COLLATE "pg_catalog"."default",
  "bool_prop_2" varchar(1) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."sched_name" IS '调度名称';
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."trigger_name" IS '触发器名称（QRTZ_TRIGGERS）';
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."trigger_group" IS '触发器所属组名称（QRTZ_TRIGGERS）';
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."str_prop_1" IS 'String类型的Trigger的第一个参数';
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."str_prop_2" IS 'String类型的Trigger的第二个参数';
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."str_prop_3" IS 'String类型的Trigger的第三个参数';
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."int_prop_1" IS 'int类型的Trigger的第一个参数';
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."int_prop_2" IS 'int类型的Trigger的第二个参数';
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."long_prop_1" IS 'Long类型的Trigger的第一个参数';
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."long_prop_2" IS 'Long类型的Trigger的第二个参数';
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."dec_prop_1" IS 'Decimal类型的Trigger的第一个参数';
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."dec_prop_2" IS 'Decimal类型的Trigger的第二个参数';
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."bool_prop_1" IS 'Boolean类型的Trigger的第一个参数';
COMMENT ON COLUMN "public"."qrtz_simprop_triggers"."bool_prop_2" IS 'Boolean类型的Trigger的第二个参数';
COMMENT ON TABLE "public"."qrtz_simprop_triggers" IS '存储 CalendarIntervalTrigger 和 DailyTimeIntervalTrigger 类型的触发器';

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS "public"."qrtz_triggers";
CREATE TABLE "public"."qrtz_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "job_group" varchar(190) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(250) COLLATE "pg_catalog"."default",
  "next_fire_time" int8,
  "prev_fire_time" int8,
  "priority" int4,
  "trigger_state" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_type" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "start_time" int8 NOT NULL,
  "end_time" int8,
  "calendar_name" varchar(190) COLLATE "pg_catalog"."default",
  "misfire_instr" int2,
  "job_data" bytea
)
;
COMMENT ON COLUMN "public"."qrtz_triggers"."sched_name" IS '调度名称';
COMMENT ON COLUMN "public"."qrtz_triggers"."trigger_name" IS '触发器名称';
COMMENT ON COLUMN "public"."qrtz_triggers"."trigger_group" IS '触发器所属组名称';
COMMENT ON COLUMN "public"."qrtz_triggers"."job_name" IS '集群中 Job 名称（QRTZ_JOB_DETAILS）';
COMMENT ON COLUMN "public"."qrtz_triggers"."job_group" IS '集群中 Job 所属组名称（QRTZ_JOB_DETAILS）';
COMMENT ON COLUMN "public"."qrtz_triggers"."description" IS '详细描述信息';
COMMENT ON COLUMN "public"."qrtz_triggers"."next_fire_time" IS '下一次触发时间（毫秒），默认为-1，意味不会自动触发';
COMMENT ON COLUMN "public"."qrtz_triggers"."prev_fire_time" IS '上一次触发时间（毫秒）';
COMMENT ON COLUMN "public"."qrtz_triggers"."priority" IS '优先级';
COMMENT ON COLUMN "public"."qrtz_triggers"."trigger_state" IS '当前触发器状态（ WAITING：等待； PAUSED：暂停； ACQUIRED：正常执行； BLOCKED：阻塞； ERROR：错误；）';
COMMENT ON COLUMN "public"."qrtz_triggers"."trigger_type" IS '触发器的类型，使用CRON表达式';
COMMENT ON COLUMN "public"."qrtz_triggers"."start_time" IS '开始时间';
COMMENT ON COLUMN "public"."qrtz_triggers"."end_time" IS '结束时间';
COMMENT ON COLUMN "public"."qrtz_triggers"."calendar_name" IS '日程表名称（QRTZ_CALENDARS）';
COMMENT ON COLUMN "public"."qrtz_triggers"."misfire_instr" IS '措施或者是补偿执行策略';
COMMENT ON COLUMN "public"."qrtz_triggers"."job_data" IS '持久化 Job 对象';
COMMENT ON TABLE "public"."qrtz_triggers" IS '触发器基本信息';

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict_item_dm
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict_item_dm";
CREATE TABLE "public"."sys_dict_item_dm" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" varchar(100) COLLATE "pg_catalog"."default",
  "sort_no" float8,
  "item_label" varchar(255) COLLATE "pg_catalog"."default",
  "item_value" varchar(255) COLLATE "pg_catalog"."default",
  "item_description" varchar(255) COLLATE "pg_catalog"."default",
  "creator_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "updater_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6) NOT NULL,
  "item_expand" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_dict_item_dm"."id" IS 'ID';
COMMENT ON COLUMN "public"."sys_dict_item_dm"."parent_id" IS '父级ID';
COMMENT ON COLUMN "public"."sys_dict_item_dm"."sort_no" IS '序号';
COMMENT ON COLUMN "public"."sys_dict_item_dm"."item_label" IS '名称';
COMMENT ON COLUMN "public"."sys_dict_item_dm"."item_value" IS '数值';
COMMENT ON COLUMN "public"."sys_dict_item_dm"."item_description" IS '描述';
COMMENT ON COLUMN "public"."sys_dict_item_dm"."creator_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_dict_item_dm"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_dict_item_dm"."updater_by" IS '更新人';
COMMENT ON COLUMN "public"."sys_dict_item_dm"."update_time" IS '更新时间（乐观锁）';
COMMENT ON COLUMN "public"."sys_dict_item_dm"."item_expand" IS '扩展字段';
COMMENT ON TABLE "public"."sys_dict_item_dm" IS '地名内码';

-- ----------------------------
-- Records of sys_dict_item_dm
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict_item_templet
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict_item_templet";
CREATE TABLE "public"."sys_dict_item_templet" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" varchar(100) COLLATE "pg_catalog"."default",
  "sort_no" float8,
  "item_label" varchar(255) COLLATE "pg_catalog"."default",
  "item_value" varchar(255) COLLATE "pg_catalog"."default",
  "item_description" varchar(255) COLLATE "pg_catalog"."default",
  "creator_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "updater_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6) NOT NULL,
  "item_expand" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_dict_item_templet"."id" IS 'ID';
COMMENT ON COLUMN "public"."sys_dict_item_templet"."parent_id" IS '父级ID';
COMMENT ON COLUMN "public"."sys_dict_item_templet"."sort_no" IS '序号';
COMMENT ON COLUMN "public"."sys_dict_item_templet"."item_label" IS '名称';
COMMENT ON COLUMN "public"."sys_dict_item_templet"."item_value" IS '数值';
COMMENT ON COLUMN "public"."sys_dict_item_templet"."item_description" IS '描述';
COMMENT ON COLUMN "public"."sys_dict_item_templet"."creator_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_dict_item_templet"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_dict_item_templet"."updater_by" IS '更新人';
COMMENT ON COLUMN "public"."sys_dict_item_templet"."update_time" IS '更新时间（乐观锁）';
COMMENT ON COLUMN "public"."sys_dict_item_templet"."item_expand" IS '扩展字段';
COMMENT ON TABLE "public"."sys_dict_item_templet" IS '数据字典模版表';

-- ----------------------------
-- Records of sys_dict_item_templet
-- ----------------------------

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_job";
CREATE TABLE "public"."sys_job" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int2 NOT NULL,
  "handler_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "handler_param" varchar(255) COLLATE "pg_catalog"."default",
  "cron_expression" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "retry_count" int4 NOT NULL,
  "retry_interval" int4 NOT NULL,
  "monitor_timeout" int4 NOT NULL,
  "creator_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "updater_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6) NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_job"."id" IS 'ID';
COMMENT ON COLUMN "public"."sys_job"."name" IS '任务名称';
COMMENT ON COLUMN "public"."sys_job"."status" IS '任务状态';
COMMENT ON COLUMN "public"."sys_job"."handler_name" IS '处理器名字';
COMMENT ON COLUMN "public"."sys_job"."handler_param" IS '处理器参数';
COMMENT ON COLUMN "public"."sys_job"."cron_expression" IS 'CRON 表达式';
COMMENT ON COLUMN "public"."sys_job"."retry_count" IS '重试次数';
COMMENT ON COLUMN "public"."sys_job"."retry_interval" IS '重试间隔';
COMMENT ON COLUMN "public"."sys_job"."monitor_timeout" IS '监控超时时间';
COMMENT ON COLUMN "public"."sys_job"."creator_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_job"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_job"."updater_by" IS '更新人';
COMMENT ON COLUMN "public"."sys_job"."update_time" IS '更新时间（乐观锁）';
COMMENT ON TABLE "public"."sys_job" IS '定时任务';

-- ----------------------------
-- Records of sys_job
-- ----------------------------

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_job_log";
CREATE TABLE "public"."sys_job_log" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "job_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "handler_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "handler_param" varchar(255) COLLATE "pg_catalog"."default",
  "execute_index" int4 NOT NULL,
  "start_time" timestamp(6) NOT NULL,
  "end_time" timestamp(6),
  "duration" int4,
  "status" int4 NOT NULL,
  "result" varchar(4000) COLLATE "pg_catalog"."default",
  "creator_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "updater_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6) NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_job_log"."id" IS 'ID';
COMMENT ON COLUMN "public"."sys_job_log"."job_id" IS '任务编号';
COMMENT ON COLUMN "public"."sys_job_log"."handler_name" IS '处理器的名字';
COMMENT ON COLUMN "public"."sys_job_log"."handler_param" IS '处理器的参数';
COMMENT ON COLUMN "public"."sys_job_log"."execute_index" IS '第几次执行';
COMMENT ON COLUMN "public"."sys_job_log"."start_time" IS '开始执行时间';
COMMENT ON COLUMN "public"."sys_job_log"."end_time" IS '结束执行时间';
COMMENT ON COLUMN "public"."sys_job_log"."duration" IS '执行时长';
COMMENT ON COLUMN "public"."sys_job_log"."status" IS '任务状态';
COMMENT ON COLUMN "public"."sys_job_log"."result" IS '结果数据';
COMMENT ON COLUMN "public"."sys_job_log"."creator_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_job_log"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_job_log"."updater_by" IS '更新人';
COMMENT ON COLUMN "public"."sys_job_log"."update_time" IS '更新时间（乐观锁）';
COMMENT ON TABLE "public"."sys_job_log" IS '定时任务执行日志';

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_log";
CREATE TABLE "public"."sys_log" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "token" varchar(300) COLLATE "pg_catalog"."default",
  "method" varchar(100) COLLATE "pg_catalog"."default",
  "url" varchar(255) COLLATE "pg_catalog"."default",
  "parameters" text COLLATE "pg_catalog"."default",
  "user_ip" varchar(50) COLLATE "pg_catalog"."default",
  "header" text COLLATE "pg_catalog"."default",
  "start_time" timestamp(6),
  "end_time" timestamp(6),
  "duration" int4,
  "code" int4,
  "message" text COLLATE "pg_catalog"."default",
  "creator_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "updater_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6) NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_log"."id" IS 'ID';
COMMENT ON COLUMN "public"."sys_log"."token" IS 'Token';
COMMENT ON COLUMN "public"."sys_log"."method" IS '请求方法名';
COMMENT ON COLUMN "public"."sys_log"."url" IS '访问地址';
COMMENT ON COLUMN "public"."sys_log"."parameters" IS '请求参数';
COMMENT ON COLUMN "public"."sys_log"."user_ip" IS '用户 IP';
COMMENT ON COLUMN "public"."sys_log"."header" IS '请求头';
COMMENT ON COLUMN "public"."sys_log"."start_time" IS '请求开始时间';
COMMENT ON COLUMN "public"."sys_log"."end_time" IS '请求结束时间';
COMMENT ON COLUMN "public"."sys_log"."duration" IS '执行时长，单位：毫秒';
COMMENT ON COLUMN "public"."sys_log"."code" IS '错误码';
COMMENT ON COLUMN "public"."sys_log"."message" IS '结果提示';
COMMENT ON COLUMN "public"."sys_log"."creator_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_log"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_log"."updater_by" IS '更新人';
COMMENT ON COLUMN "public"."sys_log"."update_time" IS '更新时间（乐观锁）';
COMMENT ON TABLE "public"."sys_log" IS '系统日志';

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_organization";
CREATE TABLE "public"."sys_organization" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" varchar(100) COLLATE "pg_catalog"."default",
  "sort_no" float8,
  "bdfh" varchar(255) COLLATE "pg_catalog"."default",
  "bdjc" varchar(255) COLLATE "pg_catalog"."default",
  "zqnm" varchar(10) COLLATE "pg_catalog"."default",
  "bznm" varchar(10) COLLATE "pg_catalog"."default",
  "bdlx" varchar(10) COLLATE "pg_catalog"."default",
  "bdjb" varchar(10) COLLATE "pg_catalog"."default",
  "zbjd" numeric(11,8),
  "zbwd" numeric(10,8),
  "zbhb" numeric(10,2),
  "dmnm" varchar(255) COLLATE "pg_catalog"."default",
  "xxdz" varchar(255) COLLATE "pg_catalog"."default",
  "creator_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "updater_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6) NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_organization"."id" IS 'ID';
COMMENT ON COLUMN "public"."sys_organization"."parent_id" IS '父级ID';
COMMENT ON COLUMN "public"."sys_organization"."sort_no" IS '序号';
COMMENT ON COLUMN "public"."sys_organization"."bdfh" IS '番号';
COMMENT ON COLUMN "public"."sys_organization"."bdjc" IS '简称';
COMMENT ON COLUMN "public"."sys_organization"."zqnm" IS '战区内码';
COMMENT ON COLUMN "public"."sys_organization"."bznm" IS '兵种内码';
COMMENT ON COLUMN "public"."sys_organization"."bdlx" IS '类型内码';
COMMENT ON COLUMN "public"."sys_organization"."bdjb" IS '级别内码';
COMMENT ON COLUMN "public"."sys_organization"."zbjd" IS '坐标经度';
COMMENT ON COLUMN "public"."sys_organization"."zbwd" IS '坐标纬度';
COMMENT ON COLUMN "public"."sys_organization"."zbhb" IS '坐标海拔';
COMMENT ON COLUMN "public"."sys_organization"."dmnm" IS '地名内码';
COMMENT ON COLUMN "public"."sys_organization"."xxdz" IS '详细地址';
COMMENT ON COLUMN "public"."sys_organization"."creator_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_organization"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_organization"."updater_by" IS '更新人';
COMMENT ON COLUMN "public"."sys_organization"."update_time" IS '更新时间（乐观锁）';
COMMENT ON TABLE "public"."sys_organization" IS '组织机构';

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO "public"."sys_organization" VALUES ('2034471800844115969', '0', 0, '996', '996', '996', '996', '996', '996', 1.00000000, 2.00000000, 3.00, '996', '996', 'admin', '2026-03-19 11:27:30.65362', NULL, '2026-03-19 11:27:30.65412');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_permission";
CREATE TABLE "public"."sys_permission" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" varchar(100) COLLATE "pg_catalog"."default",
  "sort_no" float8,
  "creator_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "updater_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6) NOT NULL,
  "mc" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "lx" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "uri" varchar(255) COLLATE "pg_catalog"."default",
  "tb" varchar(255) COLLATE "pg_catalog"."default",
  "ms" varchar(255) COLLATE "pg_catalog"."default",
  "ljsc" int2
)
;
COMMENT ON COLUMN "public"."sys_permission"."id" IS 'ID';
COMMENT ON COLUMN "public"."sys_permission"."parent_id" IS '父级ID';
COMMENT ON COLUMN "public"."sys_permission"."sort_no" IS '序号';
COMMENT ON COLUMN "public"."sys_permission"."creator_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_permission"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_permission"."updater_by" IS '更新人';
COMMENT ON COLUMN "public"."sys_permission"."update_time" IS '更新时间（乐观锁）';
COMMENT ON COLUMN "public"."sys_permission"."mc" IS '名称';
COMMENT ON COLUMN "public"."sys_permission"."lx" IS '类型标识';
COMMENT ON COLUMN "public"."sys_permission"."uri" IS 'URL（标识）';
COMMENT ON COLUMN "public"."sys_permission"."tb" IS '图标';
COMMENT ON COLUMN "public"."sys_permission"."ms" IS '描述';
COMMENT ON COLUMN "public"."sys_permission"."ljsc" IS '删除状态（0-正常，1-已删除）';
COMMENT ON TABLE "public"."sys_permission" IS '菜单权限';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role";
CREATE TABLE "public"."sys_role" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "sort_no" float8,
  "creator_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "updater_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6) NOT NULL,
  "mc" varchar(255) COLLATE "pg_catalog"."default",
  "ms" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_role"."id" IS 'ID';
COMMENT ON COLUMN "public"."sys_role"."sort_no" IS '序号';
COMMENT ON COLUMN "public"."sys_role"."creator_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_role"."updater_by" IS '更新人';
COMMENT ON COLUMN "public"."sys_role"."update_time" IS '更新时间（乐观锁）';
COMMENT ON COLUMN "public"."sys_role"."mc" IS '名称';
COMMENT ON COLUMN "public"."sys_role"."ms" IS '描述';
COMMENT ON TABLE "public"."sys_role" IS '系统角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_permission";
CREATE TABLE "public"."sys_role_permission" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "creator_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "updater_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6) NOT NULL,
  "js" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "cdqx" varchar(100) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_role_permission"."id" IS 'ID';
COMMENT ON COLUMN "public"."sys_role_permission"."creator_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_role_permission"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_role_permission"."updater_by" IS '更新人';
COMMENT ON COLUMN "public"."sys_role_permission"."update_time" IS '更新时间（乐观锁）';
COMMENT ON COLUMN "public"."sys_role_permission"."js" IS '系统角色标识';
COMMENT ON COLUMN "public"."sys_role_permission"."cdqx" IS '菜单权限标识';
COMMENT ON TABLE "public"."sys_role_permission" IS '角色权限关系';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user";
CREATE TABLE "public"."sys_user" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "real_name" varchar(255) COLLATE "pg_catalog"."default",
  "identity_number" varchar(20) COLLATE "pg_catalog"."default",
  "organization_id" varchar(100) COLLATE "pg_catalog"."default",
  "delete_flag" int2 NOT NULL,
  "creator_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "updater_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6) NOT NULL,
  "organization_node" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_user"."id" IS 'ID';
COMMENT ON COLUMN "public"."sys_user"."username" IS '登录账号';
COMMENT ON COLUMN "public"."sys_user"."password" IS '登录密码';
COMMENT ON COLUMN "public"."sys_user"."real_name" IS '真实姓名';
COMMENT ON COLUMN "public"."sys_user"."identity_number" IS '身份证号';
COMMENT ON COLUMN "public"."sys_user"."organization_id" IS '单位标识';
COMMENT ON COLUMN "public"."sys_user"."delete_flag" IS '删除状态（0-正常，1-已删除）';
COMMENT ON COLUMN "public"."sys_user"."creator_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_user"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_user"."updater_by" IS '更新人';
COMMENT ON COLUMN "public"."sys_user"."update_time" IS '更新时间（乐观锁）';
COMMENT ON COLUMN "public"."sys_user"."organization_node" IS '单位节点';
COMMENT ON TABLE "public"."sys_user" IS '系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO "public"."sys_user" VALUES ('1', 'admin', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', '管理员', NULL, NULL, 0, '1', '2026-01-12 14:18:40', '1', '2026-01-12 14:18:44', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_role";
CREATE TABLE "public"."sys_user_role" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "creator_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "updater_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6) NOT NULL,
  "yh" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "js" varchar(100) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "public"."sys_user_role"."id" IS 'ID';
COMMENT ON COLUMN "public"."sys_user_role"."creator_by" IS '创建人';
COMMENT ON COLUMN "public"."sys_user_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_user_role"."updater_by" IS '更新人';
COMMENT ON COLUMN "public"."sys_user_role"."update_time" IS '更新时间（乐观锁）';
COMMENT ON COLUMN "public"."sys_user_role"."yh" IS '用户标识';
COMMENT ON COLUMN "public"."sys_user_role"."js" IS '角色标识';
COMMENT ON TABLE "public"."sys_user_role" IS '用户角色关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for templet
-- ----------------------------
DROP TABLE IF EXISTS "public"."templet";
CREATE TABLE "public"."templet" (
  "id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" varchar(100) COLLATE "pg_catalog"."default",
  "sort_no" float8,
  "creator_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "updater_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6) NOT NULL
)
;
COMMENT ON COLUMN "public"."templet"."id" IS 'ID';
COMMENT ON COLUMN "public"."templet"."parent_id" IS '父级ID';
COMMENT ON COLUMN "public"."templet"."sort_no" IS '序号';
COMMENT ON COLUMN "public"."templet"."creator_by" IS '创建人';
COMMENT ON COLUMN "public"."templet"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."templet"."updater_by" IS '更新人';
COMMENT ON COLUMN "public"."templet"."update_time" IS '更新时间（乐观锁）';
COMMENT ON TABLE "public"."templet" IS '模板表';

-- ----------------------------
-- Records of templet
-- ----------------------------

-- ----------------------------
-- Function structure for exec
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."exec"("sqlstring" varchar);
CREATE FUNCTION "public"."exec"("sqlstring" varchar)
  RETURNS "pg_catalog"."varchar" AS $BODY$
declare
res varchar(50);
BEGIN
EXECUTE sqlstring;
RETURN 'ok';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Indexes structure for table qrtz_blob_triggers
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555494" ON "public"."qrtz_blob_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "SCHED_NAME" ON "public"."qrtz_blob_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table qrtz_blob_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_blob_triggers" ADD CONSTRAINT "CONS134218800" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Indexes structure for table qrtz_calendars
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555496" ON "public"."qrtz_calendars" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "calendar_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table qrtz_calendars
-- ----------------------------
ALTER TABLE "public"."qrtz_calendars" ADD CONSTRAINT "CONS134218801" PRIMARY KEY ("sched_name", "calendar_name");

-- ----------------------------
-- Indexes structure for table qrtz_cron_triggers
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555497" ON "public"."qrtz_cron_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table qrtz_cron_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_cron_triggers" ADD CONSTRAINT "CONS134218802" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Indexes structure for table qrtz_fired_triggers
-- ----------------------------
CREATE INDEX "IDX_QRTZ_FT_INST_JOB_REQ_RCVRY" ON "public"."qrtz_fired_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "instance_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "requests_recovery" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_FT_JG" ON "public"."qrtz_fired_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "job_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_FT_J_G" ON "public"."qrtz_fired_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "job_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "job_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_FT_TG" ON "public"."qrtz_fired_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_FT_TRIG_INST_NAME" ON "public"."qrtz_fired_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "instance_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_FT_T_G" ON "public"."qrtz_fired_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "INDEX33555498" ON "public"."qrtz_fired_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "entry_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table qrtz_fired_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_fired_triggers" ADD CONSTRAINT "CONS134218803" PRIMARY KEY ("sched_name", "entry_id");

-- ----------------------------
-- Indexes structure for table qrtz_job_details
-- ----------------------------
CREATE INDEX "IDX_QRTZ_J_GRP" ON "public"."qrtz_job_details" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "job_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_J_REQ_RECOVERY" ON "public"."qrtz_job_details" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "requests_recovery" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "INDEX33555505" ON "public"."qrtz_job_details" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "job_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "job_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table qrtz_job_details
-- ----------------------------
ALTER TABLE "public"."qrtz_job_details" ADD CONSTRAINT "CONS134218804" PRIMARY KEY ("sched_name", "job_name", "job_group");

-- ----------------------------
-- Indexes structure for table qrtz_locks
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555508" ON "public"."qrtz_locks" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "lock_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table qrtz_locks
-- ----------------------------
ALTER TABLE "public"."qrtz_locks" ADD CONSTRAINT "CONS134218805" PRIMARY KEY ("sched_name", "lock_name");

-- ----------------------------
-- Indexes structure for table qrtz_paused_trigger_grps
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555509" ON "public"."qrtz_paused_trigger_grps" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table qrtz_paused_trigger_grps
-- ----------------------------
ALTER TABLE "public"."qrtz_paused_trigger_grps" ADD CONSTRAINT "CONS134218806" PRIMARY KEY ("sched_name", "trigger_group");

-- ----------------------------
-- Indexes structure for table qrtz_scheduler_state
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555510" ON "public"."qrtz_scheduler_state" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "instance_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table qrtz_scheduler_state
-- ----------------------------
ALTER TABLE "public"."qrtz_scheduler_state" ADD CONSTRAINT "CONS134218807" PRIMARY KEY ("sched_name", "instance_name");

-- ----------------------------
-- Indexes structure for table qrtz_simple_triggers
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555511" ON "public"."qrtz_simple_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table qrtz_simple_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_simple_triggers" ADD CONSTRAINT "CONS134218808" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Indexes structure for table qrtz_simprop_triggers
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555512" ON "public"."qrtz_simprop_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table qrtz_simprop_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_simprop_triggers" ADD CONSTRAINT "CONS134218809" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Indexes structure for table qrtz_triggers
-- ----------------------------
CREATE INDEX "IDX_QRTZ_T_C" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "calendar_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_T_G" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_T_J" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "job_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "job_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_T_JG" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "job_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_T_NEXT_FIRE_TIME" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "next_fire_time" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_T_NFT_MISFIRE" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "misfire_instr" "pg_catalog"."int2_ops" ASC NULLS LAST,
  "next_fire_time" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_T_NFT_ST" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_state" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "next_fire_time" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_T_NFT_ST_MISFIRE" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "misfire_instr" "pg_catalog"."int2_ops" ASC NULLS LAST,
  "next_fire_time" "pg_catalog"."int8_ops" ASC NULLS LAST,
  "trigger_state" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_T_NFT_ST_MISFIRE_GRP" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "misfire_instr" "pg_catalog"."int2_ops" ASC NULLS LAST,
  "next_fire_time" "pg_catalog"."int8_ops" ASC NULLS LAST,
  "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_state" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_T_N_G_STATE" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_state" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_T_N_STATE" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_state" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "IDX_QRTZ_T_STATE" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_state" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "INDEX33555513" ON "public"."qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "trigger_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table qrtz_triggers
-- ----------------------------
ALTER TABLE "public"."qrtz_triggers" ADD CONSTRAINT "CONS134218810" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Indexes structure for table sys_dict_item_dm
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555526" ON "public"."sys_dict_item_dm" USING btree (
  "id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_dict_item_dm
-- ----------------------------
ALTER TABLE "public"."sys_dict_item_dm" ADD CONSTRAINT "CONS134218811" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_dict_item_templet
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555527" ON "public"."sys_dict_item_templet" USING btree (
  "id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_dict_item_templet
-- ----------------------------
ALTER TABLE "public"."sys_dict_item_templet" ADD CONSTRAINT "CONS134218812" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_job
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555528" ON "public"."sys_job" USING btree (
  "id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_job
-- ----------------------------
ALTER TABLE "public"."sys_job" ADD CONSTRAINT "CONS134218813" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_job_log
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555529" ON "public"."sys_job_log" USING btree (
  "id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_job_log
-- ----------------------------
ALTER TABLE "public"."sys_job_log" ADD CONSTRAINT "CONS134218814" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_log
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555530" ON "public"."sys_log" USING btree (
  "id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_log
-- ----------------------------
ALTER TABLE "public"."sys_log" ADD CONSTRAINT "CONS134218815" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_organization
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555534" ON "public"."sys_organization" USING btree (
  "id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_organization
-- ----------------------------
ALTER TABLE "public"."sys_organization" ADD CONSTRAINT "CONS134218819" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_permission
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555531" ON "public"."sys_permission" USING btree (
  "id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_permission
-- ----------------------------
ALTER TABLE "public"."sys_permission" ADD CONSTRAINT "CONS134218816" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_role
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555532" ON "public"."sys_role" USING btree (
  "id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "CONS134218817" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_role_permission
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555533" ON "public"."sys_role_permission" USING btree (
  "id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_role_permission
-- ----------------------------
ALTER TABLE "public"."sys_role_permission" ADD CONSTRAINT "CONS134218818" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_user
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555535" ON "public"."sys_user" USING btree (
  "id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "CONS134218820" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_user_role
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555536" ON "public"."sys_user_role" USING btree (
  "id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_user_role
-- ----------------------------
ALTER TABLE "public"."sys_user_role" ADD CONSTRAINT "CONS134218821" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table templet
-- ----------------------------
CREATE UNIQUE INDEX "INDEX33555537" ON "public"."templet" USING btree (
  "id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table templet
-- ----------------------------
ALTER TABLE "public"."templet" ADD CONSTRAINT "CONS134218822" PRIMARY KEY ("id");
