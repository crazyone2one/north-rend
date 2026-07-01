package cn.master.northrend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : 11's papa
 * @since : 2026/6/25, 星期四
 **/
@SpringBootTest
public class MinePointSorterTest {
    static class SfJxzySubstation implements Comparable<SfJxzySubstation> {
        private String id;
        private String stationNum;
        private String stationName;
        private String areaX; // 数据库为 varchar
        private String areaY; // 数据库为 varchar
        private String stationCode;

        public SfJxzySubstation(String id, String stationNum, String stationCode, String stationName, String areaX, String areaY) {
            this.id = id;
            this.stationNum = stationNum;
            this.stationName = stationName;
            this.areaX = areaX;
            this.areaY = areaY;
            this.stationCode = stationCode;
        }

        @Override
        public int compareTo(SfJxzySubstation other) {
            double thisX = Double.parseDouble(this.areaX);
            double thisY = Double.parseDouble(this.areaY);
            double otherX = Double.parseDouble(other.areaX);
            double otherY = Double.parseDouble(other.areaY);

            // 第一优先级：Y 坐标降序
            int yCompare = Double.compare(otherY, thisY);
            if (yCompare != 0) {
                return yCompare;
            }

            // 第二优先级：X 坐标降序
            int xCompare = Double.compare(otherX, thisX);
            if (xCompare != 0) {
                return xCompare;
            }

            // 第三优先级：station_num 升序兜底
            return Integer.compare(Integer.parseInt(this.stationNum),
                    Integer.parseInt(other.stationNum));
        }

        @Override
        public String toString() {
            return String.format("序号:%s | 站点编码:%s | 名称:%s | X:%s | Y:%s", stationNum, stationCode, stationName, areaX, areaY);
        }
    }

    @Test
    void test() {
        List<SfJxzySubstation> list = new ArrayList<>();

        list.add(new SfJxzySubstation("db94...", "3", "150622B001200020009201AL", "主斜井二联巷 J22-D7", "37527146.2880", "4429539.8320"));
        list.add(new SfJxzySubstation("db94...", "8", "150622B001200020009201AN", "主斜井950米 J23-D5", "37526981.0280", "4429424.1420"));
        list.add(new SfJxzySubstation("db94...", "5", "150622B001200020009201AD", "主斜井三联巷  J23-D1", "37526693.9880", "4429223.2420"));
        list.add(new SfJxzySubstation("db94...", "257", "150622B001200020009201AF", "主斜井三联巷1350米 J23-D3", "37526654.8180", "4429195.8220"));
        list.add(new SfJxzySubstation("347a...", "1", "150622B0012000200092ZZ", "主斜井口 J22-D5", "37527753.9080", "4429965.5420"));
        list.add(new SfJxzySubstation("59ea...", "2", "150622B0012000200092ZH", "主斜井口 J22-D6", "37527749.0080", "4429962.1320"));
        list.add(new SfJxzySubstation("e7f8...", "111", "150622B001200020009201AR", "主斜井250米 J22-D1", "37527549.6780", "4429823.4520"));
        list.add(new SfJxzySubstation("fda0...", "112", "150622B001200020009201AQ", "主斜井250米 J22-D2", "37527545.5980", "4429820.6120"));
        list.add(new SfJxzySubstation("2584...", "113", "150622B001200020009201AP", "主斜井360米 J22-D3", "37527461.6080", "4429761.8220"));
        list.add(new SfJxzySubstation("89dc...", "205", "150622B001200020009201CO", "主斜井360米 J22-D4", "37527457.5380", "4429758.9520"));
        list.add(new SfJxzySubstation("664d...", "4", "150622B001200020009201AJ", "主斜井二联巷 J22-D8", "37527151.1680", "4429543.2620"));

        Collections.sort(list);
        System.out.println("=== 最终排序结果 (从井口向井下) ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }
}
