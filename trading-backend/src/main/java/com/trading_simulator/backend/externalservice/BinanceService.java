package com.trading_simulator.backend.externalservice;

// Bao gồm các dữ liệu Crypto
public interface BinanceService {

    // ====================== SPOT MARKET DATA ======================

    /**
     * Lấy giá hiện tại của một cặp giao dịch (ví dụ: BTCUSDT)
     * @param symbol Cặp giao dịch, viết liền và in hoa (vd: "BTCUSDT")
     * @return JSON chứa giá hiện tại
     */
    String getCurrentPrice(String symbol);

    /**
     * Lấy dữ liệu nến (OHLCV) - Open, High, Low, Close, Volume
     * @param symbol Cặp giao dịch (vd: "BTCUSDT")
     * @param interval Khoảng thời gian mỗi nến (vd: "1m", "5m", "1h", "1d")
     * @param limit Số lượng nến muốn lấy (tối đa 1000)
     * @return JSON chứa danh sách nến
     */
    String getKlines(String symbol, String interval, int limit);

    /**
     * Lấy order book (sổ lệnh mua/bán) hiện tại
     * @param symbol Cặp giao dịch
     * @param limit Số lượng lệnh tối đa (5, 10, 20, 50, 100, 500, 1000, 5000)
     * @return JSON chứa bids (mua) và asks (bán)
     */
    String getOrderBook(String symbol, int limit);

    /**
     * Lấy lịch sử giao dịch gần nhất (public trade history)
     * @param symbol Cặp giao dịch
     * @param limit Số lượng giao dịch (tối đa 1000)
     * @return JSON danh sách các giao dịch gần đây
     */
    String getRecentTrades(String symbol, int limit);

    /**
     * Lấy dữ liệu giao dịch gộp (Aggregate Trades) - gộp các lệnh khớp liên tục thành một bản ghi
     * @param symbol Cặp giao dịch
     * @param limit Số lượng bản ghi muốn lấy
     * @return JSON danh sách giao dịch gộp
     */
    String getAggTrades(String symbol, int limit);

    /**
     * Lấy thống kê biến động 24 giờ cho một cặp giao dịch
     * @param symbol Cặp giao dịch
     * @return JSON chứa giá cao nhất, thấp nhất, volume 24h, % thay đổi
     */
    String get24hrStats(String symbol);

    /**
     * Lấy thống kê biến động 24 giờ cho tất cả các cặp giao dịch
     * @return JSON chứa danh sách thống kê cho tất cả symbol
     */
    String getAll24hrStats();

    /**
     * Lấy thống kê biến động theo khoảng thời gian tùy chọn (rolling window)
     * @param symbol Cặp giao dịch
     * @param windowSize Thời gian (vd: "1h", "2d")
     * @return JSON thống kê trong khoảng thời gian đó
     */
    String getRollingWindowTicker(String symbol, String windowSize);

    /**
     * Lấy giá trung bình (average price) trong khoảng thời gian gần nhất
     * @param symbol Cặp giao dịch
     * @return JSON chứa giá trung bình
     */
    String getAveragePrice(String symbol);

    /**
     * Lấy thông tin toàn bộ thị trường, bao gồm:
     * - Danh sách cặp giao dịch
     * - Giới hạn khối lượng, bước giá, ...
     * @return JSON thông tin thị trường
     */
    String getExchangeInfo();

    /**
     * Lấy thời gian server Binance (UTC)
     * @return JSON chứa timestamp server
     */
    String getServerTime();


    // ====================== FUTURES MARKET DATA ======================

    /**
     * Lấy Mark Price (giá đánh dấu) của hợp đồng Futures
     * @param symbol Cặp giao dịch Futures (vd: "BTCUSDT")
     * @return JSON chứa Mark Price và Funding Rate hiện tại
     */
    String getMarkPrice(String symbol);

    /**
     * Lấy lịch sử Funding Rate cho hợp đồng Futures
     * @param symbol Cặp giao dịch Futures
     * @param limit Số lượng bản ghi muốn lấy
     * @return JSON danh sách funding rate lịch sử
     */
    String getFundingRateHistory(String symbol, int limit);

    /**
     * Lấy Open Interest (tổng khối lượng hợp đồng mở) của một cặp Futures
     * @param symbol Cặp giao dịch Futures
     * @return JSON chứa Open Interest
     */
    String getOpenInterest(String symbol);

    /**
     * Lấy tỷ lệ tài khoản Long/Short hàng đầu (Top Trader Account Ratio)
     * @param symbol Cặp giao dịch Futures
     * @param period Khoảng thời gian (vd: "5m", "15m", "1h", "1d")
     * @param limit Số lượng bản ghi muốn lấy
     * @return JSON danh sách tỷ lệ long/short theo thời gian
     */
    String getTopLongShortAccountRatio(String symbol, String period, int limit);
}
