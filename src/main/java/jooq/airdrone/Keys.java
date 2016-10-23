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

    public static final UniqueKey<RatingRecord> KEY_RATING_PRIMARY = UniqueKeys0.KEY_RATING_PRIMARY;
    public static final UniqueKey<RatingRecord> KEY_RATING_IDRATING_UNIQUE = UniqueKeys0.KEY_RATING_IDRATING_UNIQUE;
    public static final UniqueKey<RatingRecord> KEY_RATING_IDUSER_UNIQUE = UniqueKeys0.KEY_RATING_IDUSER_UNIQUE;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_IDUSER_UNIQUE = UniqueKeys0.KEY_USER_IDUSER_UNIQUE;
    public static final UniqueKey<UserRecord> KEY_USER_EMAIL_UNIQUE = UniqueKeys0.KEY_USER_EMAIL_UNIQUE;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<RatingRecord, UserRecord> FK_IDUSER = ForeignKeys0.FK_IDUSER;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<RatingRecord, Integer> IDENTITY_RATING = createIdentity(Rating.RATING, Rating.RATING.IDRATING);
        public static Identity<UserRecord, Integer> IDENTITY_USER = createIdentity(User.USER, User.USER.IDUSER);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<RatingRecord> KEY_RATING_PRIMARY = createUniqueKey(Rating.RATING, "KEY_Rating_PRIMARY", Rating.RATING.IDRATING);
        public static final UniqueKey<RatingRecord> KEY_RATING_IDRATING_UNIQUE = createUniqueKey(Rating.RATING, "KEY_Rating_idRating_UNIQUE", Rating.RATING.IDRATING);
        public static final UniqueKey<RatingRecord> KEY_RATING_IDUSER_UNIQUE = createUniqueKey(Rating.RATING, "KEY_Rating_idUser_UNIQUE", Rating.RATING.IDUSER);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = createUniqueKey(User.USER, "KEY_User_PRIMARY", User.USER.IDUSER);
        public static final UniqueKey<UserRecord> KEY_USER_IDUSER_UNIQUE = createUniqueKey(User.USER, "KEY_User_idUser_UNIQUE", User.USER.IDUSER);
        public static final UniqueKey<UserRecord> KEY_USER_EMAIL_UNIQUE = createUniqueKey(User.USER, "KEY_User_email_UNIQUE", User.USER.EMAIL);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<RatingRecord, UserRecord> FK_IDUSER = createForeignKey(jooq.airdrone.Keys.KEY_USER_PRIMARY, Rating.RATING, "FK_idUser", Rating.RATING.IDRATING);
    }
}
