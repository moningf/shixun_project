# 📚 教材征订与发放管理系统

教材征订与发放管理系统，支持**管理员 / 教师 / 班长**三种角色协同工作，覆盖教材管理、在线预订、采购入库、订单处理全流程。

---

## 🧱 技术栈

| 层 | 技术 |
|---|------|
| 前端 | Vue 3 + Vite + Element Plus + Pinia + Axios |
| 后端 | Spring Boot 2.7 + Spring Data JPA + MySQL |
| 认证 | JWT（拦截器 + ThreadLocal 上下文） |

## 🚀 快速开始

### 环境要求

- JDK 8+
- Maven 3.6+
- Node.js 18+
- MySQL 8.0

### 1. 初始化数据库

```bash
mysql -u root -p --default-character-set=utf8mb4 < ylsm_database.sql
```

> Docker 用户可直接挂载 SQL 文件到 `/docker-entrypoint-initdb.d/`，容器首次启动自动执行。

### 2. 配置数据库连接

编辑 `backend/src/main/resources/application.yml`，修改数据库地址：

```yaml
spring:
  datasource:
    url: jdbc:mysql://你的IP:3306/ylsm?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false
    username: root
    password: 123456
```

### 3. 启动后端

```bash
cd backend
mvn spring-boot:run
# 启动在 http://localhost:8080
```

### 4. 启动前端

```bash
cd frontend
npm install
npm run dev
# 启动在 http://localhost:5173
```

浏览器打开 `http://localhost:5173` 即可使用。

## 👥 测试账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | `admin001` | `123456` |
| 教师 | `T001` | `123456` |
| 教师 | `T003` | `123456` |
| 班长 | `2021001001` | `123456` |
| 班长 | `2022001001` | `123456` |

> 所有用户密码均为 `123456`。登录时系统自动识别角色，无需手动选择。

## 📄 License

MIT
