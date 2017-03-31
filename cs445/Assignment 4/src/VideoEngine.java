package cs445.a4;

/**
 * This abstract data type is a predictive engine for video ratings in a streaming video set. It
 * stores a set of users, a set of videos, and a set of ratings that users have assigned to videos.
 *
 * ADD OTHER DETAILS HERE IF NECESSARY
 */
public interface VideoEngine {

    /**
     * The abstract methods below are declared as void methods with no parameters. You need to
     * expand each declaration to specify a return type and parameters, as necessary. You also need
     * to include a detailed comment for each abstract method describing its effect, its return  
     * value, any corner cases that the client may need to consider, any exceptions the method may
     * throw (including a description of the circumstances under which this will happen), and so on.
     * You should include enough details that a client could use this data structure without ever
     * being surprised or not knowing what will happen, even though they haven't read the
     * implementation.
     */

    /**
     * Adds a video to the set, avoiding duplicates.
     * 
     * <p> If newVideo is not null, the set does not contain newVideo, and the
     * set has available capacity, then add modifies the set so that it contains
     * newVideo. other videos remain unmodified.
     * 
     * <p> If newEntry is null, then addVideo throws NullPointerException without modifying the set. 
     * If the set already contains newVideo, then Video returns false without modifying the
     * set. If the set has a capacity limit, and does not have available
     * capacity, then add throws IllegalStateException without modifying the set.
     * 
     * @param newVideo video to be added to the set
     * @returns true if the addition is successful; false if newVideo is already
     * in the set
     * @throws NullPointerException  If newVideo is null
     */
    public boolean addVideo(Video newVideo) 
            throws NullPointerException, IllegalStateException;

    /**
     * Removes a specific video from this set, if possible.
     * 
     * <p> If the set contains theVideo, remove will modify the set so that it no
     * longer contains theVideo. All other videos remain unmodified.
     * 
     * <p> If the set does not contain theVideo, remove will return false without
     * modifying the set. If theVideo is null, then remove throws
     * IllegalArgumentException without modifying the set.
     * 
     * @param theVideo video to be removed from set
     * @return  true if the removal was successful; false if not
     * @throws IllegalArgumentException  If entry is null
     */
    public boolean removeVideo(Video theVideo) throws IllegalArgumentException;

    /**
     * Adds an existing television episode to an existing television series, avoiding duplicates.
     * 
     * <p> If theSeries is not null, theSeries exists, addEpisode is not null,  and 
     * addEpisode has not already been added to theSeries, then addToSeries modifies theSeries
     * by adding the episode to the Series. All other entries remain unmodified.
     * 
     * <p> If TvSeries does not exist, then addToSeries throws IllegalArgumentException.
     * If either addEpisode or theSeries is null, addToSeries throws NullPointerException.
     * 
     * @param addEpisode TvEpisode to be added
     * @param theSeries v
     * @return true if addEpisode successfully added to theSeries; false if add fails or
     * if addEpisode already exists in theSeries
     * @throws NullPointerException if either addEpisode or theSeries is null
     * @throws IllegalArgumentException if theSeries does not exist. 
     */
    public boolean addToSeries(TvEpisode addEpisode, TvSeries theSeries)
            throws NullPointerException, IllegalArgumentException;

    /**
     * Removes a television episode from a television series.
     * 
     * <p> If theSeries is not null, theSeries exists, removeEpisode is not null,  and 
     * theSeries contains removeEpisode, then removeFromSeries modifies theSeries
     * by removing the episode from the series. All other entries remain unmodified.
     * 
     * <p> If theSeries does not exist, then removeFromSeries throws IllegalArgumentException.
     * If either removeEpisode or theSeries is null, removeFromSeries throws NullPointerException.
     * 
     * @param removeEpisode TvEpisode to be removed from series
     * @param theSeries TvSeries from which TvEpisode should be removed
     * @return true if removeEpisode successfully removed from theSeries; false if remove fails, 
     * or if theSeries does not contain the TvEpisode
     * @throws NullPointerException if either addEpisode or theSeries is null
     * @throws IllegalArgumentException if theSeries does not exist
     */
    
    public boolean removeFromSeries(TvEpisode removeEpisode, TvSeries theSeries)
            throws NullPointerException, IllegalArgumentException;

    /**
     * Sets a user's rating for a video, as a number of stars from 1 to 5.
     * 
     * <p> If this user has not rated this video than the rating is created for the specified video.
     * If this has user already rated this video, and the new rating is different than the previous rating, 
     * than the previous rating is replaced with the new rating. If the rating is not integer from 1 to 5, 
     * throws IllegalArgumentException.
     * 
     * @param theUser user rating the video
     * @param rating integer from 1-5 representing star rating of video
     * @param theVideo video being rated
     * @throws IllegalArgumentException if rating is not an integer from 1 to 5
     * @throws NullPointerException if theUser or theVideo is null
     */
    public void rateVideo(User theUser, int rating, Video theVideo);

    /**
     * Clears a user's rating on a video. If this user has rated this video and the rating has not
     * already been cleared, then the rating is cleared and the state will appear as if the rating
     * was never made. If this user has not rated this video, or if the rating has already been
     * cleared, then this method will throw an IllegalArgumentException.
     *
     * @param theUser user whose rating should be cleared
     * @param theVideo video from which the user's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a rating on record for
     * the video
     * @throws NullPointerException if either the user or the video is null
     */
    public void clearRating(User theUser, Video theVideo);

    /**
     * Predicts the rating a user will assign to a video that they have not yet rated, as a number
     * of stars from 1 to 5.
     * 
     * <p> Predicts a rating for an unrated video based upon ratings the user has given to
     * other videos. If the user has not rated any videos, predictRating returns null.
     * 
     * @param theUser User for whom prediction will be made
     * @param theVideo Video to make prediction for
     * @param return the predicted rating as an int from 1 to 5; null if user has not rated 
     * any videos
     * @throws NullPointerException if either theUser or theVideo is null
     * @throws IllegalArgumentException if theUser has already rated the video
     */
    public int predictRating(User theUser, Video theVideo);

    /**
     * Suggests a video for a user based on their predicted ratings.
     * 
     * <p> suggests a video based upon predicted ratings of the videos the user 
     * has yet to rate. returns the suggested video. throws NullPointerException 
     * if the user is null.
     * 
     * @param theUser user for whom to suggest a video
     * @return the video to suggest
     * @throws NullPointerException if theUser is null
     */
    public Video suggestVideo(User theUser);


}

