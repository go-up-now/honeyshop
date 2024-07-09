$(document).ready(function() {
    // Nếu DataTable đã được khởi tạo, hãy hủy khởi tạo trước khi tiếp tục
    if ($.fn.DataTable.isDataTable('#dataTable')) {
        $('#dataTable').DataTable().clear().destroy();
    }

    // Khởi tạo DataTable
    const table = $('#dataTable').DataTable({
        "ajax": {
            "url": "/honeyshop/api/report", // URL của API để lấy dữ liệu
            "type": "GET",
            "dataSrc": function (json) {
                let totalRevenue = 0;

                const data = json.map((item, index) => {
                    totalRevenue += item.revenue;
                    return [
                        index + 1, // STT
                        item.userFullName,
                        item.phone,
                        // formatCurrency(item.revenue),
                        item.status,
                        item.nameEmployee,
                        formatDate(item.day),
                        `<button type="button" class="btn btn-info" onclick="editOrder('${item.id}')"><i class="fa fa-edit"></i></button>
                        <button type="button" class="btn btn-danger" onclick="removeOrder('${item.id}')"><i class="fa fa-trash"></i></button>` // Actions
                    ];
                });

                // Cập nhật tổng doanh thu
                $('#totalPrice').text(formatCurrency(totalRevenue));

                return data;
            }
        },
        "columns": [
            { "title": "STT" },
            { "title": "Khách hàng" },
            { "title": "Số điện thoại" },
            // { "title": "Doanh thu" },
            { "title": "Trạng thái" },
            { "title": "Người tạo" },
            { "title": "Thời gian" },
            { "title": "Thao tác" }
        ],
        processing: true,
        serverSide: false,
        paging: true,
        searching: true,
        ordering: true,
        info: true
    });

    // Hàm định dạng tiền tệ
    function formatCurrency(amount) {
        if (amount === null || amount === undefined) {
            return '0 đ';
        }
        return amount.toLocaleString('vi-VN', {
            style: 'currency', currency: 'VND' });
    }

    // Hàm định dạng ngày tháng
    function formatDate(dateString) {
        const options = {
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit',
            // hour12: false
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
        };
        return new Date(dateString).toLocaleDateString('vi-VN', options);
    }


    window.editOrder = function(orderId) {
        $('#orderModal').modal('show');
        console.log("Order ID:", orderId);

        Promise.all([
            $.ajax({
                url: `/honeyshop/api/orders/${orderId}`,
                type: 'GET'
            }),
            $.ajax({
                url: `/honeyshop/api/orders-detail/${orderId}/details`,
                type: 'GET'
            })
        ])
            .then(function([orderResponse, orderDetailResponse]) {
                console.log("Order Data:", orderResponse);
                console.log("Order Detail Data:", orderDetailResponse);

                const order = orderResponse.data;
                const orderDetails = orderDetailResponse;

                // Điền dữ liệu vào form
                $('#fullname').val(order.fullname);
                $('#phone').val(order.phone);
                $('#cartContainer').show();
                $('#cartList').empty();

                if (Array.isArray(orderDetails) && orderDetails.length) {
                    orderDetails.forEach(function(detail) {
                        $('#cartList').append(`
                    <tr>
                        <td>${detail.product.name}</td>
                        <td><img src="/honeyshop/images/${detail.product.thumbnail}" alt="${detail.product.name}" width="50"></td>
                        <td>${formatCurrency(detail.price)}</td>
                        <td>${detail.quantity}</td>
                        <td>${formatCurrency(detail.price * detail.quantity)}</td>
<!--                        <td><button type="button" class="btn btn-danger"><i class="fa fa-trash"></i></button></td>-->
                    </tr>
                `);
                    });

                    const totalPrice = orderDetails.reduce((sum, detail) => sum + (detail.price * detail.quantity), 0);
                    $('#totalPrice').text(formatCurrency(totalPrice));
                } else {
                    // Nếu không có sản phẩm, thêm một dòng thông báo và đặt tổng tiền về 0
                    $('#cartList').append(`
                <tr>
                    <td colspan="6" class="text-center">Không có sản phẩm trong đơn hàng.</td>
                </tr>
            `);
                    $('#totalPrice').text('0 ₫');
                }
            })
            .catch(function(error) {
                console.error("Có lỗi xảy ra khi lấy dữ liệu đơn hàng:", error);
            });
    };


    window.removeOrder = function(orderId) {
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
                        url: `/honeyshop/api/orders/${orderId}`,
                        method: "DELETE",
                        contentType: "application/json",
                        dataType: "json",
                        // headers: {
                        //     'Authorization': 'Bearer ' + getToken()
                        // },
                        success: function () {
                            swal("Xóa đơn đặt hàng thành công!", {
                                icon: "success",
                            });
                            $('#dataTable').DataTable().ajax.reload();
                        },
                        error: function () {
                            swal("Xóa đơn đặt hàng thất bại!", {
                                icon: "error",
                            });
                        }
                    })
                }
            });
    }
});

