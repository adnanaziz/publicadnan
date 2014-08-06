# @begin-skip, @end-skip lines
# foo bar
# @begin-skip
# something
# something else
# @end-skip
# more lines
# the last line

import re

def myskip(A, B):
    hA = open(A)
    hB = open(B, "w")
    inBlock = False
    for line in hA:
        if re.match("^@begin-skip", line):
            inBlock = True
            continue
        if re.match("^@end-skip", line):
            inBlock = False
            continue
        if not inBlock:
            hB.write(line)

    hA.close()
    hA.close()

myskip("in.txt", "out.txt")
