# north-rend

这是一个包含前端（Vite + Vue 3）和后端（Spring Boot）的多模块 Maven 项目。

主要目标：
- 本地开发前端（vite dev server）
- 本地开发后端（Spring Boot）
- 使用 Maven 一次性构建（前端会被构建并复制到后端的静态资源目录）

项目技术栈
- 后端：Java + Spring Boot (parent: spring-boot-starter-parent)
- 前端：Vue 3 + Vite + TypeScript
- 构建工具：Maven (项目包含 Maven Wrapper `mvnw` / `mvnw.cmd`)

先决条件
- JDK 25
- Maven（可选，项目自带 Maven Wrapper，可直接使用 `mvnw` / `mvnw.cmd`）
- Node / pnpm（用于前端本地开发；使用 Maven 构建时，frontend-maven-plugin 会自动安装 Node + pnpm）

目录结构（重要子目录）
- `frontend/` — 前端源代码（Vite + Vue），包含 `package.json` 和 `pnpm-lock.yaml`
- `backend/` — Spring Boot 后端代码，构建后会把 `frontend/dist` 中的静态文件复制到 `backend/src/main/resources/static`

常用命令（Windows PowerShell）

1) 在本地开发模式单独启动前端（热重载）

```powershell
cd frontend
pnpm install       # 或 npm install
pnpm run dev       # 启动 Vite 开发服务器（默认端口 5173）
```

2) 在本地开发模式单独启动后端（Spring Boot）

```powershell
cd ..\backend
.\mvnw.cmd -pl backend spring-boot:run    # 如果你在项目根目录，可直接使用 .\mvnw.cmd -pl backend spring-boot:run
# 或（使用本地 maven）
mvn -pl backend spring-boot:run
```

3) 一次性构建整个项目（包括前端构建并复制到后端静态目录），并跳过测试以加快速度

```powershell
# 在项目根目录（包含 mvnw/mvnw.cmd）运行
.\mvnw.cmd -DskipTests package
# 或（如果你使用本地 Maven）
mvn -DskipTests package
```

构建完成后，后端可执行 jar 位于 `backend/target/` 目录内：

```powershell
java -jar .\backend\target\*.jar
```

4) 只构建前端产物

```powershell
cd frontend
pnpm install
pnpm run build
# 构建结果在 frontend/dist 下
```

故障排查 / 注意事项
- 如果你没有安装 pnpm，本项目的 `frontend/pom.xml` 已配置 `frontend-maven-plugin`，在使用 Maven 构建时会自动下载并使用指定版本的 Node + pnpm（无需全局安装）。
- 如果端口冲突：Vite 默认 5173，Spring Boot 默认 8080。可以修改前端 `vite` 的配置或 Spring Boot 的 `application.yaml` 来调整端口。
- 若构建时资源复制不到后端的 static 文件夹，请确认 `frontend/dist` 已成功生成并且 Maven 有读取父项目目录的权限。

贡献
- 欢迎提交 issue / PR。请在 PR 中说明变更目的并包含必要的复现步骤。

联系方式
- 项目维护者信息请查看代码仓库的 CONTRIBUTORS / Git 历史或在仓库主页中查找维护者联系信息。

License
- 项目目前未在仓库中明确声明许可证，请在发布前补充 LICENSE 文件。

