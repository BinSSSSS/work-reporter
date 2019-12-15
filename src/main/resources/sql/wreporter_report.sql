
/*
Navicat MySQL Data Transfer

Source Server         : 127
Source Server Version : 50512
Source Host           : localhost:3306
Source Database       : ybg_report

Target Server Type    : MYSQL
Target Server Version : 50512
File Encoding         : 65001

Date: 2018-02-21 13:29:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `report_column`
-- ----------------------------
DROP TABLE IF EXISTS `report_column`;
CREATE TABLE `report_column` (
  `id` varchar(64) NOT NULL,
  `col_name` varchar(64) DEFAULT NULL,
  `col_key` varchar(64) DEFAULT NULL,
  `col_num` int(11) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `report_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_column
-- ----------------------------

-- ----------------------------
-- Table structure for `report_departmen_table`
-- ----------------------------
DROP TABLE IF EXISTS `report_departmen_table`;
CREATE TABLE `report_departmen_table` (
  `id` varchar(64) NOT NULL,
  `report_id` varchar(255) DEFAULT NULL,
  `department_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `report_id` (`report_id`,`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_departmen_table
-- ----------------------------

-- ----------------------------
-- Table structure for `report_deptment`
-- ----------------------------
DROP TABLE IF EXISTS `report_deptment`;
CREATE TABLE `report_deptment` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `deptno` varchar(64) NOT NULL COMMENT '单位代码（唯一）',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父级ID',
  `dept_name` varchar(64) DEFAULT NULL COMMENT '单位名称',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `ifdel` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `deptno` (`deptno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_deptment
-- ----------------------------

-- ----------------------------
-- Table structure for `report_row`
-- ----------------------------
DROP TABLE IF EXISTS `report_row`;
CREATE TABLE `report_row` (
  `id` varchar(64) NOT NULL,
  `row_name` varchar(64) DEFAULT NULL,
  `row_key` varchar(64) DEFAULT NULL,
  `row_num` int(11) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `report_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_row
-- ----------------------------

-- ----------------------------
-- Table structure for `report_rtable`
-- ----------------------------
DROP TABLE IF EXISTS `report_rtable`;
CREATE TABLE `report_rtable` (
  `id` varchar(64) NOT NULL,
  `report_no` varchar(64) NOT NULL,
  `report_name` varchar(64) DEFAULT NULL,
  `report_year` int(11) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `report_no` (`report_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_rtable
-- ----------------------------

-- ----------------------------
-- Table structure for `report_rtemplate`
-- ----------------------------
DROP TABLE IF EXISTS `report_rtemplate`;
CREATE TABLE `report_rtemplate` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `filename` varchar(64) DEFAULT NULL COMMENT '文件名',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `file_content` text COMMENT '模板内容',
  PRIMARY KEY (`id`),
  UNIQUE KEY `filename` (`filename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_rtemplate
-- ----------------------------
INSERT INTO `report_rtemplate` VALUES ('44a3c6ed5b5e4e3c84bafb7c8efb3ad61519187194323', '2.ureport.xml', '2018-02-21 12:26:34', '2018-02-21 12:34:47', '<?xml version=\"1.0\" encoding=\"UTF-8\"?><ureport><cell expand=\"None\" name=\"A1\" row=\"1\" col=\"1\"><cell-style font-size=\"10\" forecolor=\"0,0,0\" font-family=\"宋体\" align=\"center\" valign=\"middle\"></cell-style><simple-value><![CDATA[]]></simple-value></cell><cell expand=\"None\" name=\"B1\" row=\"1\" col=\"2\"><cell-style font-size=\"10\" forecolor=\"0,0,0\" font-family=\"宋体\" align=\"center\" valign=\"middle\"></cell-style><simple-value><![CDATA[]]></simple-value></cell><cell expand=\"None\" name=\"C1\" row=\"1\" col=\"3\"><cell-style font-size=\"10\" forecolor=\"0,0,0\" font-family=\"宋体\" align=\"center\" valign=\"middle\"></cell-style><simple-value><![CDATA[]]></simple-value></cell><cell expand=\"None\" name=\"D1\" row=\"1\" col=\"4\"><cell-style font-size=\"10\" forecolor=\"0,0,0\" font-family=\"宋体\" align=\"center\" valign=\"middle\"></cell-style><simple-value><![CDATA[]]></simple-value></cell><cell expand=\"None\" name=\"A2\" row=\"2\" col=\"1\"><cell-style font-size=\"10\" forecolor=\"0,0,0\" font-family=\"宋体\" align=\"center\" valign=\"middle\"></cell-style><simple-value><![CDATA[]]></simple-value></cell><cell expand=\"None\" name=\"B2\" row=\"2\" col=\"2\"><cell-style font-size=\"10\" forecolor=\"0,0,0\" font-family=\"宋体\" align=\"center\" valign=\"middle\"></cell-style><simple-value><![CDATA[]]></simple-value></cell><cell expand=\"None\" name=\"C2\" row=\"2\" col=\"3\"><cell-style font-size=\"10\" forecolor=\"0,0,0\" font-family=\"宋体\" align=\"center\" valign=\"middle\"></cell-style><simple-value><![CDATA[aaa]]></simple-value></cell><cell expand=\"None\" name=\"D2\" row=\"2\" col=\"4\"><cell-style font-size=\"10\" forecolor=\"0,0,0\" font-family=\"宋体\" align=\"center\" valign=\"middle\"></cell-style><simple-value><![CDATA[bbb]]></simple-value></cell><cell expand=\"None\" name=\"A3\" row=\"3\" col=\"1\"><cell-style font-size=\"10\" forecolor=\"0,0,0\" font-family=\"宋体\" align=\"center\" valign=\"middle\"></cell-style><simple-value><![CDATA[]]></simple-value></cell><cell expand=\"None\" name=\"B3\" row=\"3\" col=\"2\"><cell-style font-size=\"10\" forecolor=\"0,0,0\" font-family=\"宋体\" align=\"center\" valign=\"middle\"></cell-style><simple-value><![CDATA[]]></simple-value></cell><cell expand=\"None\" name=\"C3\" row=\"3\" col=\"3\"><cell-style font-size=\"10\" forecolor=\"0,0,0\" font-family=\"宋体\" align=\"center\" valign=\"middle\"></cell-style><simple-value><![CDATA[]]></simple-value></cell><cell expand=\"None\" name=\"D3\" row=\"3\" col=\"4\"><cell-style font-size=\"10\" forecolor=\"0,0,0\" font-family=\"宋体\" align=\"center\" valign=\"middle\"></cell-style><simple-value><![CDATA[]]></simple-value></cell><row row-number=\"1\" height=\"18\"/><row row-number=\"2\" height=\"18\"/><row row-number=\"3\" height=\"18\"/><column col-number=\"1\" width=\"80\"/><column col-number=\"2\" width=\"80\"/><column col-number=\"3\" width=\"80\"/><column col-number=\"4\" width=\"80\"/><paper type=\"A4\" left-margin=\"90\" right-margin=\"90\"\n    top-margin=\"72\" bottom-margin=\"72\" paging-mode=\"fitpage\" fixrows=\"0\"\n    width=\"595\" height=\"842\" orientation=\"portrait\" html-report-align=\"left\" bg-image=\"\" html-interval-refresh-value=\"0\" column-enabled=\"false\"></paper></ureport>');
