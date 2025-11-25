# Hướng Dẫn Hoàn Thành Tái Cấu Trúc

## 🎯 BƯỚC CÒN LẠI CẦN THỰC HIỆN

### BƯỚC 1: Di chuyển controllers từ `examination-system-app` sang modules tương ứng

#### A. Controllers cho module `examination-system-exam`:

**Từ**: `examination-system-app/src/main/java/com/examination_system/controller/`  
**Đến**: `examination-system-exam/src/main/java/com/examination_system/exam/controller/`

1. **Admin controllers**:

   ```
   controller/admin/QuestionController.java
   → exam/controller/admin/QuestionController.java
   (Thay package: com.examination_system.controller.admin → com.examination_system.exam.controller.admin)
   (Thay import service: com.examination_system.service.exam.QuestionService → com.examination_system.exam.service.QuestionService)

   controller/admin/SubjectController.java
   → exam/controller/admin/SubjectController.java

   controller/admin/MajorController.java
   → exam/controller/admin/MajorController.java

   controller/admin/StudentExamController.java
   → exam/controller/admin/StudentExamController.java

   controller/admin/UserExamHistoryController.java
   → exam/controller/admin/UserExamHistoryController.java
   ```

2. **Student controllers**:

   ```
   controller/student/DoExamController.java
   → exam/controller/student/DoExamController.java
   (Thay package: com.examination_system.controller.student → com.examination_system.exam.controller.student)
   (Thay import service: com.examination_system.service.exam.* → com.examination_system.exam.service.*)

   controller/student/UserExamController.java
   → exam/controller/student/UserExamController.java
   ```

**Cách làm**: Copy file từ app sang exam, thay đổi:

- Package declaration
- Import statements cho services (từ `com.examination_system.service.exam.*` sang `com.examination_system.exam.service.*`)

---

### BƯỚC 2: Xóa files trùng lặp và không cần thiết

#### A. Trong `examination-system-common`:

```bash
# Xóa toàn bộ thư mục service (đã di chuyển sang auth/exam)
rm -rf examination-system-common/src/main/java/com/examination_system/service/

# Xóa config security (đã có trong app)
rm -rf examination-system-common/src/main/java/com/examination_system/config/security/

# Xóa exception handler duplicate (đã có trong core)
rm examination-system-common/src/main/java/com/examination_system/exception/GlobalExceptionHandler.java

# Xóa JPA config duplicate (đã có trong core)
rm -rf examination-system-common/src/main/java/com/examination_system/config/jpa/

# Xóa DataInitializer (để trong app)
rm examination-system-common/src/main/java/com/examination_system/config/DataInitializer.java
```

#### B. Trong `examination-system-app`:

```bash
# Xóa controllers (đã di chuyển sang auth/exam)
rm -rf examination-system-app/src/main/java/com/examination_system/controller/

# Xóa services duplicate (đã di chuyển sang auth/exam)
rm -rf examination-system-app/src/main/java/com/examination_system/service/

# Xóa exception handler duplicate (đã có trong core)
rm examination-system-app/src/main/java/com/examination_system/exception/GlobalExceptionHandler.java

# Xóa model entities duplicate (đã có trong common)
rm -rf examination-system-app/src/main/java/com/examination_system/model/
```

#### C. Trong `examination-system-core`:

```bash
# Xóa JPA config cũ (đã tạo mới trong core với package đúng)
rm -rf examination-system-core/src/main/java/com/examination_system/config/
```

---

### BƯỚC 3: Cập nhật imports trong các file còn lại

#### A. Trong `examination-system-app/config/security/`:

**File: SecurityConfig.java**

- Thay: `com.examination_system.service.security.MyUserDetailService`
- Thành: `com.examination_system.auth.service.MyUserDetailService`

- Thay: `com.examination_system.service.security.JwtService`
- Thành: `com.examination_system.core.security.JwtService`

**File: JwtFilter.java**

- Thay: `com.examination_system.service.security.JwtService`
- Thành: `com.examination_system.core.security.JwtService`

- Thay: `com.examination_system.service.security.MyUserDetailService`
- Thành: `com.examination_system.auth.service.MyUserDetailService`

#### B. Trong `examination-system-common/model/entity/`:

Tất cả entities extend BaseEntity hoặc BaseLog cần cập nhật import:

