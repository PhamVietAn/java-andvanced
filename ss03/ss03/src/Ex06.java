import java.util.List;
import java.util.stream.Collectors;

public class Ex06 {

    public record Post(String title, List<String> tags) {}

    public static void main(String[] args) {

        List<Post> posts = List.of(
                new Post("Java Post", List.of("java", "backend")),
                new Post("Python Post", List.of("python", "data"))
        );

        List<String> allTags = posts.stream()
                .flatMap(post -> post.tags().stream())
                .collect(Collectors.toList());

        System.out.println(allTags);
    }
}