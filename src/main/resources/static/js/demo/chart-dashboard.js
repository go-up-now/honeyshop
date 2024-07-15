var myLineChart;
var myPieChart;
$(document).ready(function() {
    loadDataDashboard();
    configChartTotalRevenue();
    configRevenueByGenderChart();
});
function loadDataDashboard(){
    $.ajax({
        url: '/honeyshop/api/dashboard',
        method: 'GET',
        success: function (data) {

            let totalProfit = data.totalProfits - 175000;

            $('#totalRevenue').text(data.totalRevenue.toLocaleString('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }));
            $('#totalProfit').text(totalProfit.toLocaleString('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }));
            $('#totalProducts').text(data.totalQuantityProduct);
            $('#totalCustomers').text(data.totalCustomers);
            $('#totalProductsSold').text(data.totalProductSold);
        },
        error: function (xhr, status, error) {
            console.error("Có lỗi xảy ra: ", status, error);
        }
    });
}
function  configChartTotalRevenue(){
    // Thiết lập mặc định cho biểu đồ, sử dụng font chữ Nunito và các font dự phòng khác
    Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
    Chart.defaults.global.defaultFontColor = '#858796'; // Màu chữ mặc định được đặt thành #858796.
    // Khởi tạo biểu đồ
    var ctx = document.getElementById("myAreaChart");
    myLineChart  = new Chart(ctx, {
        type: 'line', // Loại biểu đồ là biểu đồ đường
        data: {
            labels: [], // Nhãn trục x (ban đầu là rỗng)
            datasets: [{
                label: "Doanh thu", // Nhãn của dữ liệu
                lineTension: 0.3, // Độ căng của đường biểu đồ
                backgroundColor: "rgba(78, 115, 223, 0.05)", // Màu nền của khu vực dưới đường biểu đồ
                borderColor: "rgba(78, 115, 223, 1)", // Màu đường biểu đồ
                pointRadius: 3, // Bán kính của các điểm trên đường biểu đồ
                pointBackgroundColor: "rgba(78, 115, 223, 1)", // Màu nền của các điểm
                pointBorderColor: "rgba(78, 115, 223, 1)", // Màu viền của các điểm
                pointHoverRadius: 3, // Bán kính của các điểm khi di chuột qua
                pointHoverBackgroundColor: "rgba(78, 115, 223, 1)", // Màu nền của các điểm khi di chuột qua
                pointHoverBorderColor: "rgba(78, 115, 223, 1)", // Màu viền của các điểm khi di chuột qua
                pointHitRadius: 10, // Bán kính vùng ảnh hưởng của các điểm khi nhấp chuột
                pointBorderWidth: 2, // Độ dày viền của các điểm
                data: [], // Dữ liệu của biểu đồ (ban đầu là rỗng)
            }],
        },
        options: { // Cấu hình các tùy chọn cho biểu đồ
            maintainAspectRatio: false, // Không duy trì tỉ lệ khung hình mặc định
            layout: {
                padding: {
                    left: 10, // Padding bên trái
                    right: 25, // Padding bên phải
                    top: 25, // Padding bên trên
                    bottom: 0 // Padding bên dưới
                }
            },
            scales: { // Cấu hình các trục của biểu đồ
                xAxes: [{ // Thiết lập cho trục x
                    gridLines: {
                        display: false, // Không hiển thị lưới
                        drawBorder: false // Không vẽ viền
                    },
                    ticks: {
                        maxTicksLimit: 7 // Giới hạn số lượng nhãn trục x tối đa
                    }
                }],
                yAxes: [{ // Thiết lập cho trục y
                    ticks: { // Cấu hình các nhãn trên trục y
                        maxTicksLimit: 5, // Giới hạn số lượng nhãn trục y tối đa
                        padding: 10, // Khoảng cách từ nhãn trục y đến trục
                        callback: function(value, index, values) {
                            return number_format(value) + ' đ'; // Định dạng nhãn trục y
                        }
                    },
                    gridLines: { // Cấu hình đường kẻ lưới trên trục y
                        color: "rgb(234, 236, 244)", // Màu của lưới
                        zeroLineColor: "rgb(234, 236, 244)", // Màu của đường lưới gốc (0)
                        drawBorder: false, // Không vẽ viền lưới
                        borderDash: [2], // Kiểu đường gạch ngang cho lưới
                        zeroLineBorderDash: [2] // Kiểu đường gạch ngang cho lưới gốc (0)
                    }
                }],
            },
            legend: { // Cấu hình phần chú thích của biểu đồ
                display: false // Không hiển thị chú giải (legend)
            },
            tooltips: { // Cấu hình phần hiển thị tooltip khi di chuột qua các điểm dữ liệu
                backgroundColor: "rgb(255,255,255)", // Màu nền của tooltip
                bodyFontColor: "#858796", // Màu chữ của tooltip
                titleMarginBottom: 10, // Khoảng cách dưới của tiêu đề tooltip
                titleFontColor: '#6e707e', // Màu chữ của tiêu đề tooltip
                titleFontSize: 14, // Kích thước font của tiêu đề tooltip
                borderColor: '#dddfeb', // Màu viền của tooltip
                borderWidth: 1, // Độ dày của viền tooltip
                xPadding: 15, // Padding ngang của tooltip
                yPadding: 15, // Padding dọc của tooltip
                displayColors: false, // Ẩn các màu sắc biểu đồ trong tooltip
                intersect: false, // Hiển thị tooltip khi di chuột gần điểm dữ liệu
                mode: 'index', // Hiển thị tất cả tooltip của các điểm dữ liệu cùng chỉ số
                caretPadding: 10, // Khoảng cách giữa caret và biên của tooltip
                callbacks: { // Định nghĩa các hàm callback cho tooltip
                    label: function(tooltipItem, chart) { // Hàm callback để tạo nội dung của tooltip
                        var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || ''; // Lấy nhãn của dataset
                        return datasetLabel + ': ' + number_format(tooltipItem.yLabel) + ' đ'; // Trả về chuỗi chứa nhãn và giá trị đã được định dạng
                    }
                }
            }
        }
    });
    updateChartTotalRevenue('1m');

    $('#btn-1d').on('click', function() {
        updateChartTotalRevenue('1d');
    });
    $('#btn-7d').on('click', function() {
        updateChartTotalRevenue('7d');
    });
    $('#btn-1m').on('click', function() {
        updateChartTotalRevenue('1m');
    });
}
// Hàm cập nhật dữ liệu biểu đồ từ API
function updateChartTotalRevenue(timeRange) {
    $.ajax({
        url: '/honeyshop/api/revenue_chart',
        method: 'GET', //
        data: { range: timeRange }, // Gửi dữ liệu thời gian dưới dạng tham số query
        success: function (response) {
            var data = response.data;
            console.log(data)
            myLineChart.data.labels = data.labels;
            myLineChart.data.datasets[0].data = data.data;
            myLineChart.update();
        },
        error: function (xhr, status, error) {
            console.error("Có lỗi xảy ra: ", status, error);
        }
    });
}
function configRevenueByGenderChart() {
    // Khởi tạo biểu đồ với dữ liệu trống
    var ctx = document.getElementById("myPieChart");
    myPieChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: [],  // Sẽ được cập nhật từ API
            datasets: [{
                data: [],  // Sẽ được cập nhật từ API
                label:"Doanh thu",
                backgroundColor: ['#4e73df', '#f6c23e'], // Màu nền
                hoverBackgroundColor: ['#2e59d9', '#f0b429'], // Màu nền khi hover
                hoverBorderColor: "rgba(234, 236, 244, 1)",
            }],
        },
        options: {
            maintainAspectRatio: false,
            tooltips: {
                backgroundColor: "rgb(255,255,255)",
                bodyFontColor: "#858796",
                borderColor: '#dddfeb',
                borderWidth: 1,
                xPadding: 15,
                yPadding: 15,
                displayColors: false,
                caretPadding: 10,
                callbacks: {
                    label: function(tooltipItem, chart) {
                        var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
                        var totalRevenue = myPieChart.data.datasets[0].data[tooltipItem.index];
                        return datasetLabel + ': ' + number_format(totalRevenue) + ' đ'; // Hiển thị tổng doanh thu của giới tính đó
                    }
                }
            },
            legend: {
                display: false,  // Ẩn legend
            },
            cutoutPercentage: 80,  // Kích thước lỗ giữa biểu đồ
        },
    });
    updatePieChart();
}

function updatePieChart() {
    $.ajax({
        url: '/honeyshop/api/revenue_gender',
        method: 'GET',
        success: function(response) {
            if (response.code === 1000) {
                // Xử lý dữ liệu từ API
                var data = response.data;
                var labels = ['Nam', 'Nữ'];
                var revenues = [0, 0];  // Index 0 cho Nam, 1 cho Nữ

                // Phân loại doanh thu theo giới tính
                data.forEach(function(item) {
                    if (item.gender) {
                        revenues[0] = item.revenue;
                    } else {
                        revenues[1] = item.revenue;
                    }
                });

                // Cập nhật dữ liệu cho biểu đồ
                myPieChart.data.labels = labels;
                myPieChart.data.datasets[0].data = revenues;
                myPieChart.data.datasets[0].backgroundColor = ['#4e73df', '#f6c23e'];
                myPieChart.data.datasets[0].hoverBackgroundColor = ['#2e59d9', '#f0b429'];
                myPieChart.update();
            } else {
                console.error('API error: ' + response.message);
            }
        },
        error: function(error) {
            console.error('AJAX error: ', error);
        }
    });
}
// Hàm định dạng số
function number_format(number, decimals, dec_point, thousands_sep) {
    number = (number + '').replace(',', '').replace(' ', ''); // Xóa các dấu phẩy và khoảng trắng trong chuỗi số
    var n = !isFinite(+number) ? 0 : +number, // Kiểm tra nếu là số hợp lệ, nếu không thì đặt là 0
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals), // Lấy số lượng chữ số thập phân, mặc định là 0
        sep = (typeof thousands_sep === 'undefined') ? '.' : thousands_sep, // Ký tự phân tách hàng nghìn, mặc định là ','
        dec = (typeof dec_point === 'undefined') ? ',' : dec_point, // Ký tự phân tách thập phân, mặc định là '.'
        s = '',
        toFixedFix = function(n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.round(n * k) / k; // Làm tròn số với số chữ số thập phân chính xác
        };
    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.'); // Tách phần nguyên và phần thập phân của số
    if (s[0].length > 3) {
        s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep); // Thêm ký tự phân tách hàng nghìn vào phần nguyên
    }
    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0'); // Thêm các số 0 vào phần thập phân nếu cần
    }
    return s.join(dec); // Nối phần nguyên và phần thập phân với ký tự phân tách thập phân
}