package com.alpha.lainovo.entityutils;

import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.model.PromotionalGift;
import com.alpha.lainovo.model.Type;

import java.util.Objects;


public class EntityUtils {
    // For Genre
    public static boolean equals(Genre genre1, Genre genre2) {
        if (genre1 == genre2) return true;
        if (genre1 == null || genre2 == null) return false;
        return Objects.equals(genre1.getGenreID(), genre2.getGenreID());
    }

    public static int hashCode(Genre genre) {
        return Objects.hash(genre.getGenreID());
    }

    /**
     * Phương thức equals(Genre genre1, Genre genre2): Phương thức này kiểm tra xem hai đối tượng Genre có bằng nhau hay không dựa trên GenreID của chúng.
     *
     *  Nếu genre1 và genre2 cùng trỏ đến cùng một đối tượng (cùng một tham chiếu), phương thức sẽ trả về true.
     *
     *  Nếu genre1 hoặc genre2 là null, phương thức sẽ trả về false.
     *
     *  Cuối cùng, nếu GenreID của genre1 và genre2 bằng nhau, phương thức sẽ trả về true. Nếu không, nó sẽ trả về false.
     *
     * Phương thức hashCode(Genre genre): Phương thức này trả về giá trị hash của một đối tượng Genre, được tính toán dựa trên GenreID của nó.
     *
     * Giá trị hash này được sử dụng khi đối tượng Genre được sử dụng như một khóa trong một HashMap hoặc một phần tử trong một HashSet.
     */

    // For Cover
    public static boolean equals(Cover cover1, Cover cover2) {
        if (cover1 == cover2) return true;
        if (cover1 == null || cover2 == null) return false;
        return Objects.equals(cover1.getCoverID(), cover2.getCoverID());
    }

    public static int hashCode(Cover cover) {
        return Objects.hash(cover.getCoverID());
    }

    // For Type
    public static boolean equals(Type type1, Type type2) {
        if (type1 == type2) return true;
        if (type1 == null || type2 == null) return false;
        return Objects.equals(type1.getTypeID(), type2.getTypeID());
    }

    public static int hashCode(Type type) {
        return Objects.hash(type.getTypeID());
    }

    // For Gift
    public static boolean equals(PromotionalGift gift1, PromotionalGift gift2) {
        if (gift1 == gift2) return true;
        if (gift1 == null || gift2 == null) return false;
        return Objects.equals(gift1.getPromotionalGiftID(), gift2.getPromotionalGiftID());
    }

    public static int hashCode(PromotionalGift gift) {
        return Objects.hash(gift.getPromotionalGiftID());
    }
}
