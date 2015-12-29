package web.demo;

/**
 * @author ChNan
 */
public class OrderWebServiceImpl implements OrderWebService {
    @Override
    public String getOrder() {
        System.out.println("Call success");
        return "ChNan order";
    }
}
