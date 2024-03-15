package com.alpha.lainovo.EntityUtils;

import com.alpha.lainovo.model.Cover;
import com.alpha.lainovo.model.Genre;
import com.alpha.lainovo.model.PromotionalGift;
import com.alpha.lainovo.model.Type;
import lombok.extern.slf4j.Slf4j;

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
