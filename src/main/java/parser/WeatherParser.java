package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class WeatherParser {

    public String getWeatherData(String cityName){
        String url = "https://yandex.ru/pogoda/search?request=" + cityName;
        String resultUrl = "https://yandex.ru";
        String data = "";
        Document doc;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
                    .referrer("http://www.google.com")
                    .get();
            Element el = doc.selectFirst("h1[class=title title_level_1]");
            System.out.println(el);
            if ( el != null && el.ownText().equals("По вашему запросу ничего не нашлось")){
                return cityName + " - unknown city";
            }

            Elements els = doc.getElementsByClass("link place-list__item-name i-bem");
            resultUrl += els.get(0).attr("href");
            System.out.println(resultUrl);
            Document weather = Jsoup.connect(resultUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
                    .referrer("http://www.google.com")
                    .get();

            Element divData = weather.selectFirst("div[class~=fact fact_theme.*card card_size_big]");
            System.out.println(divData);
            String tittle = divData.selectFirst("h1[id=main_title]").ownText();

            Element lastDay = divData.selectFirst("div[class=fact__time-yesterday-wrap]");
            String time = lastDay.selectFirst("time[class=time fact__time]").ownText();
            String lastTempValue = lastDay.selectFirst("span[class=temp__value]").ownText();

            Element nowDay = divData.selectFirst("div[class~=fact__temp-wrap]");
            String nowTempValue = nowDay.selectFirst("span[class=temp__value]").ownText();

            Element feeling = nowDay.selectFirst("div[class=link__feelings fact__feelings]");
            String feelWeather = feeling.selectFirst("div[class~=link__condition day-anchor \\w+]").ownText();
            String feelTempValue = feeling.selectFirst("span[class=temp__value]").ownText();

            Element factProps = divData.selectFirst("div[class=fact__props]");
            String speed = factProps.selectFirst("div[class=term term_orient_v fact__wind-speed]").selectFirst("div[class=term__value]").attr("aria-label");
            String humidity = factProps.selectFirst("div[class=term term_orient_v fact__humidity]").selectFirst("div[class=term__value]").attr("aria-label");
            String pressure = factProps.selectFirst("div[class= term term_orient_v fact__pressure]").selectFirst("div[class=term__value]").attr("aria-label");

            data += new StringBuilder().append(tittle).append("\n")
                    .append(time).append(" Вчера в это время: ").append(lastTempValue).append("\n")
                    .append("Температура: ").append(nowTempValue).append(" ").append(feelWeather).append(" ощущается как ").append(feelTempValue).append("\n")
                    .append(speed).append("\n")
                    .append(humidity).append("\n")
                    .append(pressure).append("\n");

        } catch (IOException e) {
            e.printStackTrace();
            return "Unknown city";
        }
        return data;
    }
}
