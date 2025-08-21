package com.trading_simulator.backend.service.externalservice;

import com.trading_simulator.backend.object.dto.TickData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrueFXServiceImpl implements TrueFXService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String connect(String currencyPairs, String format) {
//        String url = "http://webrates.truefx.com/rates/connect.html"
//                + "?id=" + ... + "&pairs=" + currencyPairs + "&f=" + format;
//        return restTemplate.getForObject(url, String.class);
        return "";
    }

    @Override
    public void disconnect(String sessionId) {
        // logic gọi endpoint disconnect (nếu có)
    }

    @Override
    public String reconnect(String sessionId) {
        // logic connect lại session
//        return connect(...);
        return "";
    }

    @Override
    public String query(String sessionId) {
        String url = "http://webrates.truefx.com/rates/connect.html?id=" + sessionId;
        return restTemplate.getForObject(url, String.class);
    }

    @Override
    public List<TickData> parse(String rawResponse) {
        // logic parse dữ liệu HTML/CSV về list các đối tượng TickData { symbol, time, bid, ask }
        return new ArrayList<>();
    }

    @Override
    public URL getHistoricalDataUrl(String symbol, YearMonth ym) {
//        String urlStr = "http://www.truefx.com/dev/data/"
//                + ym.getYear() + "/" + String.format("%02d", ym.getMonthValue())
//                + "/" + symbol + "-" + ym.toString() + ".zip";
//        return URL(urlStr);
        return null;
    }
}
