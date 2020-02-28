-- ----------------------------
-- 1、部门表
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint(20)      not null auto_increment    comment '部门id',
  parent_id         bigint(20)      default 0                  comment '父部门id',
  ancestors         varchar(50)     default ''                 comment '祖级列表',
  dept_name         varchar(30)     default ''                 comment '部门名称',
  order_num         int(4)          default 0                  comment '显示顺序',
  leader            varchar(20)     default null               comment '负责人',
  phone             varchar(11)     default null               comment '联系电话',
  email             varchar(50)     default null               comment '邮箱',
  status            char(1)         default '0'                comment '部门状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (dept_id)
) engine=innodb auto_increment=200 comment = '部门表';

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------
insert into sys_dept values(100,  0,   '0',          '肇庆学院',   0, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(101,  100, '0,100',      '计算机学院', 1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(102,  100, '0,100',      '美术学院', 2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(103,  100, '0,100',      '数学与统计学院', 3, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(104,  100, '0,100',      '政法学院', 4, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(105,  100, '0,100',      '教育科学学院', 5, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(106,  100, '0,100',      '体育与健康学院', 6, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(107,  100, '0,100',      '生命科学学院', 7, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(108,  100, '0,100',      '机械与汽车工程学院', 8, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(109,  100, '0,100',      '电子与电气学院', 9, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(110,  100, '0,100',      '旅游与历史文化学院', 10, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(111,  100, '0,100',      '音乐学院', 11, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(112,  100, '0,100',      '外国语学院', 12, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(113,  100, '0,100',      '文学院', 13, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');


insert into sys_dept values(1113,  101, '0,100,101',  '计算机科学与技术',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1114,  101, '0,100,101',  '软件工程',   2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1115,  101, '0,100,101',  '网络工程',   3, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1116,  101, '0,100,101',  '物联网工程',   4, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1117,  101, '0,100,101',  '数据科学与大数据技术',   5, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');

insert into sys_dept values(1118,  102, '0,100,102',  '产品设计',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1119,  102, '0,100,102',  '动画',   2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1120,  102, '0,100,102',  '工艺美术',   3, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1121,  102, '0,100,102',  '环境设计',   4, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1122,  102, '0,100,102',  '美术学',   5, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1123,  102, '0,100,102',  '视觉传达',   6, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1124,  102, '0,100,102',  '工业设计',   7, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');

insert into sys_dept values(1125,  103, '0,100,103',  '金融数学',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1126,  103, '0,100,103',  '数学与应用数学',   2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1127,  103, '0,100,103',  '统计学',   3, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');

insert into sys_dept values(1128,  104, '0,100,104',  '法学',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1129,  104, '0,100,104',  '行政管理',   2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1130,  104, '0,100,104',  '思想政治教育',   3, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1131,  104, '0,100,104',  '知识产权',   4, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');

insert into sys_dept values(1132,  105, '0,100,105',  '小学教育',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1133,  105, '0,100,105',  '学前教育',   2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1134,  105, '0,100,105',  '应用心理学',   3, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1135,  105, '0,100,105',  '数字媒体技术',   4, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');

insert into sys_dept values(1136,  106, '0,100,106',  '体育教育',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');

insert into sys_dept values(1137,  107, '0,100,107',  '风景园林',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1138,  107, '0,100,107',  '生物技术',   2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1139,  107, '0,100,107',  '生物科学',   3, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');

insert into sys_dept values(1140,  108, '0,100,108',  '车辆工程',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1141,  108, '0,100,108',  '机械设计制造及其自动化',   2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');

insert into sys_dept values(1142,  109, '0,100,109',  '电气工程及其自动化',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1143,  109, '0,100,109',  '电子信息科学与技术',   2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1144,  109, '0,100,109',  '通信工程',   3, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1145,  109, '0,100,109',  '物理学',   4, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');

insert into sys_dept values(1146,  110, '0,100,110',  '酒店管理',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1147,  110, '0,100,110',  '历史学',   2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');

insert into sys_dept values(1148,  111, '0,100,111',  '舞蹈学',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1149,  111, '0,100,111',  '音乐表演',   2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1150,  111, '0,100,111',  '音乐学',   3, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');

insert into sys_dept values(1151,  112, '0,100,112',  '日语',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1152,  112, '0,100,112',  '商务英语',   2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1153,  112, '0,100,112',  '英语',   3, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');

insert into sys_dept values(1154,  112, '0,100,112',  '思想政治教育',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1155,  112, '0,100,112',  '知识产权',   2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');

insert into sys_dept values(1156,  113, '0,100,113',  '广播电视学',   1, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1157,  113, '0,100,113',  '汉语言文学',   2, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1158,  113, '0,100,113',  '秘书学',   3, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');
insert into sys_dept values(1159,  113, '0,100,113',  '书法学',   4, 'AMS', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00');







-- ----------------------------
-- 2、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null auto_increment    comment '用户ID',
  dept_id           bigint(20)      default null               comment '部门ID',
  login_name        varchar(30)     not null                   comment '登录账号',
  user_name         varchar(30)     not null                   comment '用户昵称',
  user_type         varchar(2)      default '00'               comment '用户类型（00系统用户）',
  email             varchar(50)     default ''                 comment '用户邮箱',
  phonenumber       varchar(11)     default ''                 comment '手机号码',
  sex               char(1)         default '0'                comment '用户性别（0男 1女 2未知）',
  card_number       varchar(12)     default ''                 comment '用户卡号(12位学号)',
  avatar            varchar(100)    default ''                 comment '头像路径',
  password          varchar(50)     default ''                 comment '密码',
  salt              varchar(20)     default ''                 comment '盐加密',
  status            char(1)         default '0'                comment '帐号状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  login_ip          varchar(50)     default ''                 comment '最后登陆IP',
  login_date        datetime                                   comment '最后登陆时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (user_id)
) engine=innodb auto_increment=100 comment = '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
insert into sys_user values(1,  1116, 'admin', 'AMS', '00', 'ry@163.com', '15888888888', '1','201624134148', '', '29c67a30398638269fe600f73a054934', '111111', '0', '0', '127.0.0.1', '2018-03-16 11-33-00', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '管理员');
insert into sys_user values(2,  1125, 'ry',    'AMS', '00', 'ry@qq.com',  '15666666666', '1','201624134148','', '8e6d98b90472783cc73c17047ddccf36', '222222', '0', '0', '127.0.0.1', '2018-03-16 11-33-00', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '测试员');
INSERT INTO sys_user VALUES (3, 1132, 'zc', 'zengchao', '00', '920253445@qq.com', '15976195138', '0','201624134148', '', '472deaf7c5a0e68690d6c27a165e1b26', '800ced', '0', '0', '127.0.0.1', '2020-01-05 00:01:12', 'admin', '2020-01-01 21:58:24', 'admin', '2020-01-05 00:01:11', '');
INSERT INTO sys_user VALUES (4, 1141, 'zengchao', 'zengchao', '00', '920253447@qq.com', '15976195139', '0','201624134148', '', 'e8a55db6899004e7160a66969538e5b8', '15c838', '0', '0', '127.0.0.1', '2020-01-01 23:11:09', 'admin', '2020-01-01 23:10:08', '', '2020-01-05 23:30:39', '管理员2');
INSERT INTO sys_user VALUES (6, 1119, 'zengchao1', 'test', '00', '1121012388@qq.com', '15976195131', '0', '201624134148','', '303e9b4ed2a4e6dacc9776e21e065a36', '1dcf5f', '0', '0', '127.0.0.1', '2020-01-05 00:05:54', 'admin', '2020-01-05 00:05:03', '', '2020-01-05 00:05:53', NULL);


-- ----------------------------
-- 3、岗位信息表
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigint(20)      not null auto_increment    comment '岗位ID',
  post_code     varchar(64)     not null                   comment '岗位编码',
  post_name     varchar(50)     not null                   comment '岗位名称',
  post_sort     int(4)          not null                   comment '显示顺序',
  status        char(1)         not null                   comment '状态（0正常 1停用）',
  create_by     varchar(64)     default ''                 comment '创建者',
  create_time   datetime                                   comment '创建时间',
  update_by     varchar(64)     default ''			       comment '更新者',
  update_time   datetime                                   comment '更新时间',
  remark        varchar(500)    default null               comment '备注',
  primary key (post_id)
) engine=innodb comment = '岗位信息表';

-- ----------------------------
-- 初始化-岗位信息表数据
-- ----------------------------
insert into sys_post values(1, 'ceo',  '董事长',    1, '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_post values(2, 'se',   '项目经理',  2, '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_post values(3, 'hr',   '人力资源',  3, '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_post values(4, 'user', '普通员工',  4, '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');


-- ----------------------------
-- 4、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id           bigint(20)      not null auto_increment    comment '角色ID',
  role_name         varchar(30)     not null                   comment '角色名称',
  role_key          varchar(100)    not null                   comment '角色权限字符串',
  role_sort         int(4)          not null                   comment '显示顺序',
  data_scope        char(1)         default '1'                comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  status            char(1)         not null                   comment '角色状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role values('1', '管理员',   'admin',  1, 1, '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '管理员');
insert into sys_role values('2', '普通角色', 'common', 2, 2, '0', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '普通角色');


-- ----------------------------
-- 5、菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment '菜单ID',
  menu_name         varchar(50)     not null                   comment '菜单名称',
  parent_id         bigint(20)      default 0                  comment '父菜单ID',
  order_num         int(4)          default 0                  comment '显示顺序',
  url               varchar(200)    default '#'                comment '请求地址',
  target            varchar(20)     default ''                 comment '打开方式（menuItem页签 menuBlank新窗口）',
  menu_type         char(1)         default ''                 comment '菜单类型（M目录 C菜单 F按钮）',
  visible           char(1)         default 0                  comment '菜单状态（0显示 1隐藏）',
  perms             varchar(100)    default null               comment '权限标识',
  icon              varchar(100)    default '#'                comment '菜单图标',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default ''                 comment '备注',
  primary key (menu_id)
) engine=innodb auto_increment=2000 comment = '菜单权限表';

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
insert into sys_menu values('1', '系统管理', '0', '1', '#', '', 'M', '0', '', 'fa fa-gear',         'admin', '2018-03-16 11-33-00', 'zengchao', '2018-03-16 11-33-00', '系统管理目录');
insert into sys_menu values('2', '系统监控', '0', '2', '#', '', 'M', '0', '', 'fa fa-video-camera', 'admin', '2018-03-16 11-33-00', 'zengchao', '2018-03-16 11-33-00', '系统监控目录');
insert into sys_menu values('3', '系统工具', '0', '3', '#', '', 'M', '0', '', 'fa fa-bars',         'admin', '2018-03-16 11-33-00', 'zengchao', '2018-03-16 11-33-00', '系统工具目录');
insert into sys_menu values('4', '资产管理', '0', '4', '#', '', 'M', '0', '', 'fa fa-hospital-o','admin','2019-12-24 19-57-11','zengchao','2019-12-24 19-57-22','资产管理目录');
-- 二级菜单
insert into sys_menu values('100',  '用户管理', '1', '1', '/system/user',          '', 'C', '0', 'system:user:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '用户管理菜单');
insert into sys_menu values('101',  '角色管理', '1', '2', '/system/role',          '', 'C', '0', 'system:role:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '角色管理菜单');
insert into sys_menu values('102',  '菜单管理', '1', '3', '/system/menu',          '', 'C', '0', 'system:menu:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '菜单管理菜单');
insert into sys_menu values('103',  '部门管理', '1', '4', '/system/dept',          '', 'C', '0', 'system:dept:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '部门管理菜单');
insert into sys_menu values('104',  '岗位管理', '1', '5', '/system/post',          '', 'C', '0', 'system:post:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '岗位管理菜单');
insert into sys_menu values('105',  '字典管理', '1', '6', '/system/dict',          '', 'C', '0', 'system:dict:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '字典管理菜单');
insert into sys_menu values('106',  '参数设置', '1', '7', '/system/config',        '', 'C', '0', 'system:config:view',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '参数设置菜单');
insert into sys_menu values('107',  '通知公告', '1', '8', '/system/notice',        '', 'C', '0', 'system:notice:view',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '通知公告菜单');
insert into sys_menu values('108',  '日志管理', '1', '9', '#',                     '', 'M', '0', '',                         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '日志管理菜单');

insert into sys_menu values('109',  '在线用户', '2', '1', '/monitor/online',       '', 'C', '0', 'monitor:online:view',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '在线用户菜单');
insert into sys_menu values('110',  '定时任务', '2', '2', '/monitor/job',          '', 'C', '0', 'monitor:job:view',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '定时任务菜单');
insert into sys_menu values('111',  '数据监控', '2', '3', '/monitor/data',         '', 'C', '0', 'monitor:data:view',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '数据监控菜单');
insert into sys_menu values('112',  '服务监控', '2', '3', '/monitor/server',       '', 'C', '0', 'monitor:server:view',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '服务监控菜单');
insert into sys_menu values('113',  '表单构建', '3', '1', '/tool/build',           '', 'C', '0', 'tool:build:view',          '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '表单构建菜单');
insert into sys_menu values('114',  '代码生成', '3', '2', '/tool/gen',             '', 'C', '0', 'tool:gen:view',            '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '代码生成菜单');
insert into sys_menu values('115',  '系统接口', '3', '3', '/tool/swagger',         '', 'C', '0', 'tool:swagger:view',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '系统接口菜单');
insert into sys_menu values('116',  '入账管理', '4', '1', '/assets/accounting',                      '', 'C', '0', 'assets:accounting:view',         '#', 'admin', '2018-03-16 11-33-00', 'zengchao', '2019-12-31 17-48-11', '入账管理菜单');
insert into sys_menu values('117',  '领用管理', '4', '2', '/assets/allocate',                      '', 'C', '0', 'assets:allocate:view',         '#', 'admin', '2018-03-16 11-33-00', 'zengchao', '2019-12-31 17-48-11', '领用管理菜单');
insert into sys_menu values('118',  '借用管理', '4', '4', '/assets/borrow',                      '', 'C', '0', 'assets:borrow:view',         '#', 'admin', '2018-03-16 11-33-00', 'zengchao', '2019-12-31 17-48-11', '借出管理菜单');
insert into sys_menu values('119',  '保养管理', '4', '5', '/assets/maintain',                      '', 'C', '0', 'assets:maintain:view',         '#', 'admin', '2018-03-16 11-33-00', 'zengchao', '2019-12-31 17-48-11', '保养管理菜单');
insert into sys_menu values('120',  '转移管理', '4', '6', '/assets/transfer',                      '', 'C', '0', 'assets:move:view',         '#', 'admin', '2018-03-16 11-33-00', 'zengchao', '2019-12-31 17-48-11', '转移管理菜单');
insert into sys_menu values('121',  '维修管理', '4', '7', '/assets/repair',                      '', 'C', '0', 'assets:repair:view',         '#', 'admin', '2018-03-16 11-33-00', 'zengchao', '2019-12-31 17-48-11', '保修管理菜单');
insert into sys_menu values('122',  '事故管理', '4', '8', '/assets/accident',                      '', 'C', '0', 'assets:accident:view',       '#', 'admin', '2018-03-16 11-33-00', 'zengchao', '2019-12-31 17-48-11', '报废管理菜单');
insert into sys_menu values('123',  '报废管理', '4', '9', '/assets/alarm',                      '', 'C', '0', 'assets:alarm:view',       '#', 'admin', '2018-03-16 11-33-00', 'zengchao', '2019-12-31 17-48-11', '报警管理菜单');
insert into sys_menu values('124',  '盘点管理', '4', '10', '/assets/checkTask',                     '', 'C', '0', 'assets:checkTask:view',                         '#', 'admin', '2018-03-16 11-33-00', 'zengchao', '2019-12-31 17-48-11', '盘点管理菜单');
insert into sys_menu values('125',  '报表管理', '4', '11','#',                                     '', 'M', '0', '',                         '#', 'admin', '2018-03-16 11-33-00', 'zengchao', '2019-12-31 17-48-11', '报表管理菜单');
insert into sys_menu values('126',  '归还管理', '4', '3', '/assets/return',                      '', 'C', '0', 'assets:return:view',         '#', 'admin', '2018-03-16 11-33-00', 'zengchao', '2019-12-31 17-48-11', '归还管理菜单');

-- 三级菜单
insert into sys_menu values('500',  '操作日志', '108', '1', '/monitor/operlog',    '', 'C', '0', 'monitor:operlog:view',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '操作日志菜单');
insert into sys_menu values('501',  '登录日志', '108', '2', '/monitor/logininfor', '', 'C', '0', 'monitor:logininfor:view',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '登录日志菜单');
insert into sys_menu values('600',  '入库报表', '125', '1', '/assets/chart/ruKu',    '', 'C', '0', 'chart:ruKu:view',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '入库报表菜单');
insert into sys_menu values('601',  '领用报表', '125', '2', '/assets/chart/lingYong', '', 'C', '0', 'chart:lingYong:view',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '领用报表菜单');
insert into sys_menu values('603',  '资产报表', '125', '3', '/assets/chart/ziChan', '', 'C', '0', 'chart:ziChan:view',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '资产报表菜单');
insert into sys_menu values('604',  '借还报表', '125', '4', '/assets/chart/jieHuan',    '', 'C', '0', 'chart:jieHuan:view',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '借还报表菜单');
insert into sys_menu values('605',  '维修报表', '125', '5', '/assets/chart/weiXiu', '', 'C', '0', 'chart:weiXiu:view',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '维修报表菜单');
insert into sys_menu values('606',  '盘点报表', '125', '6', '/assets/chart/panDian', '', 'C', '0', 'chart:panDian:view',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '盘点报表菜单');

-- 用户管理按钮
insert into sys_menu values('1000', '用户查询', '100', '1',  '#', '',  'F', '0', 'system:user:list',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1001', '用户新增', '100', '2',  '#', '',  'F', '0', 'system:user:add',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1002', '用户修改', '100', '3',  '#', '',  'F', '0', 'system:user:edit',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1003', '用户删除', '100', '4',  '#', '',  'F', '0', 'system:user:remove',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1004', '用户导出', '100', '5',  '#', '',  'F', '0', 'system:user:export',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1005', '用户导入', '100', '6',  '#', '',  'F', '0', 'system:user:import',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1006', '重置密码', '100', '7',  '#', '',  'F', '0', 'system:user:resetPwd',    '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 角色管理按钮
insert into sys_menu values('1007', '角色查询', '101', '1',  '#', '',  'F', '0', 'system:role:list',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1008', '角色新增', '101', '2',  '#', '',  'F', '0', 'system:role:add',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1009', '角色修改', '101', '3',  '#', '',  'F', '0', 'system:role:edit',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1010', '角色删除', '101', '4',  '#', '',  'F', '0', 'system:role:remove',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1011', '角色导出', '101', '5',  '#', '',  'F', '0', 'system:role:export',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 菜单管理按钮
insert into sys_menu values('1012', '菜单查询', '102', '1',  '#', '',  'F', '0', 'system:menu:list',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1013', '菜单新增', '102', '2',  '#', '',  'F', '0', 'system:menu:add',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1014', '菜单修改', '102', '3',  '#', '',  'F', '0', 'system:menu:edit',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1015', '菜单删除', '102', '4',  '#', '',  'F', '0', 'system:menu:remove',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 部门管理按钮
insert into sys_menu values('1016', '部门查询', '103', '1',  '#', '',  'F', '0', 'system:dept:list',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1017', '部门新增', '103', '2',  '#', '',  'F', '0', 'system:dept:add',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1018', '部门修改', '103', '3',  '#', '',  'F', '0', 'system:dept:edit',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1019', '部门删除', '103', '4',  '#', '',  'F', '0', 'system:dept:remove',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 岗位管理按钮
insert into sys_menu values('1020', '岗位查询', '104', '1',  '#', '',  'F', '0', 'system:post:list',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1021', '岗位新增', '104', '2',  '#', '',  'F', '0', 'system:post:add',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1022', '岗位修改', '104', '3',  '#', '',  'F', '0', 'system:post:edit',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1023', '岗位删除', '104', '4',  '#', '',  'F', '0', 'system:post:remove',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1024', '岗位导出', '104', '5',  '#', '',  'F', '0', 'system:post:export',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 字典管理按钮
insert into sys_menu values('1025', '字典查询', '105', '1', '#', '',  'F', '0', 'system:dict:list',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1026', '字典新增', '105', '2', '#', '',  'F', '0', 'system:dict:add',          '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1027', '字典修改', '105', '3', '#', '',  'F', '0', 'system:dict:edit',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1028', '字典删除', '105', '4', '#', '',  'F', '0', 'system:dict:remove',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1029', '字典导出', '105', '5', '#', '',  'F', '0', 'system:dict:export',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 参数设置按钮
insert into sys_menu values('1030', '参数查询', '106', '1', '#', '',  'F', '0', 'system:config:list',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1031', '参数新增', '106', '2', '#', '',  'F', '0', 'system:config:add',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1032', '参数修改', '106', '3', '#', '',  'F', '0', 'system:config:edit',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1033', '参数删除', '106', '4', '#', '',  'F', '0', 'system:config:remove',    '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1034', '参数导出', '106', '5', '#', '',  'F', '0', 'system:config:export',    '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 通知公告按钮
insert into sys_menu values('1035', '公告查询', '107', '1', '#', '',  'F', '0', 'system:notice:list',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1036', '公告新增', '107', '2', '#', '',  'F', '0', 'system:notice:add',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1037', '公告修改', '107', '3', '#', '',  'F', '0', 'system:notice:edit',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1038', '公告删除', '107', '4', '#', '',  'F', '0', 'system:notice:remove',    '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 操作日志按钮
insert into sys_menu values('1039', '操作查询', '500', '1', '#', '',  'F', '0', 'monitor:operlog:list',    '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1040', '操作删除', '500', '2', '#', '',  'F', '0', 'monitor:operlog:remove',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1041', '详细信息', '500', '3', '#', '',  'F', '0', 'monitor:operlog:detail',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1042', '日志导出', '500', '4', '#', '',  'F', '0', 'monitor:operlog:export',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 登录日志按钮
insert into sys_menu values('1043', '登录查询', '501', '1', '#', '',  'F', '0', 'monitor:logininfor:list',         '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1044', '登录删除', '501', '2', '#', '',  'F', '0', 'monitor:logininfor:remove',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1045', '日志导出', '501', '3', '#', '',  'F', '0', 'monitor:logininfor:export',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1046', '账户解锁', '501', '4', '#', '',  'F', '0', 'monitor:logininfor:unlock',       '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 在线用户按钮
insert into sys_menu values('1047', '在线查询', '109', '1', '#', '',  'F', '0', 'monitor:online:list',             '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1048', '批量强退', '109', '2', '#', '',  'F', '0', 'monitor:online:batchForceLogout', '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1049', '单条强退', '109', '3', '#', '',  'F', '0', 'monitor:online:forceLogout',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 定时任务按钮
insert into sys_menu values('1050', '任务查询', '110', '1', '#', '',  'F', '0', 'monitor:job:list',                '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1051', '任务新增', '110', '2', '#', '',  'F', '0', 'monitor:job:add',                 '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1052', '任务修改', '110', '3', '#', '',  'F', '0', 'monitor:job:edit',                '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1053', '任务删除', '110', '4', '#', '',  'F', '0', 'monitor:job:remove',              '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1054', '状态修改', '110', '5', '#', '',  'F', '0', 'monitor:job:changeStatus',        '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1055', '任务详细', '110', '6', '#', '',  'F', '0', 'monitor:job:detail',              '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1056', '任务导出', '110', '7', '#', '',  'F', '0', 'monitor:job:export',              '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 代码生成按钮
insert into sys_menu values('1057', '生成查询', '114', '1', '#', '',  'F', '0', 'tool:gen:list',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1058', '生成修改', '114', '2', '#', '',  'F', '0', 'tool:gen:edit',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1059', '生成删除', '114', '3', '#', '',  'F', '0', 'tool:gen:remove',   '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1060', '预览代码', '114', '4', '#', '',  'F', '0', 'tool:gen:preview',  '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1061', '生成代码', '114', '5', '#', '',  'F', '0', 'tool:gen:code',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 入账管理按钮
insert into sys_menu values('1062', '资产入账查询', '116', '1', '#', '',  'F', '0', 'assets:accounting:list',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1063', '资产新增', '116', '2', '#', '',  'F', '0', 'assets:accounting:add',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1064', '资产修改', '116', '3', '#', '',  'F', '0', 'assets:accounting:edit',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1065', '资产删除', '116', '4', '#', '',  'F', '0', 'assets:accounting:remove',   '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1066', '资产导出', '116', '5',  '#', '',  'F', '0', 'assets:accounting:export',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1067', '资产导入', '116', '6',  '#', '',  'F', '0', 'assets:accounting:import',      '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 领用管理按钮
insert into sys_menu values('1068', '保养查询', '117', '1', '#', '',  'F', '0', 'assets:allocate:list',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1069', '保养新增', '117', '2', '#', '',  'F', '0', 'assets:allocate:add',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1070', '保养修改', '117', '3', '#', '',  'F', '0', 'assets:allocate:edit',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1071', '保养删除', '117', '4', '#', '',  'F', '0', 'assets:allocate:remove',   '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 借还管理按钮
insert into sys_menu values('1072', '借用查询', '118', '1', '#', '',  'F', '0', 'assets:borrow:list',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1073', '借用新增', '118', '2', '#', '',  'F', '0', 'assets:borrow:add',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1074', '借用修改', '118', '3', '#', '',  'F', '0', 'assets:borrow:edit',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1075', '借用删除', '118', '4', '#', '',  'F', '0', 'assets:borrow:remove',   '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1076', '归还查询', '118', '5', '#', '',  'F', '0', 'assets:return:list',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1077', '归还新增', '118', '6', '#', '',  'F', '0', 'assets:return:add',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1078', '归还修改', '118', '7', '#', '',  'F', '0', 'assets:return:edit',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1079', '归还删除', '118', '8', '#', '',  'F', '0', 'assets:return:remove',   '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 保养管理按钮
insert into sys_menu values('1080', '保养查询', '119', '1', '#', '',  'F', '0', 'assets:maintain:list',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1081', '保养新增', '119', '2', '#', '',  'F', '0', 'assets:maintain:add',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1082', '保养修改', '119', '3', '#', '',  'F', '0', 'assets:maintain:edit',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1083', '保养删除', '119', '4', '#', '',  'F', '0', 'assets:maintain:remove',   '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 事故管理按钮
insert into sys_menu values('1084', '事故查询', '122', '1', '#', '',  'F', '0', 'assets:accident:list',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1085', '事故新增', '122', '2', '#', '',  'F', '0', 'assets:accident:add',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1086', '事故修改', '122', '3', '#', '',  'F', '0', 'assets:accident:edit',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1087', '事故删除', '122', '4', '#', '',  'F', '0', 'assets:accident:remove',   '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 维修管理按钮
insert into sys_menu values('1088', '维修查询', '121', '1', '#', '',  'F', '0', 'assets:repair:list',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1089', '维修新增', '121', '2', '#', '',  'F', '0', 'assets:repair:add',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1090', '维修修改', '121', '3', '#', '',  'F', '0', 'assets:repair:edit',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1091', '维修删除', '121', '4', '#', '',  'F', '0', 'assets:repair:remove',   '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 转移管理按钮
insert into sys_menu values('1092', '转移查询', '120', '1', '#', '',  'F', '0', 'assets:transfer:list',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1093', '转移新增', '120', '2', '#', '',  'F', '0', 'assets:transfer:add',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1094', '转移修改', '120', '3', '#', '',  'F', '0', 'assets:transfer:edit',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1095', '转移删除', '120', '4', '#', '',  'F', '0', 'assets:transfer:remove',   '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
-- 盘点管理按钮
insert into sys_menu values('1096', '盘点查询', '120', '1', '#', '',  'F', '0', 'assets:checkTask:list',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1097', '盘点新增', '120', '2', '#', '',  'F', '0', 'assets:checkTask:add',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1098', '盘点修改', '120', '3', '#', '',  'F', '0', 'assets:checkTask:edit',     '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_menu values('1099', '盘点删除', '120', '4', '#', '',  'F', '0', 'assets:checkTask:remove',   '#', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');





-- ----------------------------
-- 6、用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment '用户ID',
  role_id   bigint(20) not null comment '角色ID',
  primary key(user_id, role_id)
) engine=innodb comment = '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '2');


-- ----------------------------
-- 7、角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment '角色ID',
  menu_id   bigint(20) not null comment '菜单ID',
  primary key(role_id, menu_id)
) engine=innodb comment = '角色和菜单关联表';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
insert into sys_role_menu values ('2', '1');
insert into sys_role_menu values ('2', '2');
insert into sys_role_menu values ('2', '3');
insert into sys_role_menu values ('2', '4');
insert into sys_role_menu values ('2', '100');
insert into sys_role_menu values ('2', '101');
insert into sys_role_menu values ('2', '102');
insert into sys_role_menu values ('2', '103');
insert into sys_role_menu values ('2', '104');
insert into sys_role_menu values ('2', '105');
insert into sys_role_menu values ('2', '106');
insert into sys_role_menu values ('2', '107');
insert into sys_role_menu values ('2', '108');
insert into sys_role_menu values ('2', '109');
insert into sys_role_menu values ('2', '110');
insert into sys_role_menu values ('2', '111');
insert into sys_role_menu values ('2', '112');
insert into sys_role_menu values ('2', '113');
insert into sys_role_menu values ('2', '114');
insert into sys_role_menu values ('2', '115');

insert into sys_role_menu values ('2', '116');
insert into sys_role_menu values ('2', '117');
insert into sys_role_menu values ('2', '118');
insert into sys_role_menu values ('2', '119');
insert into sys_role_menu values ('2', '120');
insert into sys_role_menu values ('2', '121');
insert into sys_role_menu values ('2', '122');
insert into sys_role_menu values ('2', '124');
insert into sys_role_menu values ('2', '126');


insert into sys_role_menu values ('2', '500');
insert into sys_role_menu values ('2', '501');
insert into sys_role_menu values ('2', '1000');
insert into sys_role_menu values ('2', '1001');
insert into sys_role_menu values ('2', '1002');
insert into sys_role_menu values ('2', '1003');
insert into sys_role_menu values ('2', '1004');
insert into sys_role_menu values ('2', '1005');
insert into sys_role_menu values ('2', '1006');
insert into sys_role_menu values ('2', '1007');
insert into sys_role_menu values ('2', '1008');
insert into sys_role_menu values ('2', '1009');
insert into sys_role_menu values ('2', '1010');
insert into sys_role_menu values ('2', '1011');
insert into sys_role_menu values ('2', '1012');
insert into sys_role_menu values ('2', '1013');
insert into sys_role_menu values ('2', '1014');
insert into sys_role_menu values ('2', '1015');
insert into sys_role_menu values ('2', '1016');
insert into sys_role_menu values ('2', '1017');
insert into sys_role_menu values ('2', '1018');
insert into sys_role_menu values ('2', '1019');
insert into sys_role_menu values ('2', '1020');
insert into sys_role_menu values ('2', '1021');
insert into sys_role_menu values ('2', '1022');
insert into sys_role_menu values ('2', '1023');
insert into sys_role_menu values ('2', '1024');
insert into sys_role_menu values ('2', '1025');
insert into sys_role_menu values ('2', '1026');
insert into sys_role_menu values ('2', '1027');
insert into sys_role_menu values ('2', '1028');
insert into sys_role_menu values ('2', '1029');
insert into sys_role_menu values ('2', '1030');
insert into sys_role_menu values ('2', '1031');
insert into sys_role_menu values ('2', '1032');
insert into sys_role_menu values ('2', '1033');
insert into sys_role_menu values ('2', '1034');
insert into sys_role_menu values ('2', '1035');
insert into sys_role_menu values ('2', '1036');
insert into sys_role_menu values ('2', '1037');
insert into sys_role_menu values ('2', '1038');
insert into sys_role_menu values ('2', '1039');
insert into sys_role_menu values ('2', '1040');
insert into sys_role_menu values ('2', '1041');
insert into sys_role_menu values ('2', '1042');
insert into sys_role_menu values ('2', '1043');
insert into sys_role_menu values ('2', '1044');
insert into sys_role_menu values ('2', '1045');
insert into sys_role_menu values ('2', '1046');
insert into sys_role_menu values ('2', '1047');
insert into sys_role_menu values ('2', '1048');
insert into sys_role_menu values ('2', '1049');
insert into sys_role_menu values ('2', '1050');
insert into sys_role_menu values ('2', '1051');
insert into sys_role_menu values ('2', '1052');
insert into sys_role_menu values ('2', '1053');
insert into sys_role_menu values ('2', '1054');
insert into sys_role_menu values ('2', '1055');
insert into sys_role_menu values ('2', '1056');
insert into sys_role_menu values ('2', '1057');
insert into sys_role_menu values ('2', '1058');
insert into sys_role_menu values ('2', '1059');
insert into sys_role_menu values ('2', '1060');
insert into sys_role_menu values ('2', '1061');

insert into sys_role_menu values ('2', '1062');
insert into sys_role_menu values ('2', '1068');
insert into sys_role_menu values ('2', '1069');
insert into sys_role_menu values ('2', '1072');
insert into sys_role_menu values ('2', '1073');
insert into sys_role_menu values ('2', '1076');
insert into sys_role_menu values ('2', '1077');
insert into sys_role_menu values ('2', '1080');
insert into sys_role_menu values ('2', '1081');
insert into sys_role_menu values ('2', '1084');
insert into sys_role_menu values ('2', '1085');
insert into sys_role_menu values ('2', '1088');
insert into sys_role_menu values ('2', '1089');
insert into sys_role_menu values ('2', '1096');



-- ----------------------------
-- 8、角色和部门关联表  角色1-N部门
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint(20) not null comment '角色ID',
  dept_id   bigint(20) not null comment '部门ID',
  primary key(role_id, dept_id)
) engine=innodb comment = '角色和部门关联表';

-- ----------------------------
-- 初始化-角色和部门关联表数据
-- ----------------------------
insert into sys_role_dept values ('2', '100');
insert into sys_role_dept values ('2', '101');
insert into sys_role_dept values ('2', '105');

-- ----------------------------
-- 9、用户与岗位关联表  用户1-N岗位
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint(20) not null comment '用户ID',
  post_id   bigint(20) not null comment '岗位ID',
  primary key (user_id, post_id)
) engine=innodb comment = '用户与岗位关联表';

-- ----------------------------
-- 初始化-用户与岗位关联表数据
-- ----------------------------
insert into sys_user_post values ('1', '1');
insert into sys_user_post values ('2', '2');


-- ----------------------------
-- 10、操作日志记录
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigint(20)      not null auto_increment    comment '日志主键',
  title             varchar(50)     default ''                 comment '模块标题',
  business_type     int(2)          default 0                  comment '业务类型（0其它 1新增 2修改 3删除）',
  method            varchar(100)    default ''                 comment '方法名称',
  request_method    varchar(10)     default ''                 comment '请求方式',
  operator_type     int(1)          default 0                  comment '操作类别（0其它 1后台用户 2手机端用户）',
  oper_name         varchar(50)     default ''                 comment '操作人员',
  dept_name         varchar(50)     default ''                 comment '部门名称',
  oper_url          varchar(255)    default ''                 comment '请求URL',
  oper_ip           varchar(50)     default ''                 comment '主机地址',
  oper_location     varchar(255)    default ''                 comment '操作地点',
  oper_param        varchar(2000)   default ''                 comment '请求参数',
  json_result       varchar(2000)   default ''                 comment '返回参数',
  status            int(1)          default 0                  comment '操作状态（0正常 1异常）',
  error_msg         varchar(2000)   default ''                 comment '错误消息',
  oper_time         datetime                                   comment '操作时间',
  primary key (oper_id)
) engine=innodb auto_increment=100 comment = '操作日志记录';


-- ----------------------------
-- 11、字典类型表
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint(20)      not null auto_increment    comment '字典主键',
  dict_name        varchar(100)    default ''                 comment '字典名称',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=100 comment = '字典类型表';

insert into sys_dict_type values(1,  '用户性别', 'sys_user_sex',        '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '用户性别列表');
insert into sys_dict_type values(2,  '菜单状态', 'sys_show_hide',       '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '菜单状态列表');
insert into sys_dict_type values(3,  '系统开关', 'sys_normal_disable',  '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '系统开关列表');
insert into sys_dict_type values(4,  '任务状态', 'sys_job_status',      '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '任务状态列表');
insert into sys_dict_type values(5,  '任务分组', 'sys_job_group',       '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '任务分组列表');
insert into sys_dict_type values(6,  '系统是否', 'sys_yes_no',          '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '系统是否列表');
insert into sys_dict_type values(7,  '通知类型', 'sys_notice_type',     '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '通知类型列表');
insert into sys_dict_type values(8,  '通知状态', 'sys_notice_status',   '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '通知状态列表');
insert into sys_dict_type values(9,  '操作类型', 'sys_oper_type',       '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '操作类型列表');
insert into sys_dict_type values(10, '系统状态', 'sys_common_status',   '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '登录状态列表');


-- ----------------------------
-- 12、字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint(20)      not null auto_increment    comment '字典编码',
  dict_sort        int(4)          default 0                  comment '字典排序',
  dict_label       varchar(100)    default ''                 comment '字典标签',
  dict_value       varchar(100)    default ''                 comment '字典键值',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  css_class        varchar(100)    default null               comment '样式属性（其他样式扩展）',
  list_class       varchar(100)    default null               comment '表格回显样式',
  is_default       char(1)         default 'N'                comment '是否默认（Y是 N否）',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = '字典数据表';

insert into sys_dict_data values(1,  1,  '男',       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '性别男');
insert into sys_dict_data values(2,  2,  '女',       '1',       'sys_user_sex',        '',   '',        'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '性别女');
insert into sys_dict_data values(3,  3,  '未知',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '性别未知');
insert into sys_dict_data values(4,  1,  '显示',     '0',       'sys_show_hide',       '',   'primary', 'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '显示菜单');
insert into sys_dict_data values(5,  2,  '隐藏',     '1',       'sys_show_hide',       '',   'danger',  'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '隐藏菜单');
insert into sys_dict_data values(6,  1,  '正常',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '正常状态');
insert into sys_dict_data values(7,  2,  '停用',     '1',       'sys_normal_disable',  '',   'danger',  'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '停用状态');
insert into sys_dict_data values(8,  1,  '正常',     '0',       'sys_job_status',      '',   'primary', 'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '正常状态');
insert into sys_dict_data values(9,  2,  '暂停',     '1',       'sys_job_status',      '',   'danger',  'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '停用状态');
insert into sys_dict_data values(10, 1,  '默认',     'DEFAULT', 'sys_job_group',       '',   '',        'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '默认分组');
insert into sys_dict_data values(11, 2,  '系统',     'SYSTEM',  'sys_job_group',       '',   '',        'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '系统分组');
insert into sys_dict_data values(12, 1,  '是',       'Y',       'sys_yes_no',          '',   'primary', 'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '系统默认是');
insert into sys_dict_data values(13, 2,  '否',       'N',       'sys_yes_no',          '',   'danger',  'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '系统默认否');
insert into sys_dict_data values(14, 1,  '通知',     '1',       'sys_notice_type',     '',   'warning', 'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '通知');
insert into sys_dict_data values(15, 2,  '公告',     '2',       'sys_notice_type',     '',   'success', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '公告');
insert into sys_dict_data values(16, 1,  '正常',     '0',       'sys_notice_status',   '',   'primary', 'Y', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '正常状态');
insert into sys_dict_data values(17, 2,  '关闭',     '1',       'sys_notice_status',   '',   'danger',  'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '关闭状态');
insert into sys_dict_data values(18, 1,  '新增',     '1',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '新增操作');
insert into sys_dict_data values(19, 2,  '修改',     '2',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '修改操作');
insert into sys_dict_data values(20, 3,  '删除',     '3',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '删除操作');
insert into sys_dict_data values(21, 4,  '授权',     '4',       'sys_oper_type',       '',   'primary', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '授权操作');
insert into sys_dict_data values(22, 5,  '导出',     '5',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '导出操作');
insert into sys_dict_data values(23, 6,  '导入',     '6',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '导入操作');
insert into sys_dict_data values(24, 7,  '强退',     '7',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '强退操作');
insert into sys_dict_data values(25, 8,  '生成代码', '8',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '生成操作');
insert into sys_dict_data values(26, 9,  '清空数据', '9',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '清空操作');
insert into sys_dict_data values(27, 1,  '成功',     '0',       'sys_common_status',   '',   'primary', 'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '正常状态');
insert into sys_dict_data values(28, 2,  '失败',     '1',       'sys_common_status',   '',   'danger',  'N', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '停用状态');
insert into sys_dict_data values(29,  1,  '购置',       '0',       'assets_source',        '',   '',        'Y', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '来源-购置');
insert into sys_dict_data values(30,  2,  '赠送',       '1',       'assets_source',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '来源-赠送');
insert into sys_dict_data values(31,  3,  '未知',     '2',       'assets_source',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '来源未知');
insert into sys_dict_data values(32,  1,  '低值资产',       '0',       'assets_nature',        '',   '',        'Y', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '低值资产');
insert into sys_dict_data values(33,  2,  '中值资产',       '1',       'assets_nature',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '中值资产');
insert into sys_dict_data values(34,  3,  '贵重器材',     '2',       'assets_nature',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '贵重器材');
insert into sys_dict_data values(35,  1,  '仪器仪表',       '0',       'assets_type',        '',   '',        'Y', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '仪器仪表');
insert into sys_dict_data values(36,  2,  '机电设备',       '1',       'assets_type',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '机电设备');
insert into sys_dict_data values(37,  3,  '电子设备',     '2',       'assets_type',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '电子设备');
insert into sys_dict_data values(38,  4,  '印刷设备',       '3',       'assets_type',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '印刷设备');
insert into sys_dict_data values(39,  5,  '卫生医疗器械',       '4',       'assets_type',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '卫生医疗器械');
insert into sys_dict_data values(40,  6,  '文体设备',     '5',       'assets_type',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '文体设备');
insert into sys_dict_data values(41,  7,  '标本模型',       '6',       'assets_type',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '标本模型');
insert into sys_dict_data values(42,  8,  '文物及陈列品',       '7',       'assets_type',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '文物及陈列品');
insert into sys_dict_data values(43,  9,  '图书',     '8',       'assets_type',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '图书');
insert into sys_dict_data values(44,  10,  '工具、量具和器皿',       '9',       'assets_type',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '工具、量具和器皿');
insert into sys_dict_data values(45,  11,  '家具',       '10',       'assets_type',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '家具');
insert into sys_dict_data values(46,  12,  '行政办公设备',     '11',       'assets_type',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '行政办公设备');
insert into sys_dict_data values(47,  1,  '闲置',       '0',       'assets_status',        '',   '',        'Y', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '闲置状态');
insert into sys_dict_data values(48,  2,  '预约中',       '1',       'assets_status',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '预约中状态');
insert into sys_dict_data values(49,  3,  '在用',       '1',       'assets_status',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '在用状态');
insert into sys_dict_data values(50,  4,  '维修中',     '2',       'assets_status',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '维修中状态');
insert into sys_dict_data values(51,  5,  '待报废',       '3',       'assets_status',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '待报废状态');
insert into sys_dict_data values(52,  6,  '停用',     '4',       'assets_status',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '停用状态');
insert into sys_dict_data values(53,  1,  '未归还',     '0',       'is_return',        '',   '',        'Y', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '未归还状态');
insert into sys_dict_data values(54,  2,  '已归还',     '1',       'is_return',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '已归还状态');
insert into sys_dict_data values(55,  1,  '常规检查',     '0',       'maintain_name',        '',   '',        'Y', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '常规检查状态');
insert into sys_dict_data values(56,  2,  '抢修',     '1',       'maintain_name',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '抢修状态');
insert into sys_dict_data values(57,  1,  '正常',     '0',       'maintain_status',        '',   '',        'Y', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '正常状态');
insert into sys_dict_data values(58,  2,  '故障',     '1',       'maintain_status',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '故障状态');
insert into sys_dict_data values(59,  1,  '部件维修',     '1',       'repair_grade',        '',   '',        'Y', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '部件维修状态');
insert into sys_dict_data values(60,  2,  '中修',     '2',       'repair_grade',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '中修状态');
insert into sys_dict_data values(61,  3,  '大修',     '3',       'repair_grade',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '大修状态');
insert into sys_dict_data values(62,  1,  '正常',     '1',       'repair_status',        '',   '',        'Y', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '正常状态');
insert into sys_dict_data values(63,  2,  '待报废',     '2',       'repair_status',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '待报废状态');
insert into sys_dict_data values(64,  3,  '故障',     '3',       'repair_status',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '故障状态');
insert into sys_dict_data values(65,  1,  '一般事故',     '1',       'accident_grade',        '',   '',        'Y', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '一般事故状态');
insert into sys_dict_data values(66,  2,  '中等事故',     '2',       'accident_grade',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '中等事故状态');
insert into sys_dict_data values(67,  3,  '重大事故',     '3',       'accident_grade',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '重大事故状态');
insert into sys_dict_data values(68,  1,  '未盘点',     '0',       'is_check',        '',   '',        'Y', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '未盘点状态');
insert into sys_dict_data values(69,  2,  '已盘点',     '1',       'is_check',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '已盘点状态');
insert into sys_dict_data values(70,  1,  '未开始',     '0',       'check_status',        '',   '',        'Y', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '未开始状态');
insert into sys_dict_data values(71,  2,  '审核中',     '1',       'check_status',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '审核中状态');
insert into sys_dict_data values(72,  3,  '同意',     '2',       'check_status',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '同意状态');
insert into sys_dict_data values(73,  4,  '驳回',     '3',       'check_status',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '驳回状态');
insert into sys_dict_data values(74,  1,  '审核中',     '0',       'examine_status',        '',   '',        'Y', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '审核中状态');
insert into sys_dict_data values(75,  2,  '同意',     '1',       'examine_status',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '同意状态');
insert into sys_dict_data values(76,  3,  '驳回',     '2',       'examine_status',        '',   '',        'N', '0', 'admin', '2020-01-16 11-33-00', 'zc', '2020-01-16 11-33-00', '驳回状态');






-- ----------------------------
-- 13、参数配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         int(5)          not null auto_increment    comment '参数主键',
  config_name       varchar(100)    default ''                 comment '参数名称',
  config_key        varchar(100)    default ''                 comment '参数键名',
  config_value      varchar(500)    default ''                 comment '参数键值',
  config_type       char(1)         default 'N'                comment '系统内置（Y是 N否）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (config_id)
) engine=innodb auto_increment=100 comment = '参数配置表';

insert into sys_config values(1, '主框架页-默认皮肤样式名称', 'sys.index.skinName',     'skin-blue',     'Y', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow' );
insert into sys_config values(2, '用户管理-账号初始密码',     'sys.user.initPassword',  '123456',        'Y', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '初始化密码 123456' );
insert into sys_config values(3, '主框架页-侧边栏主题',       'sys.index.sideTheme',    'theme-dark',    'Y', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '深色主题theme-dark，浅色主题theme-light' );


-- ----------------------------
-- 14、系统访问记录
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigint(20)     not null auto_increment   comment '访问ID',
  login_name     varchar(50)    default ''                comment '登录账号',
  ipaddr         varchar(50)    default ''                comment '登录IP地址',
  login_location varchar(255)   default ''                comment '登录地点',
  browser        varchar(50)    default ''                comment '浏览器类型',
  os             varchar(50)    default ''                comment '操作系统',
  status         char(1)        default '0'               comment '登录状态（0成功 1失败）',
  msg            varchar(255)   default ''                comment '提示消息',
  login_time     datetime                                 comment '访问时间',
  primary key (info_id)
) engine=innodb auto_increment=100 comment = '系统访问记录';


-- ----------------------------
-- 15、在线用户记录
-- ----------------------------
drop table if exists sys_user_online;
create table sys_user_online (
  sessionId         varchar(50)   default ''                comment '用户会话id',
  login_name        varchar(50)   default ''                comment '登录账号',
  dept_name         varchar(50)   default ''                comment '部门名称',
  ipaddr            varchar(50)   default ''                comment '登录IP地址',
  login_location    varchar(255)  default ''                comment '登录地点',
  browser           varchar(50)   default ''                comment '浏览器类型',
  os                varchar(50)   default ''                comment '操作系统',
  status            varchar(10)   default ''                comment '在线状态on_line在线off_line离线',
  start_timestamp   datetime                                comment 'session创建时间',
  last_access_time  datetime                                comment 'session最后访问时间',
  expire_time       int(5)        default 0                 comment '超时时间，单位为分钟',
  primary key (sessionId)
) engine=innodb comment = '在线用户记录';


-- ----------------------------
-- 16、定时任务调度表
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id              bigint(20)    not null auto_increment    comment '任务ID',
  job_name            varchar(64)   default ''                 comment '任务名称',
  job_group           varchar(64)   default 'DEFAULT'          comment '任务组名',
  invoke_target       varchar(500)  not null                   comment '调用目标字符串',
  cron_expression     varchar(255)  default ''                 comment 'cron执行表达式',
  misfire_policy      varchar(20)   default '3'                comment '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  concurrent          char(1)       default '1'                comment '是否并发执行（0允许 1禁止）',
  status              char(1)       default '0'                comment '状态（0正常 1暂停）',
  create_by           varchar(64)   default ''                 comment '创建者',
  create_time         datetime                                 comment '创建时间',
  update_by           varchar(64)   default ''                 comment '更新者',
  update_time         datetime                                 comment '更新时间',
  remark              varchar(500)  default ''                 comment '备注信息',
  primary key (job_id, job_name, job_group)
) engine=innodb auto_increment=100 comment = '定时任务调度表';

insert into sys_job values(1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams',        '0/10 * * * * ?', '3', '1', '1', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_job values(2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')',  '0/15 * * * * ?', '3', '1', '1', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');
insert into sys_job values(3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)',  '0/20 * * * * ?', '3', '1', '1', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '');


-- ----------------------------
-- 17、定时任务调度日志表
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigint(20)     not null auto_increment    comment '任务日志ID',
  job_name            varchar(64)    not null                   comment '任务名称',
  job_group           varchar(64)    not null                   comment '任务组名',
  invoke_target       varchar(500)   not null                   comment '调用目标字符串',
  job_message         varchar(500)                              comment '日志信息',
  status              char(1)        default '0'                comment '执行状态（0正常 1失败）',
  exception_info      varchar(2000)  default ''                 comment '异常信息',
  create_time         datetime                                  comment '创建时间',
  primary key (job_log_id)
) engine=innodb comment = '定时任务调度日志表';


-- ----------------------------
-- 18、通知公告表
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id         int(4)          not null auto_increment    comment '公告ID',
  notice_title      varchar(50)     not null                   comment '公告标题',
  notice_type       char(1)         not null                   comment '公告类型（1通知 2公告）',
  notice_content    varchar(2000)   default null               comment '公告内容',
  status            char(1)         default '0'                comment '公告状态（0正常 1关闭）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(255)    default null               comment '备注',
  primary key (notice_id)
) engine=innodb auto_increment=10 comment = '通知公告表';

-- ----------------------------
-- 初始化-公告信息表数据
-- ----------------------------
insert into sys_notice values('1', '温馨提醒：2018-07-01 AMS新版本发布啦', '2', '新版本内容', '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '管理员');
insert into sys_notice values('2', '维护通知：2018-07-01 AMS系统凌晨维护', '1', '维护内容',   '0', 'admin', '2018-03-16 11-33-00', 'ry', '2018-03-16 11-33-00', '管理员');


-- ----------------------------
-- 19、代码生成业务表
-- ----------------------------
drop table if exists gen_table;
create table gen_table (
  table_id          bigint(20)      not null auto_increment    comment '编号',
  table_name        varchar(200)    default ''                 comment '表名称',
  table_comment     varchar(500)    default ''                 comment '表描述',
  class_name        varchar(100)    default ''                 comment '实体类名称',
  tpl_category      varchar(200)    default 'crud'             comment '使用的模板（crud单表操作 tree树表操作）',
  package_name      varchar(100)                               comment '生成包路径',
  module_name       varchar(30)                                comment '生成模块名',
  business_name     varchar(30)                                comment '生成业务名',
  function_name     varchar(50)                                comment '生成功能名',
  function_author   varchar(50)                                comment '生成功能作者',
  options           varchar(1000)                              comment '其它生成选项',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (table_id)
) engine=innodb auto_increment=1 comment = '代码生成业务表';


-- ----------------------------
-- 20、代码生成业务表字段
-- ----------------------------
drop table if exists gen_table_column;
create table gen_table_column (
  column_id         bigint(20)      not null auto_increment    comment '编号',
  table_id          varchar(64)                                comment '归属表编号',
  column_name       varchar(200)                               comment '列名称',
  column_comment    varchar(500)                               comment '列描述',
  column_type       varchar(100)                               comment '列类型',
  java_type         varchar(500)                               comment 'JAVA类型',
  java_field        varchar(200)                               comment 'JAVA字段名',
  is_pk             char(1)                                    comment '是否主键（1是）',
  is_increment      char(1)                                    comment '是否自增（1是）',
  is_required       char(1)                                    comment '是否必填（1是）',
  is_insert         char(1)                                    comment '是否为插入字段（1是）',
  is_edit           char(1)                                    comment '是否编辑字段（1是）',
  is_list           char(1)                                    comment '是否列表字段（1是）',
  is_query          char(1)                                    comment '是否查询字段（1是）',
  query_type        varchar(200)    default 'EQ'               comment '查询方式（等于、不等于、大于、小于、范围）',
  html_type         varchar(200)                               comment '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  dict_type         varchar(200)    default ''                 comment '字典类型',
  sort              int                                        comment '排序',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time 	    datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (column_id)
) engine=innodb auto_increment=1 comment = '代码生成业务表字段';


-- ----------------------------
-- 21、资产信息表
-- ----------------------------
drop table if exists assets_accounting;
create table assets_accounting(
    assets_id        int              not null auto_increment     comment '资产ID',
    assets_number    varchar(15)       not null default ''         comment '资产编号',
    assets_name      varchar(20)      not null default ''         comment '资产名称',
    assets_nature    varchar(10)      default ''                  comment '资产性质',
    assets_type      varchar(10)      default ''                  comment '资产类别',
    assets_amount    int              default 1                   comment '数量',
    measuring_unit   char(1)          default ''                  comment '计量单位',
    assets_price     float            default 0.0                 comment '单价',
    storage_unit     varchar(10)      default ''                  comment '保管单位',
    storage_department      varchar(10)         default ''          comment '保管部门',
    custodian               varchar(10)         default ''          comment '保管人',
    user                    varchar(10)         default ''          comment '使用人',
    storage_addr            varchar(20)         default ''          comment '存放地点',
    use_status              char(1)             default '0'         comment '资产状态(0：闲置 1：审核中  2：在用 3：维修中 4；待报废 5：停用)',
    assets_source           varchar(10)         default '购置'      comment '资产来源(0：购置  1：赠送  3：未知)',
    useful_life             varchar(5)          default ''          comment '使用年限',
    residual_rate           float               default 0.0         comment '残值率',
    check_status            char(1)             default '0'         comment '是否盘点(0：否  1：是)',
    assets_format           varchar(10)         default ''          comment '规格',
    assets_model            varchar(20)         default ''          comment '型号',
    assets_brand            varchar(50)         default ''          comment '品牌',
    purchase_date           varchar(10)         default ''          comment '购入日期',
    manufacture_date        varchar(10)         default ''          comment '出厂日期',
    warranty_date           varchar(10)         default ''          comment '保修日期',
    maintain_cycle          varchar(15)         default ''          comment '保养周期(单位天)',
    maintain_date           varchar(20)         default ''          comment '上次保养日期',
    del_flag                char(1)             default '0'         comment '删除标志（0：正常  2：已删除）',
    mark                    text                                    comment '备注' ,
    create_by               varchar(64)         default ''          comment '创建者',
    create_time              timestamp           not null default current_timestamp      comment '创建时间/登记时间',
    update_by               varchar(64)         default ''          comment '更新者',
    update_time              timestamp           not null default current_timestamp on update current_timestamp      comment '更新时间',
    primary key (assets_id),
    unique key (assets_number)
)engine = innodb default charset utf8 comment '资产导入表';

-- ----------------------------
-- 初始化-资产信息
-- ----------------------------
INSERT INTO assets_accounting VALUES (1, 'a9b7e8', '电脑', '低值资产', '办公家具', 1, '台', 5400, '新奥燃气', '行政部', '', '', '第一教学楼101教室', '1', '购置', '5', 0.03, '1', '', '启天M7150', '联想', '2019-05-25', '', '', '0', NULL, 'admin', '2020-01-21 09:46:58', '', '2020-01-21 09:46:58');
INSERT INTO assets_accounting VALUES (2, 'c377d8', '彩色打印机', '低值资产', '办公家具', 1, '台', 8890, '新奥燃气', '行政部', '', '', '第三教学楼103教室', '1', '购置', '5', 0.06, '1', '', 'CP1020', '惠普', '2019-05-26', '', '', '0', NULL, 'admin', '2020-01-21 09:46:58', '', '2020-01-21 09:46:58');
INSERT INTO assets_accounting VALUES (3, 'e6f4d2', '传真机', '低值资产', '仪器仪表', 1, '台', 3000, '新奥燃气', '行政部', '', '', '第二教学楼102教室', '1', '购置', '2', 0.04, '1', '', 'SF-560R', '三星', '2019-05-27', '', '', '0', NULL, 'admin', '2020-01-21 09:46:58', '', '2020-01-21 09:47:17');
INSERT INTO assets_accounting VALUES (4, '38d87b', '打印机', '低值资产', '仪器仪表', 1, '台', 1000.5, '新奥燃气', '行政部', '', '', '福慧图书馆电子阅览室', '0', '购置', '3', 0.12, '1', '', 'S2520', 'FUJI', '2019-05-28', '2014-01-29', '', '0', NULL, 'admin', '2020-01-21 09:46:58', '', '2020-01-21 09:47:52');
INSERT INTO assets_accounting VALUES (5, '5e6a1b', '电视机', '低值资产', '仪器仪表', 1, '台', 5800, '新奥燃气', '行政部', '', '', '中巴软件园708教室', '1', '赠送', '5', 0.22, '0', '', '', 'KONKA', '2019-05-29', '', '', '0', NULL, 'admin', '2020-01-21 09:46:58', '', '2020-01-21 16:48:18');
INSERT INTO assets_accounting VALUES (6, 'oodsaf23', '打印机', '低值资产', '行政办公设备', 1, '台', 1345.5, '计算机学院', '物联', '', '', '中巴软件园709教室', '1', '购置', '', 0, '0', '', '', '', '', '', '', '0', NULL, 'admin', '2020-01-21 09:57:24', '', '2020-01-22 10:27:47');
INSERT INTO assets_accounting VALUES (7, 'oodsbe24', '打印机', '低值资产', '行政办公设备', 1, '台', 1345.5, '计算机学院', '物联', '', '', '学生活动中心', '1', '购置', '', 0, '0', '', '', '', '', '', '', '0', NULL, 'admin', '2020-01-21 09:57:24', '', '2020-01-22 10:27:47');
INSERT INTO assets_accounting VALUES (8, 'oodsct25', '打印机', '低值资产', '行政办公设备', 1, '台', 1345.5, '计算机学院', '物联', '', '', '学术大厅', '1', '购置', '', 0, '0', '', '', '', '', '', '', '0', NULL, 'admin', '2020-01-21 09:57:24', '', '2020-01-22 10:27:47');
INSERT INTO assets_accounting VALUES (9, 'oodsaf26', '打印机', '低值资产', '行政办公设备', 1, '台', 1345.5, '计算机学院', '物联', '', '', '行政楼106室', '1', '购置', '', 0, '0', '', '', '', '', '', '', '0', NULL, 'admin', '2020-01-21 09:57:24', '', '2020-01-22 10:27:47');
INSERT INTO assets_accounting VALUES (10, 'oidsqf27', '打印机', '低值资产', '行政办公设备', 1, '台', 1345.5, '计算机学院', '物联', '', '', '实验大楼305室', '1', '购置', '', 0, '0', '', '', '', '', '', '', '0', NULL, 'admin', '2020-01-21 09:57:24', '', '2020-01-22 10:27:47');
INSERT INTO assets_accounting VALUES (11, 'oodsmf28', '打印机', '低值资产', '行政办公设备', 1, '台', 1345.5, '计算机学院', '物联', '', '', '实验大楼306室', '1', '购置', '', 0, '0', '', '', '', '', '', '', '0', NULL, 'admin', '2020-01-21 09:57:24', '', '2020-01-22 10:27:47');
INSERT INTO assets_accounting VALUES (12, 'oodsaf29', '打印机', '低值资产', '行政办公设备', 1, '台', 1345.5, '计算机学院', '物联', '', '', '实验大楼411室', '1', '购置', '', 0, '0', '', '', '', '', '', '', '0', NULL, 'admin', '2020-01-21 09:57:24', '', '2020-01-22 10:27:47');
INSERT INTO assets_accounting VALUES (13, 'oedjaf30', '打印机', '低值资产', '行政办公设备', 1, '台', 1345.5, '计算机学院', '物联', '', '', '第一教学楼101教室', '1', '购置', '', 0, '0', '', '', '', '', '', '', '0', NULL, 'admin', '2020-01-21 09:57:24', '', '2020-01-22 10:27:47');
INSERT INTO assets_accounting VALUES (14, 'uusoc31', '飞利浦投影仪', '低值资产', '行政办公设备', 1, '台', 1345.5, '计算机学院', '物联', '', '', '第一教学楼101教室', '1', '购置', '', 0, '0', '', '', '', '', '', '', '0', NULL, 'admin', '2020-01-21 09:57:24', '', '2020-01-22 10:27:47');
INSERT INTO assets_accounting VALUES (15, 'esdc73', '凯德龙音响', '低值资产', '行政办公设备', 1, '台', 1345.5, '计算机学院', '物联', '', '', '中巴软件园708教室', '1', '购置', '', 0, '0', '', '', '', '', '', '', '0', NULL, 'admin', '2020-01-21 09:57:24', '', '2020-01-22 10:27:47');
INSERT INTO assets_accounting VALUES (16, 'dd78aa3', '中智讯嵌入式实验箱001', '中值资产', '实验教学设备', 1, '台', 1345.5, '计算机学院', '物联', '', '', '中巴软件园708教室', '1', '购置', '', 0, '0', '', '', '', '', '', '', '0', NULL, 'admin', '2020-01-21 09:57:24', '', '2020-01-22 10:27:47');


-- ----------------------------
-- 22、资产来源表
-- ----------------------------
drop table if exists assets_source;
create table assets_source(

    source_id           int         not null auto_increment             comment '资产来源ID',
    source_name         varchar(20)         not null        default ''  comment '资产来源名称',
    source_sort         int(4)              not null                    comment '显示顺序',
    status              char(1)             not null                    comment '状态（0正常  1停用）',
    create_by           varchar(64)         default ''                  comment '创建者',
    create_time         timestamp           not null default current_timestamp      comment '创建时间',
    update_by           varchar(64)         default ''                  comment '更新者',
    update_time         timestamp           not null default current_timestamp  on update current_timestamp   comment '更新时间',
    remark              text                                            comment '备注',
    primary key (source_id)
)engine = innodb default charset utf8 comment '资产来源信息表';

-- ----------------------------
-- 初始化-资产来源信息
-- ----------------------------
insert into assets_source values (1,'购置',1,'0','admin','2020-01-14 11:50:06','zc','2020-01-14 11:50:08','');
insert into assets_source values (2,'赠送',2,'0','admin','2020-01-14 11:50:06','zc','2020-01-14 11:50:08','');


-- ----------------------------
-- 23、资产领用表
-- ----------------------------
drop table if exists assets_allocate;
create table assets_allocate(
    allocate_id           int               not null auto_increment         comment '资产领用ID',
    allocate_orderNum       varchar(50)       not null default ''             comment '领用单号',
    assets_number         varchar(15)       not null default ''             comment '资产编号',
    user_id               int               not null default 0              comment '用户ID',
    auditor_id            int               not null default 0              comment '审批者ID',
    status                char(1)           not null default '0'            comment '资产领用状态（0：审核中，1：审核通过，2：驳回）',
    remark                text                                              comment '备注',
    create_by               varchar(64)         default ''          comment '创建者',
    create_time              timestamp           not null default current_timestamp      comment '创建时间/登记时间',
    update_by               varchar(64)         default ''          comment '更新者',
    update_time              timestamp           not null default current_timestamp on update current_timestamp      comment '更新时间',
    primary key (allocate_id)

)engine = innodb default charset utf8 comment '资产领用表';
-- ----------------------------
-- 初始化-资产领用信息
-- ----------------------------
insert into assets_allocate values (1,'LY20190101120000','a9b7e8',2,0,'0','资产分配','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_allocate values (2,'LY20190101120005','e6f4d2',3,0,'0','资产分配','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_allocate values (3,'LY20190101120010','c377d8',2,0,'0','资产分配','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_allocate values (4,'LY20190101120015','5e6a1b',2,0,'0','资产分配','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');


-- ----------------------------
-- 24、资产借用表
-- ----------------------------
drop table if exists assets_borrow;
create table assets_borrow(
    borrow_id             int               not null auto_increment         comment '借还表ID',
    borrow_orderNum       varchar(50)       not null default ''             comment '借用单号',
    assets_number         varchar(15)       not null default ''             comment '资产编号',
    borrow_userid         int               not null default 0              comment '借用人ID',
    enter_person          varchar(15)       not null default ''             comment '录入人',
    auditor_id            int(11)           NOT NULL DEFAULT '0'            COMMENT '审批者ID',
    status                char(1)           NOT NULL DEFAULT '0'            COMMENT '状态（0：审核中，1：审核通过，2：驳回）',
    remark                text                                              comment '备注',
    is_return             char(1)           not null default '0'            comment '是否归还（0：未归还 1：归还审核中 2：已归还）',
    borrow_time           timestamp         not null default current_timestamp             comment '借用时间',
    return_time           timestamp         not null default current_timestamp             comment '预计归还时间',
    create_by               varchar(64)         default ''          comment '创建者',
    create_time              timestamp           not null default current_timestamp      comment '创建时间/登记时间',
    update_by               varchar(64)         default ''          comment '更新者',
    update_time              timestamp           not null default current_timestamp on update current_timestamp      comment '更新时间',
    primary key (borrow_id)
)engine = innodb default charset utf8 comment '资产借还表';
-- ----------------------------
-- 初始化-资产借用信息
-- ----------------------------
insert into assets_borrow values (1,'JY20190101120000','a9b7e8',2,'',0,0,'资产借用','0','2020-01-14 11:50:06','2020-01-14 11:50:08','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_borrow values (2,'JY20190101120005','e6f4d2',3,'',0,0,'资产借用','0','2020-01-14 11:50:06','2020-01-14 11:50:08','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_borrow values (3,'JY20190101120010','c377d8',2,'',0,0,'资产借用','0','2020-01-14 11:50:06','2020-01-14 11:50:08','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_borrow values (4,'JY20190101120015','5e6a1b',2,'',0,0,'资产借用','0','2020-01-14 11:50:06','2020-01-14 11:50:08','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');

-- ----------------------------
-- 24、资产归还表
-- ----------------------------
drop table if exists assets_return;
create table assets_return(
    return_id                       int                 not null auto_increment                     comment '归还id',
    return_orderNum                 varchar(50)         not null default ''                         comment '归还单号',
    assets_number                   varchar(15)         not null default ''                         comment '资产编号',
    return_userId                   int                 not null default 0                          comment '归还人员id',
    return_time                     timestamp           not null default current_timestamp          comment '归还日期',
    enter_person                    varchar(50)         not null default ''                         comment '录入人',
    auditor_id            int(11)           NOT NULL DEFAULT '0'            COMMENT '审批者ID',
    status                char(1)           NOT NULL DEFAULT '0'            COMMENT '状态（0：审核中，1：审核通过，2：驳回）',
    remark                          text                                                            comment '备注',
    create_by                       varchar(64)         default ''          comment '创建者',
    create_time                     timestamp           not null default current_timestamp      comment '创建时间/登记时间',
    update_by                       varchar(64)         default ''          comment '更新者',
    update_time                     timestamp           not null default current_timestamp on update current_timestamp      comment '更新时间',
    primary key (return_id)
)engine = innodb default charset utf8 comment '资产归还表';
-- ----------------------------
-- 初始化-资产归还信息
-- ----------------------------
insert into assets_return values (1,'GH20190101120000','a9b7e8',2,'2020-01-14 11:50:06','',0,0,'资产借用','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_return values (2,'GH20190101120005','e6f4d2',3,'2020-01-14 11:50:06','',0,0,'资产借用','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_return values (3,'GH20190101120010','c377d8',2,'2020-01-14 11:50:06','',0,0,'资产借用','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_return values (4,'GH20190101120015','5e6a1b',2,'2020-01-14 11:50:06','',0,0,'资产借用','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');

-- ----------------------------
-- 25、资产保养表
-- ----------------------------
drop table if exists assets_maintain;
create table assets_maintain(
     maintain_id           int               not null auto_increment         comment '保养表ID',
     maintain_orderNum                 varchar(50)         not null default ''                         comment '保养单号',
     assets_number         varchar(15)       not null default ''             comment '资产编号',
     maintain_userId       int               not null default 0              comment '保养人ID',
     maintain_name         char(1)           not null default ''             comment '保养名称（0：常规检查，1：抢修）',
     maintain_status       char(1)           not null default '0'            comment '保养后状态（0：正常，1：故障）',
     auditor_id            int(11)           NOT NULL DEFAULT '0'            COMMENT '审批者ID',
     status                char(1)           NOT NULL DEFAULT '0'            COMMENT '状态（0：审核中，1：审核通过，2：驳回）',
     remark                text                                              comment '备注',
     maintain_time         timestamp         not null default current_timestamp             comment '保养时间',
     create_by               varchar(64)         default ''          comment '创建者',
     create_time              timestamp           not null default current_timestamp      comment '创建时间/登记时间',
     update_by               varchar(64)         default ''          comment '更新者',
     update_time              timestamp           not null default current_timestamp on update current_timestamp      comment '更新时间',
     primary key (maintain_id)
)engine = innodb default charset utf8 comment '资产保养表';
-- ----------------------------
-- 初始化-资产保养信息
-- ----------------------------
insert into assets_maintain values (1,'BY20190101120000','a9b7e8',2,0,0,0,0,'资产保养','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_maintain values (2,'BY20190101120005','e6f4d2',3,1,0,0,0,'资产保养','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_maintain values (3,'BY20190101120010','c377d8',2,1,0,0,0,'资产保养','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_maintain values (4,'BY20190101120015','5e6a1b',2,0,0,0,0,'资产保养','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');


-- ----------------------------
-- 26、设备事故表
-- ----------------------------
drop table if exists assets_accident;
create table assets_accident(
     accident_id           int               not null auto_increment         comment '事故ID',
     accident_orderNum                 varchar(50)         not null default ''                         comment '事故单号',
     assets_number         varchar(15)       not null default ''             comment '资产编号',
     report_userId         int               not null default 0              comment '事故上报人ID',
     accident_dept         varchar(50)       not null default ''             comment '事故发生部门',
     accident_grade        char(1)           not null default ''             comment '事故级别（1：一般事故，2：中等事故，3：重大事故）',
     economic_losses       varchar(10)       not null default ''             comment '经济损失',
     auditor_id            int(11)           NOT NULL DEFAULT '0'            COMMENT '审批者ID',
     status                char(1)           NOT NULL DEFAULT '0'            COMMENT '状态（0：审核中，1：审核通过，2：驳回）',
     remark       text                                              comment '故障描述',
     summary               text                                              comment '防范总结',
     accident_time         timestamp         not null default current_timestamp      comment '事故发生日期',
     create_by               varchar(64)         default ''          comment '创建者',
     create_time              timestamp           not null default current_timestamp      comment '创建时间/登记时间',
     update_by               varchar(64)         default ''          comment '更新者',
     update_time              timestamp           not null default current_timestamp on update current_timestamp      comment '更新时间',
     primary key (accident_id)
)engine = innodb default charset utf8 comment '资产事故表';
-- ----------------------------
-- 初始化-设备事故信息
-- ----------------------------
insert into assets_accident values (1,'SG20190101120000','a9b7e8',2,'第一教学楼','3','1000',0,0,'资产事故','注意规范使用设备','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_accident values (2,'SG20190101120005','e6f4d2',3,'第二教学楼','1','1500',0,0,'资产事故','注意规范使用设备','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_accident values (3,'SG20190101120010','c377d8',2,'第三教学楼','2','1586.2',0,0,'资产事故','注意规范使用设备','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_accident values (4,'SG20190101120015','5e6a1b',2,'图书馆','1','624.33',0,0,'资产事故','注意规范使用设备','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');

-- ----------------------------
-- 27、资产维修表
-- ----------------------------
drop table if exists assets_repair;
create table assets_repair(
    repair_id             int               not null auto_increment         comment '维修ID',
    repair_orderNum                 varchar(50)         not null default ''                         comment '维修单号',
    assets_number         varchar(15)       not null default ''             comment '资产编号',
    repair_userId                int               not null default 0              comment '申请人ID',
    repair_status       char(1)           not null default ''               comment '修复状态（1：正常，2：待报废，3：故障）',
    repair_grade          char(1)           not null default ''             comment '修复级别（1：部件维修，2：中修，3：大修）',
    repair_fees           varchar(10)       not null default ''             comment '维修费用',
    auditor_id            int(11)           NOT NULL DEFAULT '0'            COMMENT '审批者ID',
    status                char(1)           NOT NULL DEFAULT '0'            COMMENT '状态（0：审核中，1：审核通过，2：驳回）',
    remark       text                                              comment '故障描述',
    sent_time             timestamp         not null default current_timestamp      comment '送修日期',
    repair_time           timestamp         not null default current_timestamp      comment '维修日期',
    create_by               varchar(64)         default ''          comment '创建者',
    create_time              timestamp           not null default current_timestamp      comment '创建时间/登记时间',
    update_by               varchar(64)         default ''          comment '更新者',
    update_time              timestamp           not null default current_timestamp on update current_timestamp      comment '更新时间',
    primary key (repair_id)
)engine = innodb default charset utf8 comment '资产维修表';
-- ----------------------------
-- 初始化-设备维修信息
-- ----------------------------
insert into assets_repair values (1,'WX20190101120000','a9b7e8',2,'1','3','1000',0,0,'资产维修','2020-01-14 11:50:06','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_repair values (2,'WX20190101120005','e6f4d2',3,'2','1','1500',0,0,'资产维修','2020-01-14 11:50:06','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_repair values (3,'WX20190101120010','c377d8',2,'3','2','1586.2',0,0,'资产维修','2020-01-14 11:50:06','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_repair values (4,'WX20190101120015','5e6a1b',2,'2','1','624.33',0,0,'资产维修','2020-01-14 11:50:06','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');

-- ----------------------------
-- 28、资产转移表
-- ----------------------------
drop table if exists assets_transfer;
create table assets_transfer(
    transfer_id             int               not null auto_increment         comment '转移ID',
    transfer_orderNum                 varchar(50)         not null default ''                         comment '转移单号',
    assets_number         varchar(15)       not null default ''             comment '资产编号',
    transfer_userId       int               not null default 0              comment '负责人ID',
    original_addr         varchar(50)       not null default ''             comment '原存放地点',
    present_addr          varchar(50)       not null default ''             comment '现在存放地点',
    auditor_id            int(11)           NOT NULL DEFAULT '0'            COMMENT '审批者ID',
    status                char(1)           NOT NULL DEFAULT '0'            COMMENT '状态（0：审核中，1：审核通过，2：驳回）',
    remark                text                                              comment '备注',
    transfer_time         timestamp         not null default current_timestamp      comment '转移日期',
    create_by               varchar(64)         default ''          comment '创建者',
    create_time              timestamp           not null default current_timestamp      comment '创建时间/登记时间',
    update_by               varchar(64)         default ''          comment '更新者',
    update_time              timestamp           not null default current_timestamp on update current_timestamp      comment '更新时间',
    primary key (transfer_id)
)engine = innodb default charset utf8 comment '资产转移表';
-- ----------------------------
-- 初始化-设备转移信息
-- ----------------------------
insert into assets_transfer values (1,'ZY20190101120000','a9b7e8',2,'第二教学楼','第一教学楼',0,0,'资产转移','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_transfer values (2,'ZY20190101120005','e6f4d2',3,'第三教学楼','第二教学楼',0,0,'资产转移','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_transfer values (3,'ZY20190101120010','c377d8',2,'中巴软件园709','第三教学楼',0,0,'资产转移','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_transfer values (4,'ZY20190101120015','5e6a1b',2,'学生活动中心','图书馆',0,0,'资产转移','2020-01-14 11:50:06','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');

-- ----------------------------
-- 29、盘点任务表
-- ----------------------------
drop table if exists assets_check_task;
create table assets_check_task
(
    task_id             int                 not null auto_increment        comment '盘点id',
    check_number        varchar(20)         not null default ''            comment '盘点单号',
    check_userId        int                 not null default 0             comment '盘点负责人',
    check_addr          varchar(50)         not null default ''            comment '盘点地址',
    target_num          int                 not null default 0             comment '应盘数量',
    check_profit        int                                                comment '盘盈',
    check_loss          int                                                comment '盘亏',
    is_check            char(1)             not null default '0'           comment '是否盘点（0：未盘点 1：已盘点）',
    check_status        char(1)             not null default '0'           comment '已盘点后的任务状态（0：未开始 1：审核中 2：通过 3：驳回）',
    create_by           varchar(64)                  default ''                    comment '创建者',
    create_time         timestamp           not null default current_timestamp     comment '创建时间/登记时间',
    update_by           varchar(64)                  default ''                    comment '更新者',
    update_time         timestamp           not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (task_id)
)engine = innodb default charset utf8 comment '盘点任务表';
-- ----------------------------
-- 初始化-设备盘点信息
-- ----------------------------
insert into assets_check_task values (1,'PD20200209175100',2,'第一教学楼101教室',3,1,0,1,1,'admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_task values (2,'PD20200210132100',3,'中巴软件园708教室',3,0,0,1,2,'admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_task values (3,'PD20200211154100',2,'中巴软件园708教室',3,0,1,1,3,'admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_task values (4,'PD20200212111100',2,'中巴软件园708教室',3,1,0,1,2,'admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_task values (5,'PD20200213113100',2,'中巴软件园708教室',3,null,null,0,0,'admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');

-- ----------------------------
-- 30、盘点资产表
-- ----------------------------
drop table if exists assets_check_item;
create table assets_check_item(
      item_id           int                 not null auto_increment comment '盘点id',
      check_number      varchar(20)         not null default '' comment '盘点单号',
      assets_number     varchar(15)         not null default '' comment '资产编号',
      check_item_status   char(1)             not null default '0' comment '盘点状态（1：盘到 2：盘亏 3：盘盈）',
      create_by         varchar(64)                  default '' comment '创建者',
      create_time       timestamp           not null default current_timestamp comment '创建时间/登记时间',
      update_by         varchar(64)                  default '' comment '更新者',
      update_time       timestamp           not null default current_timestamp on update current_timestamp comment '更新时间',
      primary key (item_id)
)engine = innodb default charset utf8 comment '盘点资产表';
-- ----------------------------
-- 初始化-设备盘点信息
-- ----------------------------
insert into assets_check_item values (1,'PD20200209175100','a9b7e8','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (2,'PD20200209175100','uusoc31','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (3,'PD20200209175100','oedjaf30','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (4,'PD20200209175100','5e6a1b','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');

insert into assets_check_item values (5,'PD20200210132100','5e6a1b','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (6,'PD20200210132100','esdc73','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (7,'PD20200210132100','dd78aa3','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (8,'PD20200210132100','5e6a1b','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');

insert into assets_check_item values (9,'PD20200211154100','a9b7e8','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (10,'PD20200211154100','c377d8','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (11,'PD20200211154100','e6f4d2','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (12,'PD20200211154100','5e6a1b','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');

insert into assets_check_item values (13,'PD20200212111100','a9b7e8','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (14,'PD20200212111100','c377d8','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (15,'PD20200212111100','e6f4d2','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (16,'PD20200212111100','5e6a1b','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');

insert into assets_check_item values (17,'PD20200213113100','a9b7e8','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (18,'PD20200213113100','c377d8','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (19,'PD20200213113100','e6f4d2','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_check_item values (20,'PD20200213113100','5e6a1b','0','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');

-- ----------------------------
-- 31、资产存放地址表
-- ----------------------------
drop table if exists assets_storage_addr;
create table assets_storage_addr(
    addr_id           int                 not null auto_increment    comment '地点id',
    addr_no           int                 not null default 0         comment '地址编号',
    addr_name         varchar(50)         not null default ''         comment '地址名称',
    create_by         varchar(64)          default '' comment '创建者',
    create_time       timestamp   not null default current_timestamp comment '创建时间/登记时间',
    update_by         varchar(64)          default '' comment '更新者',
    update_time       timestamp   not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (addr_id)
)engine = innodb default charset utf8 comment '资产存放地址表';
-- ----------------------------
-- 初始化-资产存放地址表
-- ----------------------------
insert into assets_storage_addr values (1,10000,'第一教学楼101教室','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_storage_addr values (2,10001,'第二教学楼102教室','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_storage_addr values (3,10002,'第三教学楼103教室','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_storage_addr values (4,10003,'福慧图书馆电子阅览室','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_storage_addr values (5,10004,'中巴软件园708教室','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_storage_addr values (6,10005,'中巴软件园709教室','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_storage_addr values (7,10006,'学生活动中心','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_storage_addr values (8,10007,'学术大厅','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_storage_addr values (9,10008,'行政楼106室','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_storage_addr values (10,10009,'实验大楼305室','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_storage_addr values (11,10010,'实验大楼306室','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_storage_addr values (12,10011,'实验大楼411室','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');

-- ----------------------------
-- 33、资产待报废表
-- ----------------------------
drop table if exists assets_alarm;
create table assets_alarm(
     alarm_id          int                 not null auto_increment    comment '报废id',
     assets_number           varchar(15)         not null default ''         comment '资产编号',
     reason                  char(1)         not null default ''         comment '待报废原因（1：事故 2：维修无效）',
     create_by         varchar(64)          default '' comment '创建者',
     create_time       timestamp   not null default current_timestamp comment '创建时间/登记时间',
     update_by         varchar(64)          default '' comment '更新者',
     update_time       timestamp   not null default current_timestamp on update current_timestamp comment '更新时间',
     primary key (alarm_id)
)engine = innodb default charset utf8 comment '资产待报废表';
-- ----------------------------
-- 初始化-资产存放地址表
-- ----------------------------
insert into assets_alarm values (1,'5e6a1b','1','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_alarm values (2,'oodsaf23','2','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_alarm values (3,'dd78aa7','1','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');
insert into assets_alarm values (4,'ccvdaa6','2','admin','2020-01-14 11:50:06','','2020-01-14 11:50:08');

