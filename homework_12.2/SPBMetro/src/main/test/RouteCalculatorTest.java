import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RouteCalculatorTest extends TestCase {

    List<Station> twoTransfersRoute;
    List<Station> oneTransfersRoute;
    List<Station> noTransferRoute;
    RouteCalculator calculator;
    Station lenina;
    Station pobedi;
    Station molodejnaya;
    Station aerodromnaya;

    @Override
    protected void setUp() throws Exception {
        StationIndex stationIndex = new StationIndex();
        Line line = new Line(1,"Синяя");
        Line line1 = new Line(2, "Красная");
        Line line2 = new Line(3, "Зеленая");
        pobedi = new Station("Площадь победы", line);
        Station okt = new Station("Октябрьская", line);
        lenina = new Station("Площадь Ленина", line);
        Station kup = new Station("Купаловская", line1);
        Station bogushevicha = new Station("Площадь Богушевича", line2);
        Station frunz = new Station("Фрунзенская", line1);
        molodejnaya = new Station("Молодежная", line1);
        aerodromnaya = new Station("Аэродромная", line2);
        line.addStation(pobedi);
        line.addStation(okt);
        line.addStation(lenina);
        line2.addStation(bogushevicha);
        line1.addStation(kup);
        line1.addStation(frunz);
        line1.addStation(molodejnaya);
        line2.addStation(aerodromnaya);
        stationIndex.addLine(line);
        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addConnection(Stream.of(okt, kup).collect(Collectors.toList()));
        stationIndex.addConnection(Stream.of(frunz, bogushevicha).collect(Collectors.toList()));

        noTransferRoute = Stream.of(pobedi, okt, lenina).collect(Collectors.toList());
        oneTransfersRoute = Stream.of(pobedi, okt, kup, frunz, molodejnaya).collect(Collectors.toList());
        twoTransfersRoute = Stream.of(pobedi, okt, kup, frunz, bogushevicha, aerodromnaya).collect(Collectors.toList());

        calculator = new RouteCalculator(stationIndex);
    }

    public void testGetShortestRoute() {
        List<Station> actualNoTransfer = calculator.getShortestRoute(pobedi, lenina);
        List<Station> actualOneTransfer = calculator.getShortestRoute(pobedi, molodejnaya);
        List<Station> actualTwoTransfers = calculator.getShortestRoute(pobedi, aerodromnaya);

        List<Station> expectedNoTransfers = noTransferRoute;
        List<Station> expectedOneTransfers = oneTransfersRoute;
        List<Station> expectedTwoTransfers = twoTransfersRoute;

        assertEquals(expectedNoTransfers, actualNoTransfer);
        assertEquals(expectedOneTransfers, actualOneTransfer);
        assertEquals(expectedTwoTransfers, actualTwoTransfers);
    }

    public void testCalculateDuration() {
        double actual = RouteCalculator.calculateDuration(twoTransfersRoute);
        double expected = 14.5;
        assertEquals(expected, actual);
    }
}
