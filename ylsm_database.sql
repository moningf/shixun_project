-- ============================================================
-- 教材征订与发放管理系统 - 数据库初始化脚本
-- 数据库: ylsm
-- 字符集: utf8mb4
-- ============================================================

-- 强制设置客户端/连接/结果字符集
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;
SET character_set_client     = utf8mb4;
SET character_set_connection = utf8mb4;
SET character_set_results    = utf8mb4;

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `ylsm` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `ylsm`;
ALTER DATABASE `ylsm` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ============================================================
-- 1. 管理员表
-- ============================================================
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `Mno`    varchar(20)  NOT NULL COMMENT '管理员编号',
  `Mname`  varchar(50)  DEFAULT NULL COMMENT '姓名',
  `Mtel`   varchar(20)  DEFAULT NULL COMMENT '电话',
  `Meamil` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`Mno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员';

INSERT INTO `admin` VALUES
('admin001', '张管理', '13800001111', 'admin@school.edu.cn', '123456');

-- ============================================================
-- 2. 课程表
-- ============================================================
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `CCno`   varchar(20) NOT NULL COMMENT '课程号',
  `CCname` varchar(100) DEFAULT NULL COMMENT '课程名',
  PRIMARY KEY (`CCno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程';

INSERT INTO `course` VALUES
('C001', '高等数学'),
('C002', '大学英语'),
('C003', '数据结构与算法'),
('C004', '操作系统原理'),
('C005', '计算机网络'),
('C006', '数据库原理'),
('C007', '软件工程'),
('C008', '编译原理');

-- ============================================================
-- 3. 教师表
-- ============================================================
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `Tno`      varchar(20)  NOT NULL COMMENT '教师编号',
  `Tname`    varchar(50)  DEFAULT NULL COMMENT '姓名',
  `Tdept`    varchar(100) DEFAULT NULL COMMENT '学院',
  `Ttel`     varchar(20)  DEFAULT NULL COMMENT '电话',
  `Temail`   varchar(100) DEFAULT NULL COMMENT '邮箱',
  `CCno`     varchar(20)  DEFAULT NULL COMMENT '所教课程号',
  `password` varchar(50)  DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`Tno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师';

INSERT INTO `teacher` VALUES
('T001', '李教授', '数学与统计学院', '13900001111', 'liprof@school.edu.cn', 'C001', '123456'),
('T002', '王老师', '外国语学院',     '13900002222', 'wangteach@school.edu.cn', 'C002', '123456'),
('T003', '陈教授', '计算机学院',     '13900003333', 'chenprof@school.edu.cn', 'C003', '123456'),
('T004', '刘老师', '计算机学院',     '13900004444', 'liuteach@school.edu.cn', 'C004', '123456'),
('T005', '赵教授', '计算机学院',     '13900005555', 'zhaoprof@school.edu.cn', 'C005', '123456'),
('T006', '周老师', '计算机学院',     '13900006666', 'zhouteach@school.edu.cn', 'C006', '123456');

-- ============================================================
-- 4. 班级表
-- ============================================================
DROP TABLE IF EXISTS `monitor`;
CREATE TABLE `monitor` (
  `Cno`      varchar(20)  NOT NULL COMMENT '班级编号',
  `Cgrade`   varchar(20)  DEFAULT NULL COMMENT '年级',
  `Cdept`    varchar(100) DEFAULT NULL COMMENT '学院',
  `Cmajor`   varchar(100) DEFAULT NULL COMMENT '专业',
  `Cnum`     varchar(10)  DEFAULT NULL COMMENT '班级人数',
  `password` varchar(50)  DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`Cno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级';

INSERT INTO `monitor` VALUES
('2021001001', '2021级', '计算机学院', '计算机科学与技术', '45', '123456'),
('2021002001', '2021级', '数学与统计学院', '数学与应用数学', '38', '123456'),
('2021003001', '2021级', '外国语学院', '英语', '32', '123456'),
('2022001001', '2022级', '计算机学院', '软件工程', '50', '123456'),
('2022002001', '2022级', '计算机学院', '大数据技术', '42', '123456');