- Thay: `com.examination_system.model.entity.BaseEntity`
- Thành: `com.examination_system.core.model.entity.BaseEntity`

- Thay: `com.examination_system.model.entity.BaseLog`
- Thành: `com.examination_system.core.model.entity.BaseLog`

**Files cần sửa**:

```
common/src/main/java/com/examination_system/model/entity/user/User.java
common/src/main/java/com/examination_system/model/entity/user/AuthInfo.java
common/src/main/java/com/examination_system/model/entity/exam/Exam.java
common/src/main/java/com/examination_system/model/entity/exam/Question.java
common/src/main/java/com/examination_system/model/entity/exam/QuestionOption.java
common/src/main/java/com/examination_system/model/entity/exam/Subject.java
common/src/main/java/com/examination_system/model/entity/exam/Chapter.java
common/src/main/java/com/examination_system/model/entity/exam/Major.java
common/src/main/java/com/examination_system/model/entity/exam/student/StudentExam.java
common/src/main/java/com/examination_system/model/entity/log/ExamLog.java
common/src/main/java/com/examination_system/model/entity/log/BanLog.java
```

#### C. Trong `examination-system-common/repository/`:

Nếu có repository extend SoftDeleteRepository:

- Thay: `com.examination_system.repository.SoftDeleteRepository`
- Thành: `com.examination_system.core.repository.SoftDeleteRepository`

---

### BƯỚC 4: Build và test

```bash
# Build từ root
cd SpringExaminationSystem
mvn clean install

# Nếu có lỗi compile, kiểm tra:
# 1. Tất cả imports đã được cập nhật đúng chưa
# 2. Package declarations đã đúng chưa
# 3. pom.xml dependencies đã đúng thứ tự chưa
```

---

### BƯỚC 5: Cập nhật Component Scan (nếu cần)

Trong `examination-system-app/SpringExaminationSystemApplication.java`, thêm:

```java
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.examination_system",           // App config
    "com.examination_system.core",      // Core beans
    "com.examination_system.auth",      // Auth controllers & services
    "com.examination_system.exam"       // Exam controllers & services
})
@EntityScan(basePackages = "com.examination_system.model.entity")
@EnableJpaRepositories(basePackages = "com.examination_system.repository")
public class SpringExaminationSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringExaminationSystemApplication.class, args);
    }
}
```

---

## 📋 CHECKLIST Hoàn Thành

- [ ] ✅ Core module đã tạo với BaseEntity, BaseLog, JwtService, Exception handler
- [ ] ✅ Common module đã cập nhật pom.xml (chỉ có entity, repo, dto, mapper)
- [ ] ✅ Auth module đã tạo với AuthController, UserController, services
- [ ] ✅ Exam module đã tạo với các services (QuestionService, SubjectService, etc.)
- [ ] ⏳ **CẦN LÀM**: Di chuyển tất cả exam controllers từ app sang exam module
- [ ] ⏳ **CẦN LÀM**: Xóa files trùng lặp trong common và app
- [ ] ⏳ **CẦN LÀM**: Cập nhật imports trong SecurityConfig và JwtFilter
- [ ] ⏳ **CẦN LÀM**: Cập nhật imports BaseEntity/BaseLog trong tất cả entities
- [ ] ⏳ **CẦN LÀM**: Build và test toàn bộ project

---

## 🔧 Commands Nhanh

```bash
# Xem structure
tree -L 3 -I 'target|test'

# Build
mvn clean install -DskipTests

# Run
cd examination-system-app && mvn spring-boot:run

# Check dependencies
mvn dependency:tree
```

---

## 📞 Nếu Gặp Lỗi

### Lỗi "Cannot find symbol"

→ Kiểm tra imports và package declarations

### Lỗi "Missing artifact"

→ Build modules theo thứ tự: core → common → auth/exam → app

### Lỗi "No bean found"

→ Kiểm tra @ComponentScan trong SpringBootApplication

### Lỗi "Circular dependency"

→ Kiểm tra lại dependencies trong pom.xml, đảm bảo không có vòng lặp

---

**Lưu ý**: Do giới hạn về tool, tôi đã tạo cấu trúc cơ bản và hướng dẫn chi tiết.
Bạn cần thực hiện các bước còn lại theo checklist trên để hoàn thành việc tái cấu trúc.
