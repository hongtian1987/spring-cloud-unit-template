-- -----------------------------------------------------------
-- ismp_template 为xxxxx所使用的数据库脚本
-- 数据库采用utf-8的字符集
-- -----------------------------------------------------------
##填入最新版本号
set @newversion = '1.0.0';

##判断数据库是否存在，不存在则创建数据库
create database if not exists ismp_template DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

##进入数据库
use ismp_template;

set names utf8mb4;

-- -----------------------------------------------------------
-- 版本由0.0.0升级到1.0.0的过程
-- -----------------------------------------------------------
##数据库从无(0.0.0)到（1.0.0）的存储过程创建
drop procedure if exists rs_proc_ver0tover1;
delimiter //
create procedure rs_proc_ver0tover1()
begin
declare indxcnt int;
declare tabexit int;
declare trigexit int;
declare colexit int;
SET FOREIGN_KEY_CHECKS=1;

-- ----------------------------
-- Table structure for db_version
-- ----------------------------
select count(table_name) into tabexit from information_schema.tables where table_schema='ismp_template' and table_name='db_version';
if tabexit <= 0 then
    CREATE TABLE `db_version` (
      `version` varchar(16) NOT NULL,
      `upgrade_time` datetime NOT NULL 
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
end if;

-- ----------------------------
-- Records of db_version
-- ----------------------------
DELETE FROM `db_version`;
INSERT INTO `db_version` VALUES ('0.0.0', now());

end;
//
delimiter ;

-- -----------------------------------------------------------
-- 升级的总控制存储过程
-- -----------------------------------------------------------
##通过数据库存数过程来进行数据库升级
drop procedure if exists rs_proc_upgrade;
delimiter //
create procedure rs_proc_upgrade(in newver varchar(10))
begin 
-- 获取数据库中原有版本信息
declare oldver varchar(10);
declare oldtmp varchar(10);
declare vcnt int;
declare vmsgcnt int;
set oldtmp = '0.0.0';

select count(*) into vcnt from information_schema.tables where table_schema='ismp_template' and table_name='db_version';
if vcnt > 0 then
    select count(*) into vmsgcnt from db_version;
    if vmsgcnt > 0 then
        select version into oldtmp from db_version limit 0,1;
    else 
        insert into db_version(version, upgrade_time) values('0.0.0', now());
    end if;
end if;

set oldver = oldtmp;

-- 比较新旧版本进行升级,如果当前版本低于升级版本
while strcmp(oldtmp, newver) = -1 do
begin
    if strcmp(oldtmp, '1.0.0') = -1 then
        call rs_proc_ver0tover1();
        update db_version set version = '1.0.0',upgrade_time = now() where version= oldtmp;
	set oldtmp = '1.0.0';
    end if;

end;
end while;


select oldver;
select newver;

end;
//
delimiter ;

##调用数据存数过程进行升级
call rs_proc_upgrade(@newversion);

##释放掉数据库函数
drop procedure rs_proc_upgrade;
drop procedure rs_proc_ver0tover1;

##提交整个操作
commit;