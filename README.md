# codeChallengeTwitParser

This project created to pass code challenge on Insights Data Science

This is java based twit parser. 
  It'will walk through file tweets.txt.
  Replace and count all non-ascii symbols via Util class.
  Prints cleaned and well formated tweets in f1.txt file in tweet_output directory.
  Also the code is getting twitter hashtags, building graph with 60 second range with Graph class.
  After counting average degree it will save this parameter to f2.txt file in tweet_output directory.

To run the project you need to execute run.sh script.

Used libs:
  GSON library that helps parse json via streams
