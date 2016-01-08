package web.route;

/**
 * @author Dylan
 */
public class PathNode {

    public final String value;

    private PathNode next;

    public PathNode(String value) {
        this.value = value;
    }

    /**
     * path = /order/:orderId/:user
     * <p>
     * root is below:
     * /
     * ---next---order
     * -----next-----:orderId
     * ----------next---------:user
     */
    public static PathNode parse(String path) {
        PathNode root = new PathNode("/");
        if (path.equals("/")) return root;

        PathNode current = root;
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < path.length(); i++) {
            char ch = path.charAt(i);
            if (ch != '/') {
                builder.append(ch);
            } else {
                current.next = new PathNode(builder.toString());
                current = current.next;
                builder = new StringBuilder();
            }
        }
        String lastPath = builder.length() == 0 ? "/" : builder.toString();
        current.next = new PathNode(lastPath);
        return root;
    }
}
