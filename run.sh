#!/usr/bin/env bash

# example of the run script for running the word count

# I'll execute my programs, with the input directory tweet_input and output the files in the directory tweet_output



clear
echo "---------Starting Script---------"
echo
echo "---------Compile started---------"
javac -d bin -cp lib/*:./src src/app/Main.java
echo "---------Run started---------"
java -cp ./bin:./lib/* app.Main
echo "---------Process finished---------"