#!/bin/sh
# format text to 60-char lines
awk '
/./ { for (i = 1; i <= NF; i++)
         addword($i) }
/^$/ { printline(); print "" }
END { printline() }
function addword(w) {
  if (length(line) + length(w) > 60)
    printline()
  line = line space w
  space = " "
}

function printline() {
  if (length(line) > 0)
    print line
    line = space = ""
  }
' "$@"
