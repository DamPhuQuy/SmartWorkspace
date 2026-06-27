# Cascade and `orphanRemoval` in JPA/Hibernate

## 1. Cascade là gì?

`cascade` dùng để nói với JPA rằng: khi thực hiện một thao tác trên entity cha, thao tác đó có được tự động áp dụng xuống entity con hay không.

Ví dụ:

```java
@OneToOne(
    mappedBy = "user",
    cascade = CascadeType.ALL
)
private UserProfileEntity profile;
```

Nếu `UserEntity` là entity cha và `UserProfileEntity` là entity con, thì khi lưu, cập nhật hoặc xóa `UserEntity`, JPA có thể tự động áp dụng thao tác tương ứng cho `UserProfileEntity`.

## 2. Các loại Cascade phổ biến

### `CascadeType.PERSIST`

Khi lưu entity cha, entity con cũng được lưu theo.

```java
user.setProfile(profile);
userRepository.save(user);
```

Nếu có `CascadeType.PERSIST`, `profile` cũng được insert vào database.

---

### `CascadeType.MERGE`

Khi cập nhật entity cha, entity con cũng được cập nhật theo.

```java
user.setEmail("new@gmail.com");
user.getProfile().setFullName("New Name");
userRepository.save(user);
```

Nếu có `CascadeType.MERGE`, thay đổi của `profile` cũng được merge.

---

### `CascadeType.REMOVE`

Khi xóa entity cha, entity con cũng bị xóa theo.

```java
userRepository.delete(user);
```

Nếu có `CascadeType.REMOVE`, `profile` cũng bị delete.

---

### `CascadeType.ALL`

Bao gồm tất cả các cascade chính:

```java
CascadeType.ALL
```

Tương đương với:

```java
PERSIST, MERGE, REMOVE, REFRESH, DETACH
```

Thường dùng khi vòng đời của entity con phụ thuộc hoàn toàn vào entity cha.

## 3. `orphanRemoval` là gì?

`orphanRemoval = true` nghĩa là: nếu entity con bị tách khỏi entity cha, nó sẽ bị xóa khỏi database.

Ví dụ:

```java
@OneToOne(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
)
private UserProfileEntity profile;
```

Khi làm:

```java
user.setProfile(null);
```

`UserProfileEntity` cũ sẽ trở thành “orphan” — tức là không còn thuộc về `UserEntity` nào nữa. Khi đó JPA sẽ tự động xóa nó khỏi database.

## 4. Khác nhau giữa `CascadeType.REMOVE` và `orphanRemoval`

### `CascadeType.REMOVE`

Xóa entity con khi entity cha bị xóa.

```java
userRepository.delete(user);
```

Kết quả:

```text
Xóa user
→ Xóa luôn profile
```

### `orphanRemoval = true`

Xóa entity con khi nó bị gỡ khỏi quan hệ với entity cha.

```java
user.setProfile(null);
```

Kết quả:

```text
User vẫn còn
Profile bị xóa
```

## 5. Ví dụ dễ hiểu

Giả sử có quan hệ:

```text
User 1 - 1 UserProfile
```

`UserProfile` không có ý nghĩa nếu không có `User`.

Khi đó có thể cấu hình:

```java
@OneToOne(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
)
private UserProfileEntity profile;
```

Ý nghĩa:

```text
Lưu User → lưu luôn Profile
Cập nhật User → cập nhật luôn Profile
Xóa User → xóa luôn Profile
Gỡ Profile khỏi User → xóa Profile khỏi database
```

## 6. Khi nào nên dùng `cascade`?

Nên dùng `cascade` khi entity con phụ thuộc vòng đời vào entity cha.

Ví dụ nên dùng:

```text
User - UserProfile
Order - OrderItem
Post - Comment
```

Vì các entity con thường không tồn tại độc lập nếu không có entity cha.

Không nên dùng bừa bãi với các quan hệ độc lập.

Ví dụ cần cẩn thận:

```text
User - Role
Product - Category
Student - Course
```

Vì `Role`, `Category`, `Course` có thể được nhiều entity khác dùng chung. Nếu cascade remove sai, có thể xóa nhầm dữ liệu quan trọng.

## 7. Khi nào nên dùng `orphanRemoval`?

Nên dùng khi entity con không được phép tồn tại nếu không còn thuộc về entity cha.

Ví dụ:

```text
OrderItem không nên tồn tại nếu không thuộc Order
UserProfile không nên tồn tại nếu không thuộc User
Comment có thể bị xóa nếu bị gỡ khỏi Post
```

Không nên dùng nếu entity con vẫn có ý nghĩa độc lập.

## 8. Quy tắc nhớ nhanh

```text
cascade = thao tác trên cha lan xuống con

orphanRemoval = con bị gỡ khỏi cha thì bị xóa khỏi database
```

Hoặc dễ nhớ hơn:

```text
CascadeType.REMOVE:
Cha chết → con chết

orphanRemoval = true:
Con bị bỏ rơi → con bị xóa
```

## 9. Ví dụ với `@OneToMany`

```java
@OneToMany(
    mappedBy = "order",
    cascade = CascadeType.ALL,
    orphanRemoval = true
)
private List<OrderItemEntity> items = new ArrayList<>();
```

Khi thêm item:

```java
order.getItems().add(item);
item.setOrder(order);
```

Khi xóa item khỏi list:

```java
order.getItems().remove(item);
```

Nếu `orphanRemoval = true`, item đó sẽ bị delete khỏi database.

## 10. Kết luận

`cascade` và `orphanRemoval` đều liên quan đến vòng đời của entity, nhưng chúng giải quyết hai vấn đề khác nhau.

`cascade` quyết định thao tác trên entity cha có lan xuống entity con hay không.

`orphanRemoval` quyết định entity con có bị xóa khi không còn được entity cha tham chiếu hay không.

Trong thực tế, với các quan hệ phụ thuộc mạnh như `User - UserProfile`, `Order - OrderItem`, thường có thể dùng:

```java
cascade = CascadeType.ALL,
orphanRemoval = true
```

Nhưng với các entity dùng chung như `Role`, `Category`, `Course`, cần tránh dùng `CascadeType.REMOVE` hoặc `CascadeType.ALL` nếu không chắc chắn.
