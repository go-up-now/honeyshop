$(document).ready(function() {
    // Nếu DataTable đã được khởi tạo, hãy hủy khởi tạo trước khi tiếp tục
    if ($.fn.DataTable.isDataTable('#dataTable')) {
        $('#dataTable').DataTable().clear().destroy();
    }

    // Khởi tạo DataTable
    const table = $('#dataTable').DataTable({
        ajax: {
            url: '/honeyshop/api/statistical/gender-of-product-consumption',
            dataSrc: function (json) {
                console.log("json: " + json)

                // Kiểm tra nếu json không phải là mảng và chuyển đến thuộc tính data
                if (json.data && Array.isArray(json.data)) {
                    json = json.data;
                } else {
                    console.error('Dữ liệu trả về không chứa thuộc tính data mong đợi hoặc không phải là mảng.');
                    return [];
                }

                let totalRevenue = 0;
                // json.forEach(item => totalRevenue += parseFloat(item.totalRevenue) || 0);
                // $('#totalRevenue').text(formatCurrencyVND(totalRevenue));

                return json.map((item) => [
                    item.gender || 'N/A',
                    item.productName || 'N/A',
                    item.categoryName || 'N/A',
                    item.numberOfSales || 0,
                    item.totalRevenue ? formatCurrencyVND(item.totalRevenue) : 'N/A',
                    item.percentageOfTotalSales ? (item.percentageOfTotalSales.toFixed(2)) + '%' : 'N/A'
                ]);
            },
            error: function (xhr, status, error) {
                console.error('Lỗi khi tải dữ liệu:', status, error);
            }
        },
        columns: [
            { title: 'Giới tính', width: '10%' }, // Thiết lập chiều rộng cho từng cột
            { title: 'Tên sản phẩm', width: '20%' },
            { title: 'Loại sản phẩm', width: '20%' },
            { title: 'Số lượng bán', width: '10%' },
            { title: 'Tổng doanh thu', width: '15%' },
            { title: 'Phần trăm trên tổng số bán', width: '15%' }
        ],
        processing: true,
        serverSide: false,
        paging: true,
        searching: true,
        ordering: true,
        info: true
    });

});