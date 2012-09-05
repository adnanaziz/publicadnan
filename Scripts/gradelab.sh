#!/bin/bash

function test {
  # svn co https://subversion.assembla.com/svn/$1 RPN.java
  ./run.sh | grep "@score" >> labgrades.csv
}

for s in "adnanpublic/Scripts" "adnanpublic/Scripts"
  do
    test "adnanpublic/Scripts"
  done
