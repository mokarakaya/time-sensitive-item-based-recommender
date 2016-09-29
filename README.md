# time-sensitive-item-based-recommender
Time aware Item Based Recommendation System

In this study, I implemented a time sensitive item based collaborative filtering recommender system.
This system basically recommends movies to particular users by using their ratings to other items.

Original implementation is item based collaborative filtering algorithm (http://www10.org/cdrom/papers/519/node11.html) but in this implementation we manipulate this algorithm in order to consider the time of the given rating.
We give more importance to recent ratings of the user, since users' tastes change in time.

We use dataset at http://grouplens.org/datasets/movielens/100k/

Similarity and Recommender interfaces consist general methods of them.
AbstractSimilarity and AbstractItemBasedRecommender have generic methods which is applicable to their variations. For instance, every collaborative filtering recommender creates recommendation lists as it is implemented in getRecommendationList method.


Execution time for first 5 users is ~15 seconds with a 8 processor and 8 GB RAM computer.
