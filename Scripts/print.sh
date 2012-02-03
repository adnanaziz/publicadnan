#!/bin/tcsh
foreach f (`ls *.c`) 
  echo "Processing $f"
  setenv TMP "`cat $f | grep main`"
  setenv TMP2 `echo "$TMP" | sed '{s/main/foo/}' | sed '{s/\{//} | sed '{s/\}//}`
  #setenv TMP2 `echo "$TMP" | sed '{s/main/foo/}'`
  echo "$TMP2"
end
