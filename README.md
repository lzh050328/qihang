# 启航招生 CRM（qihang）

面向 AI 教育培训机构的销售/招生管理系统，围绕「线索 → 商机 → 客户」的转化主线，覆盖课程、活动、员工、部门、角色与操作日志管理。

## 技术栈

| 类别 | 技术 |
| --- | --- |
| 基础框架 | Java 24、Spring Boot 3.4.8 |
| 数据层 | MySQL 8、MyBatis-Plus 3.5.17 |
| 认证 | JWT（jjwt 0.9.1）、自定义拦截器 + ThreadLocal |
| 切面 | Spring AOP（操作日志） |
| 文件存储 | 阿里云 OSS |
| 工具 | Hutool 5.8、Lombok |

## 模块说明

| 模块 | 职责 |
| --- | --- |
| `qihang-common` | 统一响应 `Result`、分页结构、`DataHolder`（ThreadLocal）、JWT 工具、OSS 上传 |
| `qihang-entity` | 11 个实体、9 个 DTO、2 个 VO、自定义时间反序列化器 |
| `qihang-management` | Web 层与业务实现：12 个 Controller、12 个 Service、11 个 Mapper、AOP 日志、全局异常 |

## 核心实现

### 1. 线索/商机状态机

- 线索：待分配 → 待跟进 → 跟进中 → 伪线索 / 转商机
- 商机：待分配 → 待跟进 → 跟进中 → 回收 / 转客户
- 支持公海池领取、踢回公海、跟进记录落库，状态流转配合事务控制

### 2. 自研 JWT 认证与上下文传递

`LoginInterceptor` 解析请求头 token 写入 `DataHolder`（ThreadLocal），`afterCompletion` 中清理，避免 Tomcat 线程复用导致用户串号；跟进人等身份直接取自上下文，不依赖请求体。

### 3. AOP 操作日志

环绕通知匹配 `*Controller.add*/update*/delete*`，记录操作人、类名、方法名、参数、返回值与耗时，落库 `operate_log`。

### 4. 并发去重与动态 SQL

- 新增线索先查重给出友好提示，并捕获 `DuplicateKeyException` 作为唯一索引的并发兜底
- Mapper XML 使用 `resultMap + collection` 将跟进记录映射为集合，多表 left join 带出展示字段

### 5. 首页数据看板

按状态统计线索与商机共 12 项指标，供销售主管查看转化漏斗。

## 项目结构

```
qihang
├── qihang-common        # 公共组件
├── qihang-entity        # 实体 / DTO / VO
├── qihang-management    # Web 与业务实现（启动模块）
├── db-seed-business.sql # 商机/线索联调种子数据
├── db-avatar-backup.sql # 头像地址备份
└── pom.xml              # 父 POM
```

## 本地启动

### 1. 环境准备

- JDK 24、Maven 3.8+、MySQL 8

### 2. 初始化数据库

```bash
mysql -u root -p -e "CREATE DATABASE qk DEFAULT CHARSET utf8mb4;"
mysql -u root -p qk < db-seed-business.sql
```

> 建表 DDL 请按 `qihang-entity` 中的实体字段自行生成，或将现有库导出后导入。

### 3. 配置环境变量

| 变量 | 说明 |
| --- | --- |
| `MYSQL_HOST` / `MYSQL_PORT` / `MYSQL_DB` | 数据库地址与库名 |
| `MYSQL_USERNAME` / `MYSQL_PASSWORD` | 数据库账号 |
| `OSS_BUCKET_NAME` / `OSS_ENDPOINT` / `OSS_REGION` | 阿里云 OSS |
| `JWT_SECRET` | JWT 签名密钥，生产环境必须修改 |

### 4. 启动

```bash
mvn clean package -DskipTests
java -jar qihang-management/target/qihang-management.jar
```

## 说明

- JDK 23+ 默认关闭隐式注解处理，父 POM 已显式配置 Lombok 处理器（`maven-compiler-plugin`），否则编译会报「找不到 getter/setter 符号」。
- 本项目为学习与工程实践用途，种子数据不含真实客户信息。
