
    var ApigetUser = "/honeyshop/api/users";
    var ApideleteUser ="/honeyshop/api/users/"
    $(document).ready(function() {
    loadUser();
    $("#addUser").click(function () {
    resetForm(); // Reset form khi thêm mới người dùng
    $('#addUserPanel').show();
});

    // Cập nhật form submit để xử lý cả insert và update
    $("#form_create_user").submit(function (event) {
    event.preventDefault();
    saveUser();
});
});
    function resetForm() {
    $('#form_create_user')[0].reset();
    $('#email').prop('disabled', false);
    $('#save').data('user-id', null);
    $('#save').data('old-password', null);
    $('#roles').prop('disabled', true); // Vô hiệu hóa trường roles
    $('#chon-anh').empty();
    $('#img').val('');  // Xóa ảnh đã chọn trong input file
}


    $(document).ready(function() {
    loadUser();

    $("#addUser").click(function () {
    $('#form-title').text('Thêm tài khoản');
    resetForm(); // Reset form khi thêm mới người dùng
    $('#addUserPanel').show();
});

    // Cập nhật form submit để xử lý cả insert và update
    $("#form_create_user").submit(function (event) {
    event.preventDefault();
    saveUser();
});

    // Đóng panel
    $("#closePanelBtn").click(function () {
    $('#addUserPanel').hide();
});
});

    function resetForm() {
    $('#form_create_user')[0].reset();
    $('#email').prop('disabled', false);
    $('#save').data('user-id', null);
    $('#save').data('old-password', null);
    $('#roles').prop('disabled', true); // Vô hiệu hóa trường roles
    $('#chon-anh').empty();
    $('#img').val('');  // Xóa ảnh đã chọn trong input file
}

    function loadUser() {
    // Xóa DataTable cũ nếu tồn tại
    if ($.fn.DataTable.isDataTable('#dataTable')) {
    $('#dataTable').DataTable().destroy();
    $('#userTableBody').empty();
}
    $('#dataTable').DataTable({
    "ajax": {
    "url": ApigetUser,
    "type": "GET",
    "dataType": "json",
    "dataSrc": function(json) {
    // Log dữ liệu để kiểm tra
    console.log("Dữ liệu phản hồi từ API:", json);

    // Kiểm tra cấu trúc dữ liệu trả về
    if (!json.data || !Array.isArray(json.data)) {
    console.error("Dữ liệu API không có thuộc tính 'data' hoặc không phải là một mảng.");
    return [];
}

    let users = json.data;

    // Xử lý dữ liệu cho DataTable
    return users.map(function(user, index) {
    var roleName = Array.isArray(user.roles) && user.roles.length > 0 ? user.roles[0].name : "N/A";
    if (roleName === "STAFF") {
    roleName = "Nhân viên";
} else if (roleName === "CUSTOMER") {
    roleName = "Khách hàng";
}
    else  roleName = "ADMIN";
    return [
    index + 1,
    user.fullname,
    `<img src="/honeyshop/images/${user.thumbnail}" alt="Avatar" width="60" height="60">`,
    user.gender ? 'Nam' : 'Nữ',
    roleName,
    `<button type="button" class="btn btn-info" onclick="editUser('${user.id}')"><i class="fa fa-edit"></i></button>
                                 <button type="button" class="btn btn-danger" data-toggle="modal" onclick="removeUser('${user.id}')"><i class="fa fa-trash"></button>` // Actions
    ];
});
},
    error: function(xhr, status, error) {
    console.error("Có lỗi xảy ra khi gọi API:", status, error);
}
},
    "columns": [
{ "title": "STT" },
{ "title": "Họ và tên" },
{ "title": "Avatar" },
{ "title": "Giới tính" },
{ "title": "Vai trò" },
{ "title": "Thao tác" }
    ],
    processing: true,
    serverSide: false,
    paging: true,
    searching: true,
    ordering: true,
    info: true
});
}

    function removeUser(id) {
    console.log(`removeUser function called with ID ${id}`);
    swal({
    title: "Bạn có chắc muốn xóa không?",
    text: "",
    icon: "warning",
    buttons: true,
    dangerMode: true,
})
    .then((willDelete) => {
    if (willDelete) {
    $.ajax({
    url: ApideleteUser + id,
    method: "DELETE",
    contentType: "application/json",
    dataType: "json",
    // headers: {
    //     'Authorization': 'Bearer ' + getToken()
    // },
    success: function () {
    $("#userrow_" + id).remove();
    swal("Xóa người dùng thành công!", {
    icon: "success",
});
},
    error: function () {
    swal("Xóa người dùng thất bại!", {
    icon: "error",
});
}
})
}
});
}


    function editUser(id) {
    $.ajax({
        url: "/honeyshop/api/users/" + id,
        method: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function(response) {
            let user = response.data;

            $('#fullname').val(user.fullname);
            $('#email').val(user.email).prop('disabled', true);
            $('#phone').val(user.phone);
            $('#address').val(user.address);
            $('#birthday').val(user.dob);
            $('input[name="gender"][value="' + (user.gender ? 'true' : 'false') + '"]').prop('checked', true);
            $('#roles').val(user.roles[0].name);
            $('#roles').prop('disabled', false);
            if (user.thumbnail) {
                $('#chon-anh').html(`<img src="/honeyshop/images/${user.thumbnail}" alt="Avatar" width="100" height="100">`); // Hiển thị ảnh nếu có
            } else {
                $('#chon-anh').empty(); // Xóa ảnh nếu không có
            }

            $('#save').data('user-id', id);
            $('#save').data('old-password', user.password);

            // Thay đổi tiêu đề thành "Cập nhật tài khoản"
            $('#form-title').text('Cập nhật tài khoản');

            $('#addUserPanel').show(); // Hiển thị form nếu nó bị ẩn
        },
        error: function(error) {
            console.error("Có lỗi xảy ra khi tải dữ liệu người dùng: ", error);
        }
    });
}
    function saveUser() {
    let formData = new FormData(document.getElementById('form_create_user'));
    let user = {
    fullname: $("#fullname").val(),
    // email: $("#email").val(),
    password:$('#password').val(),
    phone: $("#phone").val(),
    dob: $("#birthday").val(),
    address: $("#address").val(),
    gender: $('input[name="gender"]:checked').val(),
    roles: $('#roles').val()
};

    if (user.roles && !Array.isArray(user.roles)) {
    user.roles = [user.roles];
}

    // Thêm email nếu là thêm mới người dùng
    let userId = $('#save').data('user-id');
    if (!userId) {
    user.email = $("#email").val();
}

    formData.append('data', new Blob([JSON.stringify(user)], { type: 'application/json' }));

    let fileInput = document.getElementById('img');
    if (fileInput.files.length > 0) {
    formData.append('img', fileInput.files[0]);
}

    if (userId) {
    $.ajax({
    url: ApideleteUser + userId,
    method: "PUT",
    data: formData,

    processData: false, // Prevent jQuery from automatically transforming the data into a query string
    contentType: false,  // Chuyển đổi dữ liệu người dùng thành JSON
    success: function(response) {
    swal("Cập nhật người dùng thành công", "", "success");
    loadUser();
},
    error: function(e) {
    console.error("Lỗi khi cập nhật người dùng: ", e);
    swal("Cập nhật người dùng thất bại", "", "error");
}
});
} else {
    $.ajax({
    url: ApigetUser,
    method: "POST",
    data: formData,
    processData: false,
    contentType: false,
    success: function(response) {
    swal("Thêm người dùng thành công", "", "success");
    $("#form_create_user")[0].reset();
    loadUser();
},
    error: function(xhr, status, error) {
    console.error("Lỗi khi thêm người dùng: ", {
    status: status,
    error: error,
    responseText: xhr.responseText,
    responseJSON: xhr.responseJSON
});
    swal("Thêm người dùng thất bại", `Lỗi: ${xhr.responseJSON.message}`, "error");
}
});
}
}
