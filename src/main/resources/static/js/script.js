var navbar = document.getElementById("navbar");
var leftThreshold = 50; // Giá trị ngưỡng từ phía bên trái để hiển thị navbar
var isHovered = false; // Biến xác định trạng thái con trỏ chuột trên navbar

navbar.addEventListener("mouseenter", function() {
  isHovered = true;
  navbar.style.left = "0";
});

navbar.addEventListener("mouseleave", function() {
  isHovered = false;
  setTimeout(function() {
    if (!isHovered) {
      navbar.style.left = "-20%"; // Ẩn navbar nếu con trỏ chuột không còn trên navbar sau 1 khoảng thời gian
    }
  }, 500); // Thời gian chờ 500ms trước khi ẩn navbar
});

document.addEventListener("mousemove", function(event) {
  var x = event.clientX;

  if (x <= leftThreshold) {
    navbar.style.left = "0";
  } else if (!isHovered) {
    navbar.style.left = "-20%"; // Ẩn navbar nếu con trỏ chuột không nằm trong navbar và vượt qua ngưỡng
  }
});
