#!/bin/bash

function test {
  OUTFILE="/tmp/lcalctest.$RANDOM"
  GOLDFILE="/tmp/lcalctest.$RANDOM"
  echo $1 | ./lcalc > $OUTFILE
  echo $2 > $GOLDFILE
  if diff $OUTFILE $GOLDFILE > /dev/null ; then
    echo Expr test $1 passes
  else
    echo Expr test $2 fails
  fi
  rm -f $OUTFILE;
  rm -f $GOLDFILE;
}

test "2 + 2" "4"
test "2 + 3*4" "14"
