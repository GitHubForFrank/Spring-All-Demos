use pds_demo01;
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_shop`
-- ----------------------------
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