-- ============================================================
-- 5. 教材表
-- ============================================================
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `Bno`      varchar(20)  NOT NULL COMMENT '书号',
  `Bname`    varchar(200) DEFAULT NULL COMMENT '书名',
  `Bauthor`  varchar(100) DEFAULT NULL COMMENT '作者',
  `Bsource`  varchar(100) DEFAULT NULL COMMENT '出版社',
  `Bedition` varchar(50)  DEFAULT NULL COMMENT '版次',
  `Bprice`   varchar(20)  DEFAULT NULL COMMENT '单价',
  `Bnum`     varchar(10)  DEFAULT '0' COMMENT '库存数量',
  `CCno`     varchar(20)  DEFAULT NULL COMMENT '课程号',
  `Tno`      varchar(20)  DEFAULT NULL COMMENT '教师编号',
  PRIMARY KEY (`Bno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教材';

INSERT INTO `book` VALUES
('978-7-04-050000-1', '高等数学（第七版）上册',     '同济大学数学系', '高等教育出版社', '第7版', '46.50', '80',  'C001', 'T001'),
('978-7-04-050000-2', '高等数学（第七版）下册',     '同济大学数学系', '高等教育出版社', '第7版', '39.80', '75',  'C001', 'T001'),
('978-7-5600-5000-1', '新视野大学英语（第三版）',   '郑树棠',       '外语教学与研究出版社', '第3版', '58.00', '100', 'C002', 'T002'),
('978-7-302-50000-1', '数据结构（C语言版）',        '严蔚敏',       '清华大学出版社',       '第1版', '35.00', '60',  'C003', 'T003'),
('978-7-111-50000-1', '计算机操作系统（第四版）',   '汤小丹',       '西安电子科技大学出版社', '第4版', '45.00', '55',  'C004', 'T004'),
('978-7-121-50000-1', '计算机网络（第八版）',       '谢希仁',       '电子工业出版社',       '第8版', '49.00', '70',  'C005', 'T005'),
('978-7-04-050000-3', '数据库系统概论（第五版）',   '王珊',         '高等教育出版社',       '第5版', '39.90', '65',  'C006', 'T006'),
('978-7-111-50000-2', '软件工程导论（第六版）',     '张海藩',       '清华大学出版社',       '第6版', '42.00', '50',  'C007', 'T003'),
('978-7-111-50000-3', '编译原理（第三版）',         '陈火旺',       '国防工业出版社',       '第3版', '55.00', '40',  'C008', 'T004');

-- ============================================================
-- 6. 教材预订表（订单）
-- ============================================================
DROP TABLE IF EXISTS `reserve`;
CREATE TABLE `reserve` (
  `Rno`        varchar(20)   NOT NULL COMMENT '订单号',
  `Cno`        varchar(20)   DEFAULT NULL COMMENT '班级编号',
  `Bno`        varchar(20)   DEFAULT NULL COMMENT '书号',
  `Rnum`       int(11)       DEFAULT NULL COMMENT '预订数量',
  `totalPrice` decimal(10,2) DEFAULT NULL COMMENT '总价',
  `R_time`     varchar(20)   DEFAULT NULL COMMENT '预订日期',
  `Q_time`     varchar(20)   DEFAULT NULL COMMENT '发放日期',
  `Bstatus`    varchar(20)   DEFAULT '未发放' COMMENT '状态: 未发放/已处理',
  `Place`      varchar(200)  DEFAULT NULL COMMENT '发放地点',
  PRIMARY KEY (`Rno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教材预订';

INSERT INTO `reserve` VALUES
('1', '2021001001', '978-7-302-50000-1', 45, 1575.00, '2026-01-15', '2026-02-20', '已处理', '教三楼101'),
('2', '2021001001', '978-7-111-50000-1', 45, 2025.00, '2026-01-15', '2026-02-20', '已处理', '教三楼101'),
('3', '2021002001', '978-7-04-050000-1', 38, 1767.00, '2026-01-16', '2026-02-21', '已处理', '教二楼205'),
('4', '2022001001', '978-7-111-50000-2', 50, 2100.00, '2026-06-20', NULL,          '未发放', NULL),
('5', '2022001001', '978-7-121-50000-1', 50, 2450.00, '2026-06-20', NULL,          '未发放', NULL),
('6', '2022002001', '978-7-04-050000-3', 42, 1675.80, '2026-06-21', NULL,          '未发放', NULL);

-- ============================================================
-- 7. 采购计划表
-- ============================================================
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase` (
  `Bno`      varchar(20)  NOT NULL COMMENT '书号',
  `Bname`    varchar(200) DEFAULT NULL COMMENT '书名',
  `Bauthor`  varchar(100) DEFAULT NULL COMMENT '作者',
  `Bsource`  varchar(100) DEFAULT NULL COMMENT '出版社',
  `Bedition` varchar(50)  DEFAULT NULL COMMENT '版次',
  `Bprice`   varchar(20)  DEFAULT NULL COMMENT '单价',
  `BNumber`  int(11)      DEFAULT NULL COMMENT '采购数量',
  PRIMARY KEY (`Bno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购计划';

INSERT INTO `purchase` VALUES
('978-7-302-50000-1', '数据结构（C语言版）',    '严蔚敏', '清华大学出版社',             '第1版', '35.00', 50),
('978-7-111-50000-2', '软件工程导论（第六版）', '张海藩', '清华大学出版社',             '第6版', '42.00', 40),
('978-7-111-50000-3', '编译原理（第三版）',     '陈火旺', '国防工业出版社',             '第3版', '55.00', 35);

-- ============================================================
-- 完成
-- ============================================================
-- 验证数据
SELECT 'admin' AS `表名`, COUNT(*) AS `记录数` FROM `admin`
UNION ALL SELECT 'teacher', COUNT(*) FROM `teacher`
UNION ALL SELECT 'monitor', COUNT(*) FROM `monitor`
UNION ALL SELECT 'course',  COUNT(*) FROM `course`
UNION ALL SELECT 'book',    COUNT(*) FROM `book`
UNION ALL SELECT 'reserve', COUNT(*) FROM `reserve`
UNION ALL SELECT 'purchase', COUNT(*) FROM `purchase`;
