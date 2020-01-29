/**************************************************************************************************
*  Version No.      Date               Remark
*  1.0              2019-12-22         Tables initialization SQL
***************************************************************************************************/
--=========================================Create Basic Tables =========================================
-----------------------------------------------------------------------------------------------------------------
-- Table structure for `programmer`
-----------------------------------------------------------------------------------------------------------------
use pds_demo01;
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `programmer`;

create table programmer (
  id       int primary key auto_increment,
  name     varchar(20),
  age      tinyint,
  salary   float,
  birthday datetime
)

SELECT * from pds_demo01.programmer;

-----------------------------------------------------------------------------------------------------------------
-- Table structure for `t_shop`
-----------------------------------------------------------------------------------------------------------------
use pds_demo01;
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `t_shop`;

CREATE TABLE `t_shop` (
  `id` bigint(20) NOT NULL auto_increment,
  `shopId` varchar(100) NULL COMMENT '外部平台店铺id',
  `catId` varchar(100) NULL COMMENT '店铺所属类目id',
  `nick` varchar(100) NULL COMMENT '卖家昵称',
  `title` varchar(100) NULL COMMENT '店铺标题',
  `description` varchar(100) NULL COMMENT '店铺描述',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

SELECT * from pds_demo01.t_shop;
SELECT * from pds_demo02.t_shop;

delete from pds_demo01.t_shop;
delete from pds_demo02.t_shop;

insert into pds_demo01.t_shop values (1,'shopId01','catId01','nick01','title01','description01');
insert into pds_demo02.t_shop values (1,'shopId02','catId02','nick02','title02','description02');

-----------------------------------------------------------------------------------------------------------------
-- Table structure for `tbl_user`
-----------------------------------------------------------------------------------------------------------------
use pds_demo01;
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `tbl_user`;

CREATE TABLE `tbl_user` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(100) NULL COMMENT '名字',
  `dept` varchar(100) NULL COMMENT '部门',
  `phone` varchar(100) NULL COMMENT '电话',
  `website` varchar(100) NULL COMMENT '网站',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

SELECT * from pds_demo01.tbl_user;


