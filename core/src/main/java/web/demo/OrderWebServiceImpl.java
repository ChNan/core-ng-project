package web.demo;

/**
 * @author Dylan
 */
public class OrderWebServiceImpl implements OrderWebService {
    @Override
    public String getOrder() {
        System.out.println("Call success");
        return "Dylan order";
    }
}
