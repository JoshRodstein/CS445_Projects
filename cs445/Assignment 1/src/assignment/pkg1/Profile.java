package cs445.a1;

import java.lang.Math;
import java.util.Arrays;

/**
 * CS 445 Assignment 1
 *
 * @author Joshua Rodstein
 */
public class Profile implements ProfileInterface {

    private String name, about;
    SetInterface<ProfileInterface> followingSet = new Set<>();

    public Profile() {
        name = "";
        about = "";

    }

    public Profile(String name, String about) {
        this.name = name;
        this.about = about;

    }

    /**
     * Sets this profile's name.
     *
     * <p>
     * If newName is not null, then setName modifies this profile so that its
     * name is newName. If newName is null, then setName throws
     * java.lang.IllegalArgumentException without modifying the profile.
     *
     * @param newName The new name
     * @throws java.lang.IllegalArgumentException If newEntry is null
     */
    public void setName(String newName)
            throws java.lang.IllegalArgumentException {
        if (newName == null) {
            throw new IllegalArgumentException();
        }
        name = newName;
    }

    /**
     * Gets this profile's name.
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets this profile's "about me" blurb.
     *
     * <p>
     * If newAbout is not null, then setAbout modifies this profile so that its
     * about blurb is newAbout. If newAbout is null, then setAbout throws
     * java.lang.IllegalArgumentException without modifying the profile.
     *
     * @param newAbout The new blurb
     * @throws java.lang.IllegalArgumentException If newAbout is null
     */
    public void setAbout(String newAbout)
            throws java.lang.IllegalArgumentException {
        if (newAbout == null) {
            throw new IllegalArgumentException();
        }
        about = newAbout;
    }

    /**
     * Gets this profile's "about me" blurb
     *
     * @return The blurb
     */
    public String getAbout() {
        return about;
    }

    /**
     * Adds another profile to this profile's following set.
     *
     * <p>
     * If this profile's following set is at capacity, or if other is null, then
     * follow returns false without modifying the profile. Otherwise, this
     * profile in modified in such a way that other is added to this profile's
     * following set.
     *
     * @param other The profile to follow
     * @return True if successful, false otherwise
     */
    public boolean follow(ProfileInterface other) {
        if (other == null) {
            return false;
        }
        try {
            return followingSet.add(other);
        } catch (SetFullException e) {
            return false;
        }
    }

    /**
     * Removes the specified profile from this profile's following set.
     *
     * <p>
     * If this profile's following set does not contain other, or if other is
     * null, then unfollow returns false without modifying the profile.
     * Otherwise, this profile in modified in such a way that other is removed
     * from this profile's following set.
     *
     * @param other The profile to follow
     * @return True if successful, false otherwise
     */
    public boolean unfollow(ProfileInterface other) {
        try {
            if (followingSet.contains(other)) {
                return followingSet.remove(other);
            }
        } catch (IllegalArgumentException e) {
        }
        return false;
    }

    /**
     * Returns a preview of this profile's following set.
     *
     * <p>
     * The howMany parameter is a maximum size. The returned array may be less
     * than the requested size if this profile is following fewer than howMany
     * other profiles. Clients of this method must be careful to check the size
     * of the returned array to avoid java.lang.ArrayIndexOutOfBoundsException.
     *
     * <p>
     * Specifically, following returns an array of size max(howMany, &lt;number
     * of profiles that this profile is following&gt;). This array is populated
     * with arbitrary profiles that this profile follows.
     *
     * @param howMany The maximum number of profiles to return
     * @return An array of size &le;howMany, containing profiles that this
     * profile follows
     */
    @Override
    public ProfileInterface[] following(int howMany) {
        return Arrays.copyOf(followingSet.toArray(), Math.min(howMany,
                followingSet.getCurrentSize()), ProfileInterface[].class);
    }

    /**
     * Recommends a profile for this profile to follow. This returns a profile
     * followed by one of this profile's followed profiles. Should not recommend
     * this profile to follow someone they already follow, and should not
     * recommend to follow oneself.
     *
     * <p>
     * For example, if this profile is Alex, and Alex follows Bart, and Bart
     * follows Crissy, this method might return Crissy.
     *
     * @return The profile to suggest, or null if no suitable profile is
     * possible.
     */
    public ProfileInterface recommend() {
        int index = 0;
        ProfileInterface[] friends = Arrays.copyOf(followingSet.toArray(), 
                followingSet.getCurrentSize(), ProfileInterface[].class);
        

        while(index < friends.length) {
            if(friends[index].following(1).length > 0){
                ProfileInterface[] friendsOf = friends[index].
                        following(4);
                for(int i = 0; i < friendsOf.length; i++){
                    if (!followingSet.contains(friendsOf[i]) && 
                            friendsOf[i] != this){
                        return friendsOf[i];
                    }
                }
            }
            index++;
        }
        

        return null;
    }

}
