
DROP TABLE IF EXISTS `verification_mail` ;
CREATE TABLE `verification_mail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipient_mail` varchar(50) NOT NULL COMMENT '接收方邮件地址',
  `create_time` datetime NOT NULL COMMENT '发送邮件的时间,精确到秒',
  `code` varchar(45) NOT NULL COMMENT '发送的验证码信息',
  `deadline` datetime NOT NULL COMMENT '截至的有效时间，精确到秒',
  `weights` double(12,0) NOT NULL COMMENT '权重值， 在一个用户发 送过多条邮件之后，,越早创建的权重值越低，权重值设置方法由创建的时间来决定，,使用创建的时间的字符串转换为数字作为权重值。,如 2018-10-2/ 10:20 的权重值为 201810021020,之后查询数据的时候只需要拿到权重值最大的一条来判断是否过期即可',
  `type` TINYINT NOT NULL DEFAULT 1 COMMENT '表示发送验证邮件的类型- 1 表示 注册验证码 2 表示修改密码验证码 3表示修改密码 4 表示登录安全验证码 5 表示消费验证码 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;