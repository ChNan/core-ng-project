import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MinCompare {
    public static void main(String[] args) {
        List<Integer> stringList = Arrays.asList(2, 1, 3, 4);

        Optional<Integer> m = stringList
            .stream()
            .min(Integer::compareTo);
        if (m.isPresent()) {
            System.out.println(m.get());
        } else {
            System.out.println("No Value");
        }

    }
}