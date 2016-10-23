/**
 * This class is generated by jOOQ
 */
package jooq.airdrone;


import javax.annotation.Generated;

import jooq.airdrone.tables.Rating;
import jooq.airdrone.tables.User;
import jooq.airdrone.tables.records.RatingRecord;
import jooq.airdrone.tables.records.UserRecord;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>airdrone</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<RatingRecord, Integer> IDENTITY_RATING = Identities0.IDENTITY_RATING;
    public static final Identity<UserRecord, Integer> IDENTITY_USER = Identities0.IDENTITY_USER;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<UserRecord> USER_PKEY = UniqueKeys0.USER_PKEY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<RatingRecord, UserRecord> RATING__RATING_EMAIL_FKEY = ForeignKeys0.RATING__RATING_EMAIL_FKEY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<RatingRecord, Integer> IDENTITY_RATING = createIdentity(Rating.RATING, Rating.RATING.IDRATING);
        public static Identity<UserRecord, Integer> IDENTITY_USER = createIdentity(User.USER, User.USER.IDUSER);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<UserRecord> USER_PKEY = createUniqueKey(User.USER, "user_pkey", User.USER.EMAIL);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<RatingRecord, UserRecord> RATING__RATING_EMAIL_FKEY = createForeignKey(jooq.airdrone.Keys.USER_PKEY, Rating.RATING, "rating__rating_email_fkey", Rating.RATING.EMAIL);
    }
}
