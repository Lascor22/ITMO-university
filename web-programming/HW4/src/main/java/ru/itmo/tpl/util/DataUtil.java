package ru.itmo.tpl.util;

import ru.itmo.tpl.model.Color;
import ru.itmo.tpl.model.Post;
import ru.itmo.tpl.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, 1, "Update", "Кодфорсес больше не будет падать"),
            new Post(2, 2, "New Round", "Я написал новый раунд"),
            new Post(3, 3, "Contest", "My contest special for my lovely russians"),
            new Post(4, 5, "Algorithms", "Я тут сделал контест для рассказанных ранее алгосиков"),
            new Post(5, 7, "Biography", "Senior full stack Codeforces developer"),
            new Post(6, 11, "Information for children", "Не дудосьте кф пожалуйста"),
            new Post(7, 2, "500 слов", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Enim nulla aliquet porttitor lacus luctus accumsan tortor posuere ac. Lectus vestibulum mattis ullamcorper velit sed ullamcorper morbi. Adipiscing elit pellentesque habitant morbi tristique. Dignissim cras tincidunt lobortis feugiat vivamus at augue eget arcu. Nullam non nisi est sit amet facilisis magna etiam tempor. Ultrices vitae auctor eu augue ut lectus arcu. Posuere ac ut consequat semper viverra nam. Mauris in aliquam sem fringilla. Sed pulvinar proin gravida hendrerit lectus a. Tortor dignissim convallis aenean et tortor. Purus in mollis nunc sed id semper risus in hendrerit. Posuere urna nec tincidunt praesent semper feugiat nibh sed. Sagittis eu volutpat odio facilisis mauris sit amet massa. Etiam dignissim diam quis enim. Ultrices sagittis orci a scelerisque. Egestas fringilla phasellus faucibus scelerisque eleifend donec. Scelerisque eu ultrices vitae auctor eu augue. Tempus iaculis urna id volutpat lacus laoreet non curabitur. In cursus turpis massa tincidunt dui. Donec et odio pellentesque diam volutpat. Sit amet consectetur adipiscing elit pellentesque habitant morbi tristique senectus. Vitae justo eget magna fermentum. Sed risus ultricies tristique nulla aliquet enim tortor at auctor. In ornare quam viverra orci sagittis. Enim facilisis gravida neque convallis. Congue mauris rhoncus aenean vel elit. Ac tortor vitae purus faucibus ornare suspendisse sed. Condimentum mattis pellentesque id nibh tortor id aliquet lectus. Urna nunc id cursus metus aliquam eleifend. Facilisis leo vel fringilla est ullamcorper eget nulla. Phasellus faucibus scelerisque eleifend donec pretium vulputate sapien nec sagittis. Eget sit amet tellus cras adipiscing enim eu turpis. Varius morbi enim nunc faucibus a pellentesque sit. In fermentum et sollicitudin ac orci phasellus egestas tellus rutrum. Nunc sed augue lacus viverra vitae congue eu consequat ac. Ullamcorper eget nulla facilisi etiam dignissim diam quis enim. Metus vulputate eu scelerisque felis imperdiet proin fermentum. Urna porttitor rhoncus dolor purus non enim praesent. Ultrices eros in cursus turpis massa tincidunt. Sodales neque sodales ut etiam sit amet. In arcu cursus euismod quis viverra nibh. Lectus proin nibh nisl condimentum. Enim nulla aliquet porttitor lacus. Ornare quam viverra orci sagittis eu. Faucibus scelerisque eleifend donec pretium vulputate sapien nec sagittis aliquam. Diam vulputate ut pharetra sit amet aliquam id diam maecenas. Enim blandit volutpat maecenas volutpat blandit aliquam etiam. In ornare quam viverra orci sagittis eu volutpat odio. Id donec ultrices tincidunt arcu non sodales neque sodales."),
            new Post(8, 2, "250 символов", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Enim nulla aliquet porttitor lacus luctus accumsan tortor posuere ac. Lectus vestibulum mattis ullamcorper velit sed ullamcorp"),
            new Post(9, 2, "251 символ", "Lorem dipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Enim nulla aliquet porttitor lacus luctus accumsan tortor posuere ac. Lectus vestibulum mattis ullamcorper velit sed ullamcorp")
    );

    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirayanov", "Mikhail Mirzayanov", Color.BLACK, getCountPosts(1)),
            new User(2, "tourist", "Genady Korotkevich", Color.RED, getCountPosts(2)),
            new User(3, "emusk", "Elon Musk", Color.GREEN, getCountPosts(3)),
            new User(5, "pashka", "Pavel Mavrin", Color.RED, getCountPosts(5)),
            new User(7, "geranazavr555", "Georgiy Nazarov", Color.GREY, getCountPosts(7)),
            new User(11, "cannon147", "Erofey Bashunov", Color.BLUE, getCountPosts(11))
    );

    private static List<User> getUsers() {
        return USERS;
    }

    private static List<Post> getPosts() {
        return POSTS;
    }

    private static long getCountPosts(long userId) {
        long answer = 0;
        for (Post post : POSTS) {
            if (post.getUserId() == userId) {
                answer++;
            }
        }
        return answer;
    }

    public static void putData(Map<String, Object> data) {
        data.put("users", getUsers());
        data.put("posts", getPosts());
        for (User user : getUsers()) {
            if (data.get("logged_user_id") != null && user.getId() == (long) data.get("logged_user_id")) {
                data.put("user", user);
            }
        }
    }
}

