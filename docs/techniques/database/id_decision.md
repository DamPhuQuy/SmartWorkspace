# Lựa chọn kiểu cho id

## Nên chọn INT / BIGINT khi:

Hệ thống Monument/Tập trung (Monolithic): Chỉ có một database duy nhất xử lý chính.

Ưu tiên hiệu năng và dung lượng: Các bảng chứa lượng dữ liệu cực lớn, tần suất ghi/đọc cao (ví dụ: bảng Log, bảng lịch sử giao dịch).

Bảng nội bộ (Internal): Các bảng cấu hình, bảng danh mục phụ (Ví dụ: categories, roles, status) – nơi dữ liệu không bao giờ cần lộ ra ngoài URL cho người dùng thấy.

## Nên chọn UUID khi:

Hệ thống phân tán hoặc Microservices: Dữ liệu được tạo ra từ nhiều dịch vụ khác nhau hoặc cần đồng bộ từ client (ví dụ: App Mobile sinh ID trước khi gửi lên Server).

Bảo mật thông tin kinh doanh: Dùng làm ID cho các thực thể nhạy cảm public ra ngoài như User_ID, Order_ID, Invoice_ID.

Tránh Data Migration Pain: Khi bạn cần gộp dữ liệu từ nhiều DB của các chi nhánh lại với nhau, dùng UUID sẽ không bao giờ lo bị xung đột (trùng) khóa chính.

## Giải pháp hiện đại:

### Dùng cả hai:

- Không dùng 100% UUID.
- Cần tối ưu: BIGINT AUTO_INCREMENT để tối ưu các câu lệnh JOIN và Index nội bộ giữa các bảng.

### Sử dụng UUID v7 (Time-based UUID):

Nếu bắt buộc dùng UUID làm khóa chính, hãy chọn UUID v7. Loại UUID này có cấu trúc chứa Timestamp ở các byte đầu tiên, giúp các ID sinh ra sau luôn lớn hơn ID sinh ra trước (có tính tuần tự). Nó giải quyết được bài toán tụt hiệu năng Index của UUID v4 truyền thống mà vẫn giữ được tính độc nhất và bảo mật.
